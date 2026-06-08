package co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.user.application.port.in.CreateUserUseCase;
import co.edu.udc.desechos_fabrica.user.application.port.in.DeleteUserUseCase;
import co.edu.udc.desechos_fabrica.user.application.port.in.GetAllUsersUseCase;
import co.edu.udc.desechos_fabrica.user.application.port.in.GetUserByEmailUseCase;
import co.edu.udc.desechos_fabrica.user.application.port.in.LoginUseCase;
import co.edu.udc.desechos_fabrica.user.application.port.in.UpdateUserUseCase;
import co.edu.udc.desechos_fabrica.user.application.service.dto.command.CreateUserCommand;
import co.edu.udc.desechos_fabrica.user.application.service.dto.command.DeleteUserCommand;
import co.edu.udc.desechos_fabrica.user.application.service.dto.command.LoginCommand;
import co.edu.udc.desechos_fabrica.user.application.service.dto.command.UpdateUserCommand;
import co.edu.udc.desechos_fabrica.user.application.service.dto.query.GetUserByEmailQuery;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.exception.InvalidCredentialsException;
import co.edu.udc.desechos_fabrica.user.domain.exception.UserAlreadyExistsException;
import co.edu.udc.desechos_fabrica.user.domain.exception.UserNotFoundException;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserEmail;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserFirstName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserLastName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserPassword;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.CreateUserRequest;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.LoginRequest;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserController")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  private static final String BCRYPT_HASH =
          "$2a$12$abcdefghijklmnopqrstabcdefghijklmnñopqrstuvwxyzabcdefgh";

  @Mock private CreateUserUseCase createUserUseCase;
  @Mock private UpdateUserUseCase updateUserUseCase;
  @Mock private DeleteUserUseCase deleteUserUseCase;
  @Mock private GetUserByEmailUseCase getUserByEmailUseCase;
  @Mock private GetAllUsersUseCase getAllUsersUseCase;
  @Mock private LoginUseCase loginUseCase;

  private UserController controller;

  private static UserModel buildUser(
          final String firstName,
          final String lastName,
          final String email,
          final UserRole role,
          final UserStatus status,
          final Long enterpriseId) {
    return new UserModel(
            null,
            new UserFirstName(firstName),
            new UserLastName(lastName),
            new UserEmail(email),
            UserPassword.fromHash(BCRYPT_HASH),
            role,
            status,
            new EnterpriseId(enterpriseId));
  }

  @BeforeEach
  void setUp() {
    controller =
            new UserController(
                    createUserUseCase,
                    updateUserUseCase,
                    deleteUserUseCase,
                    getUserByEmailUseCase,
                    getAllUsersUseCase,
                    loginUseCase);
  }

  // ── listAllUsers

  @Test
  @DisplayName("listAllUsers() returns a correctly mapped UserResponse list when the use case returns users")
  void listAllUsers_returnsMappedResponseList_whenUsersExist() {
    final UserModel user = buildUser("Alice", "Smith", "alice@example.com", UserRole.ADMIN, UserStatus.ACTIVE, 2L);
    when(getAllUsersUseCase.execute()).thenReturn(List.of(user));

    final List<UserResponse> result = controller.listAllUsers();

    assertAll(
            "single-user list mapping",
            () -> assertEquals(1, result.size()),
            () -> assertEquals("Alice", result.getFirst().firstName()),
            () -> assertEquals("Smith", result.getFirst().lastName()),
            () -> assertEquals("alice@example.com", result.getFirst().email()),
            () -> assertEquals("ADMIN", result.getFirst().role()),
            () -> assertEquals("ACTIVE", result.getFirst().status()));
    verify(getAllUsersUseCase).execute();
  }

  @Test
  @DisplayName("listAllUsers() returns an empty list when the use case returns no users")
  void listAllUsers_returnsEmptyList_whenNoUsersExist() {
    when(getAllUsersUseCase.execute()).thenReturn(List.of());

    final List<UserResponse> result = controller.listAllUsers();

    assertTrue(result.isEmpty());
    verify(getAllUsersUseCase).execute();
  }

  // ── findUserByEmail

  @Test
  @DisplayName("findUserByEmail() builds a GetUserByIdQuery with the given id and returns the mapped response")
  void findUserByEmail_returnsMappedResponse_whenUserExists() {
    final String email = "bob@example.com";
    final UserModel user = buildUser("Bob", "Jones", email, UserRole.MEMBER, UserStatus.ACTIVE, 2L);
    when(getUserByEmailUseCase.execute(any(GetUserByEmailQuery.class))).thenReturn(user);

    final UserResponse result = controller.findUserByEmail(email);

    assertAll(
            "findUserByEmail response mapping",
            () -> assertEquals("Bob", result.firstName()),
            () -> assertEquals("Jones", result.lastName()),
            () -> assertEquals(email, result.email()),
            () -> assertEquals("MEMBER", result.role()),
            () -> assertEquals("ACTIVE", result.status()));
  }

  @Test
  @DisplayName("findUserByEmail() propagates UserNotFoundException when the use case cannot find the user")
  void findUserByEmail_propagatesUserNotFoundException_whenUserDoesNotExist() {
    final String email = "bob@example.com";
    when(getUserByEmailUseCase.execute(any(GetUserByEmailQuery.class)))
            .thenThrow(UserNotFoundException.becauseEmailWasNotFound(email));

    assertThrows(UserNotFoundException.class, () -> controller.findUserByEmail(email));
  }

  // ── createUser

  @Test
  @DisplayName("createUser() delegates a correctly populated CreateUserCommand and returns the mapped response")
  void createUser_delegatesCorrectCommandAndReturnsMappedResponse_whenCreationSucceeds() {
    final CreateUserRequest request = new CreateUserRequest("Carol", "White", "carol@example.com", "Pass1234", UserRole.MEMBER, 1L);
    final UserModel createdUser = buildUser("Carol", "White", "carol@example.com", UserRole.MEMBER, UserStatus.PENDING, 1L);
    final ArgumentCaptor<CreateUserCommand> captor = ArgumentCaptor.forClass(CreateUserCommand.class);
    when(createUserUseCase.execute(captor.capture())).thenReturn(createdUser);

    final UserResponse result = controller.createUser(request);

    assertAll(
            "createUser command delegation and response mapping",
            () -> assertEquals("Carol", captor.getValue().firstName()),
            () -> assertEquals("White", captor.getValue().lastName()),
            () -> assertEquals("carol@example.com", captor.getValue().email()),
            () -> assertEquals("Pass1234", captor.getValue().password()),
            () -> assertEquals("MEMBER", captor.getValue().role()),
            () -> assertEquals("PENDING", result.status()));
  }

  @Test
  @DisplayName("createUser() propagates UserAlreadyExistsException when the use case rejects a duplicate email")
  void createUser_propagatesUserAlreadyExistsException_whenEmailIsDuplicated() {
    final CreateUserRequest request = new CreateUserRequest("Dave", "Brown", "dave@example.com", "Pass5678", UserRole.MEMBER, 1L);
    when(createUserUseCase.execute(any())).thenThrow(UserAlreadyExistsException.becauseEmailAlreadyExists("dave@example.com"));

    assertThrows(UserAlreadyExistsException.class, () -> controller.createUser(request));
  }

  // ── updateUser (¡Modificado con Request y Captor!)

  @Test
  @DisplayName("updateUser() mapea correctamente el Request al Command, delega al Use Case y retorna la respuesta")
  void updateUser_delegatesCorrectCommandAndReturnsMappedResponse_whenUpdateSucceeds() {
    // Arrange
    final UpdateUserRequest request = new UpdateUserRequest(
            "eve2@example.com", "Eve", "Martinez", "eve@example.com", "NewPass9!", "ADMIN", "ACTIVE", 2L);
    final UserModel updatedUser = buildUser("Eve", "Martinez", "eve@example.com", UserRole.ADMIN, UserStatus.ACTIVE, 2L);

    final ArgumentCaptor<UpdateUserCommand> captor = ArgumentCaptor.forClass(UpdateUserCommand.class);
    when(updateUserUseCase.execute(captor.capture())).thenReturn(updatedUser);

    // Act
    final UserResponse result = controller.updateUser(request);

    // Assert
    assertAll(
            "Verificación del mapeo interno Request -> Command",
            () -> assertEquals("Eve", captor.getValue().newFirstName(), "firstName mapeado incorrectamente"),
            () -> assertEquals("Martinez", captor.getValue().newLastName(), "lastName mapeado incorrectamente"),
            () -> assertEquals("eve@example.com", captor.getValue().newEmail(), "newEmail mapeado incorrectamente"),
            () -> assertEquals("NewPass9!", captor.getValue().password(), "password mapeado incorrectamente"),
            () -> assertEquals("ADMIN", captor.getValue().role(), "role mapeado incorrectamente"),
            () -> assertEquals("ACTIVE", captor.getValue().status(), "status mapeado incorrectamente")
    );

    assertAll(
            "Verificación del mapeo de salida Model -> Response",
            () -> assertEquals("Eve", result.firstName()),
            () -> assertEquals("eve@example.com", result.email()),
            () -> assertEquals("ADMIN", result.role())
    );
  }

  @Test
  @DisplayName("updateUser() propaga UserNotFoundException sin alterarla")
  void updateUser_propagatesUserNotFoundException_whenUserDoesNotExist() {
    // Arrange
    final UpdateUserRequest request = new UpdateUserRequest(
            "ghost2@example.com","Ghost", "User", "ghost@example.com", "Pass9999!", "ADMIN", "INACTIVE", 2L);

    when(updateUserUseCase.execute(any(UpdateUserCommand.class)))
            .thenThrow(UserNotFoundException.becauseEmailWasNotFound("ghost@example.com"));

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> controller.updateUser(request));
  }

  // ── deleteUser

  @Test
  @DisplayName("deleteUser() delegates a DeleteUserCommand with the given id to the use case")
  void deleteUser_delegatesDeleteCommandWithCorrectId() {
    final ArgumentCaptor<DeleteUserCommand> captor = ArgumentCaptor.forClass(DeleteUserCommand.class);
    doNothing().when(deleteUserUseCase).execute(captor.capture());

    controller.deleteUser("ghost@example.com");

    assertEquals("ghost@example.com", captor.getValue().email());
  }

  @Test
  @DisplayName("deleteUser() propagates UserNotFoundException when the use case cannot find the user")
  void deleteUser_propagatesUserNotFoundException_whenUserDoesNotExist() {
    doThrow(UserNotFoundException.becauseEmailWasNotFound("ghost@example.com")).when(deleteUserUseCase).execute(any());

    assertThrows(UserNotFoundException.class, () -> controller.deleteUser("ghost@example.com"));
  }

  // ── login

  @Test
  @DisplayName("login() delegates a correctly populated LoginCommand and returns the mapped response")
  void login_delegatesCorrectCommandAndReturnsMappedResponse_whenCredentialsAreValid() {
    final LoginRequest request = new LoginRequest("frank@example.com", "Pass1234!");
    final UserModel loggedUser = buildUser("Frank", "Green", "frank@example.com", UserRole.MEMBER, UserStatus.ACTIVE, 2L);
    final ArgumentCaptor<LoginCommand> captor = ArgumentCaptor.forClass(LoginCommand.class);
    when(loginUseCase.execute(captor.capture())).thenReturn(loggedUser);

    final UserResponse result = controller.login(request);

    assertAll(
            "login command delegation and response mapping",
            () -> assertEquals("frank@example.com", captor.getValue().email()),
            () -> assertEquals("Pass1234!", captor.getValue().password()),
            () -> assertEquals("frank@example.com", result.email()),
            () -> assertEquals("ACTIVE", result.status()));
  }

  @Test
  @DisplayName("login() propagates InvalidCredentialsException when the use case rejects the credentials")
  void login_propagatesInvalidCredentialsException_whenCredentialsAreInvalid() {
    final LoginRequest request = new LoginRequest("frank@example.com", "WrongPass1");
    when(loginUseCase.execute(any())).thenThrow(InvalidCredentialsException.becauseCredentialsAreInvalid());

    assertThrows(InvalidCredentialsException.class, () -> controller.login(request));
  }
}