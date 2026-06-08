package co.edu.udc.desechos_fabrica.user.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.shared.infrastructure.session.SessionManager;
import co.edu.udc.desechos_fabrica.user.application.port.out.GetUserByEmailPort;
import co.edu.udc.desechos_fabrica.user.application.port.out.UpdateUserPort;
import co.edu.udc.desechos_fabrica.user.application.service.dto.command.UpdateUserCommand;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.exception.PermissionDeniedException;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;
import co.edu.udc.desechos_fabrica.user.domain.service.UserRoleManager;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserEmail;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserFirstName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserLastName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserPassword;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UserResponse;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UpdateUserService - Escenarios de Negocio y Seguridad")
@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

  @Mock private UpdateUserPort updateUserPort;
  @Mock private GetUserByEmailPort getUserByEmailPort;
  @Mock private EmailNotificationService emailNotificationService;
  @Mock private UserRoleManager userRoleManager;

  private UpdateUserService service;

  private UserModel existingUser;
  private UserModel actorUser;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new UpdateUserService(updateUserPort, getUserByEmailPort, emailNotificationService, validatorFactory.getValidator(), userRoleManager);
    }

    existingUser = new UserModel(
            1L,
            new UserFirstName("John"),
            new UserLastName("Arrieta"),
            new UserEmail("john@example.com"),
            UserPassword.createDummy(),
            UserRole.MEMBER,
            UserStatus.ACTIVE,
            new EnterpriseId(1L));

    actorUser = new UserModel(
            2L,
            new UserFirstName("Admin"),
            new UserLastName("User"),
            new UserEmail("admin@ecoresiduos.com"),
            UserPassword.createDummy(),
            UserRole.ADMIN,
            UserStatus.ACTIVE,
            new EnterpriseId(999L));
  }

  @AfterEach
  void tearDown() {
    SessionManager.logout();
  }

  private void mockSession(String email, String role) {
    SessionManager.login(new UserResponse("SessionFirst", "SessionLast", email, role, "ACTIVE", 999L));
  }

  @Test
  @DisplayName("execute() actualiza el usuario y envía notificación cuando los datos son válidos")
  void shouldUpdateUserAndNotifyWhenDataIsValid() {
    final String targetEmail = "john@example.com";
    final String newEmail = "new.john@example.com";
    mockSession("admin@ecoresiduos.com", "ADMIN");

    final UpdateUserCommand command = new UpdateUserCommand(
            targetEmail, "John", "Updated", newEmail, null,
            UserRole.MEMBER.name(), UserStatus.ACTIVE.name(), 1L);
    
    when(getUserByEmailPort.getByEmail(new UserEmail(targetEmail))).thenReturn(Optional.of(existingUser));
    when(getUserByEmailPort.getByEmail(new UserEmail(newEmail))).thenReturn(Optional.empty());
    when(updateUserPort.update(any(), any())).thenAnswer(invocation -> invocation.getArgument(1));

    final UserModel result = service.execute(command);

    assertNotNull(result);
    assertEquals(newEmail, result.getEmail().value());
    verify(updateUserPort).update(eq(new UserEmail(targetEmail)), any(UserModel.class));
    verify(emailNotificationService).notifyUserUpdated(any(UserModel.class));
  }

  @Test
  @DisplayName("should throw PermissionDenied when Reviewer tries to update an Admin")
  void shouldThrowPermissionDeniedWhenReviewerUpdatesAdmin() {
    mockSession("reviewer@corp.com", "REVIEWER");

    UserModel adminUser = createMockUser("admin@corp.com", UserRole.ADMIN);
    when(getUserByEmailPort.getByEmail(any())).thenReturn(Optional.of(adminUser));

    doThrow(PermissionDeniedException.class)
            .when(userRoleManager).checkUpdatePermissions(any(), any(), any());

    UpdateUserCommand command = new UpdateUserCommand("admin@corp.com", "John", "Doe", "e@e.com", null, "ADMIN", "ACTIVE", 1L);

    assertThrows(PermissionDeniedException.class, () -> service.execute(command));
  }

  @Test
  @DisplayName("should throw PermissionDenied when EnterpriseAdmin tries to update user outside their enterprise")
  void shouldThrowPermissionDeniedWhenEnterpriseAdminCrossesEnterprise() {
    mockSession("ent.admin@corp.com", "ENTERPRISE_ADMIN");

    UserModel targetUser = createMockUser("member@other.com", UserRole.MEMBER);
    when(getUserByEmailPort.getByEmail(any())).thenReturn(Optional.of(targetUser));

    doThrow(PermissionDeniedException.class)
            .when(userRoleManager).checkUpdatePermissions(any(), any(), any());

    UpdateUserCommand command = new UpdateUserCommand("member@other.com", "John", "Doe", "e@e.com", null, "MEMBER", "ACTIVE", 2L);

    assertThrows(PermissionDeniedException.class, () -> service.execute(command));
  }

  @Test
  @DisplayName("should update user successfully when role manager approves")
  void shouldUpdateSuccessfully() {
    mockSession("admin@corp.com", "ADMIN");
    UserModel target = createMockUser("target@corp.com", UserRole.MEMBER);

    when(getUserByEmailPort.getByEmail(new UserEmail("target@corp.com"))).thenReturn(Optional.of(target));
    when(updateUserPort.update(any(), any())).thenAnswer(inv -> inv.getArgument(1));

    UpdateUserCommand command = new UpdateUserCommand("target@corp.com", "New", "Name", "new@e.com", null, "MEMBER", "ACTIVE", 1L);

    assertDoesNotThrow(() -> service.execute(command));
    verify(emailNotificationService).notifyUserUpdated(any());
  }
  private UserModel createMockUser(String email, UserRole role) {
    return new UserModel(1L, new UserFirstName("Frank"), new UserLastName("Lewis"),
            new UserEmail(email), UserPassword.createDummy(), role, UserStatus.ACTIVE, new EnterpriseId(1L));
  }
}