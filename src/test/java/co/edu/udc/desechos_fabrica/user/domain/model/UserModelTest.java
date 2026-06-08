package co.edu.udc.desechos_fabrica.user.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests para UserModel.
 * * Se verifican los métodos con lógica de dominio: create(), activate(), 
 * deactivate() y las reglas de negocio de transiciones en updateWith().
 */
@DisplayName("UserModel")
class UserModelTest {

  // ── Arrange Generales
  private static final Long ID = 1L;
  private static final String HASH = "$2a$12$abcdefghijklmnopqrstuO";
  private static final Long ENTERPRISE_ID = 2L;

  private UserFirstName userFirstName;
  private UserLastName userLastName;
  private UserEmail userEmail;
  private EnterpriseId enterpriseId;
  private UserPassword password;

  @BeforeEach
  void setUp() {
    userFirstName = new UserFirstName("Alice");
    userLastName = new UserLastName("Smith");
    userEmail = new UserEmail("alice@example.com");
    enterpriseId = new EnterpriseId(ENTERPRISE_ID);
    password = UserPassword.fromHash(HASH);
  }

  // ── create()

  @Test
  @DisplayName("create() debe fijar status PENDING, mapear el ID de empresa numérico y preservar campos")
  void shouldCreateUserWithPendingStatusAndCorrectSignatures() {
    // Act
    final UserModel model =
            UserModel.create(ID, userFirstName, userLastName, userEmail, password, UserRole.MEMBER, ENTERPRISE_ID);

    // Assert
    assertAll(
            "create() factory validation",
            () -> assertEquals(ID, model.getId(), "El id debe preservarse"),
            () -> assertEquals(UserStatus.PENDING, model.getStatus(), "status debe iniciar como PENDING"),
            () -> assertEquals(enterpriseId, model.getEnterpriseId(), "enterpriseId debe ser mapeado a Value Object"),
            () -> assertSame(userFirstName, model.getFirstName(), "firstName debe preservarse"),
            () -> assertSame(userLastName, model.getLastName(), "lastName debe preservarse"),
            () -> assertSame(userEmail, model.getEmail(), "email debe preservarse"),
            () -> assertSame(password, model.getPassword(), "password debe preservarse"),
            () -> assertEquals(UserRole.MEMBER, model.getRole(), "role debe preservarse"));
  }

  // ── activate()

  @Test
  @DisplayName("activate() debe retornar nueva instancia con ACTIVE y demás campos intactos")
  void shouldActivateAndPreserveOtherFields() {
    // Arrange
    final UserModel pending =
            new UserModel(ID, userFirstName, userLastName, userEmail, password, UserRole.REVIEWER, UserStatus.PENDING, enterpriseId);

    // Act
    final UserModel activated = pending.activate();

    // Assert
    assertAll(
            "resultado de activate()",
            () -> assertNotSame(pending, activated, "debe ser una nueva instancia"),
            () -> assertEquals(UserStatus.ACTIVE, activated.getStatus(), "status debe ser ACTIVE"),
            () -> assertEquals(ID, activated.getId(), "id debe preservarse"),
            () -> assertSame(userFirstName, activated.getFirstName(), "firstName debe preservarse"),
            () -> assertSame(userLastName, activated.getLastName(), "lastName debe preservarse"),
            () -> assertSame(userEmail, activated.getEmail(), "email debe preservarse"),
            () -> assertSame(password, activated.getPassword(), "password debe preservarse"),
            () -> assertSame(enterpriseId, activated.getEnterpriseId(), "enterpriseId debe preservarse"),
            () -> assertEquals(UserRole.REVIEWER, activated.getRole(), "role debe preservarse"));
  }

  // ── deactivate()

  @Test
  @DisplayName("deactivate() debe retornar nueva instancia con INACTIVE y demás campos intactos")
  void shouldDeactivateAndPreserveOtherFields() {
    // Arrange
    final UserModel active =
            new UserModel(ID, userFirstName, userLastName, userEmail, password, UserRole.REVIEWER, UserStatus.ACTIVE, enterpriseId);

    // Act
    final UserModel deactivated = active.deactivate();

    // Assert
    assertAll(
            "resultado de deactivate()",
            () -> assertNotSame(active, deactivated, "debe ser una nueva instancia"),
            () -> assertEquals(UserStatus.INACTIVE, deactivated.getStatus(), "status debe ser INACTIVE"),
            () -> assertEquals(ID, deactivated.getId(), "id debe preservarse"),
            () -> assertSame(userFirstName, deactivated.getFirstName(), "firstName debe preservarse"),
            () -> assertSame(userLastName, deactivated.getLastName(), "lastName debe preservarse"),
            () -> assertSame(userEmail, deactivated.getEmail(), "email debe preservarse"),
            () -> assertSame(password, deactivated.getPassword(), "password debe preservarse"),
            () -> assertSame(enterpriseId, deactivated.getEnterpriseId(), "enterpriseId debe preservarse"),
            () -> assertEquals(UserRole.REVIEWER, deactivated.getRole(), "role debe preservarse"));
  }

  // ── updateWith() & Reglas de Negocio Especiales

  @Test
  @DisplayName("updateWith() preserva rol y estado si la empresa no cambia")
  void updateWith_preservesRoleAndStatus_whenEnterpriseDoesNotChange() {
    // Arrange
    final UserModel original =
            new UserModel(ID, userFirstName, userLastName, userEmail, password, UserRole.MEMBER, UserStatus.ACTIVE, enterpriseId);

    final UserFirstName newName = new UserFirstName("UpdatedName");

    // Act
    final UserModel updated = original.updateWith(
            newName, userLastName, userEmail, password, null, null, enterpriseId);

    // Assert
    assertAll(
            "Actualización básica",
            () -> assertSame(newName, updated.getFirstName()),
            () -> assertEquals(UserStatus.ACTIVE, updated.getStatus(), "El estado original debe mantenerse si el nuevo es null"),
            () -> assertEquals(UserRole.MEMBER, updated.getRole(), "El rol original debe mantenerse si el nuevo es null")
    );
  }

  @Test
  @DisplayName("updateWith() cambia estado a PENDING y rol a ENTERPRISE_ADMIN si se asigna una nueva empresa")
  void updateWith_resetsStatusAndRole_whenEnterpriseChangesToANewOne() {
    // Arrange
    final UserModel original =
            new UserModel(ID, userFirstName, userLastName, userEmail, password, UserRole.MEMBER, UserStatus.ACTIVE, enterpriseId);

    final EnterpriseId newEnterprise = new EnterpriseId(99L);

    // Act
    final UserModel updated = original.updateWith(
            userFirstName, userLastName, userEmail, password, UserRole.MEMBER, UserStatus.ACTIVE, newEnterprise);

    // Assert
    assertAll(
            "Cambio de empresa activo",
            () -> assertEquals(newEnterprise, updated.getEnterpriseId()),
            () -> assertEquals(UserStatus.PENDING, updated.getStatus(), "Debe forzar PENDING debido al cambio de empresa"),
            () -> assertEquals(UserRole.ENTERPRISE_ADMIN, updated.getRole(), "Debe forzar ENTERPRISE_ADMIN debido al cambio de empresa")
    );
  }

  @Test
  @DisplayName("updateWith() cambia el rol a MEMBER si la empresa pasa a ser nula (removida)")
  void updateWith_setsRoleToMember_whenEnterpriseChangesToNull() {
    // Arrange
    final UserModel original =
            new UserModel(ID, userFirstName, userLastName, userEmail, password, UserRole.ENTERPRISE_ADMIN, UserStatus.ACTIVE, enterpriseId);

    // Act
    final UserModel updated = original.updateWith(
            userFirstName, userLastName, userEmail, password, UserRole.ENTERPRISE_ADMIN, UserStatus.ACTIVE, null);

    // Assert
    assertAll(
            "Remoción de empresa",
            () -> assertNull(updated.getEnterpriseId()),
            () -> assertEquals(UserRole.MEMBER, updated.getRole(), "Al remover la empresa el rol debe degradarse a MEMBER")
    );
  }
}