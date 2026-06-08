package co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.exception.UserNotFoundException;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserEmail;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserFirstName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserLastName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserPassword;
import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UserRepositoryPostgreSQLTest")
@ExtendWith(MockitoExtension.class)
class UserRepositoryPostgreSQLTest {

  private static final Long ID = 1L;
  private static final String FIRST_NAME = "Miguel";
  private static final String LAST_NAME = "Jal";
  private static final String EMAIL = "john@example.com";
  private static final Long ENTERPRISE_ID = 2L;
  private static final String HASH = "$2a$12$abcdefghijklmnopqrstuO";
  private static final String ROLE = "ADMIN";
  private static final String STATUS = "ACTIVE";
  private static final String CREATED_AT = "2024-01-01";
  private static final String UPDATED_AT = "2024-01-02";

  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private UserRepositoryPostgresSQL repository;
  private UserModel userModel;
  private UserEmail userEmail;

  @BeforeEach
  void setUp() {
    repository = new UserRepositoryPostgresSQL(connection);
    userEmail = new UserEmail(EMAIL);
    userModel = new UserModel(
            ID,
            new UserFirstName(FIRST_NAME),
            new UserLastName(LAST_NAME),
            userEmail,
            UserPassword.fromHash(HASH),
            UserRole.REVIEWER,
            UserStatus.ACTIVE,
            new EnterpriseId(ENTERPRISE_ID));
  }


  private void configureStatementAndResultSet() throws SQLException {
    lenient().when(connection.prepareStatement(anyString())).thenReturn(statement);
    lenient().when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
    lenient().when(statement.executeQuery()).thenReturn(resultSet);
    lenient().when(statement.getGeneratedKeys()).thenReturn(resultSet);
  }

  // Se usa lenient() para que los métodos de error/cierre no fallen si no leen todas las columnas
  private void configureResultSetRow() throws SQLException {
    lenient().when(resultSet.getLong(1)).thenReturn(ID);
    lenient().when(resultSet.getLong("id")).thenReturn(ID);
    lenient().when(resultSet.getString("first_name")).thenReturn(FIRST_NAME);
    lenient().when(resultSet.getString("last_name")).thenReturn(LAST_NAME);
    lenient().when(resultSet.getString("email")).thenReturn(EMAIL);
    lenient().when(resultSet.getString("password")).thenReturn(HASH);
    lenient().when(resultSet.getString("role")).thenReturn(ROLE);
    lenient().when(resultSet.getString("status")).thenReturn(STATUS);
    lenient().when(resultSet.getString("created_at")).thenReturn(CREATED_AT);
    lenient().when(resultSet.getString("updated_at")).thenReturn(UPDATED_AT);
    lenient().when(resultSet.getLong("enterprise_id")).thenReturn(ENTERPRISE_ID);
  }

  // ── save()

  @Test
  @DisplayName("save() executes INSERT and returns the persisted user fetched by id")
  void shouldSaveUserAndReturnByEmail() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    configureResultSetRow();
    when(resultSet.next()).thenReturn(true);

    // Act
    final UserModel result = repository.save(userModel);

    // Assert
    assertAll(
            "save() happy path",
            () -> assertEquals(ID, result.getId()),
            () -> assertEquals(FIRST_NAME, result.getFirstName().value()),
            () -> assertEquals(LAST_NAME, result.getLastName().value()),
            () -> assertEquals(EMAIL, result.getEmail().value()),
            () -> assertEquals(new EnterpriseId(ENTERPRISE_ID), result.getEnterpriseId()));
  }

  @Test
  @DisplayName("save() throws PersistenceException when INSERT raises SQLException")
  void shouldThrowPersistenceExceptionWhenInsertFails() throws SQLException {
    lenient().when(connection.prepareStatement(anyString())).thenReturn(statement);
    lenient().when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
    lenient().when(statement.executeUpdate()).thenThrow(new SQLException("Insert failed"));

    assertThrows(PersistenceException.class, () -> repository.save(userModel));
  }

  @Test
  @DisplayName("save() throws UserNotFoundException when the saved user cannot be found")
  void shouldThrowUserNotFoundExceptionWhenUserNotFoundAfterSave() throws SQLException {
    lenient().when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
    lenient().when(statement.executeUpdate()).thenReturn(1);
    lenient().when(statement.getGeneratedKeys()).thenReturn(resultSet);
    lenient().when(resultSet.next()).thenReturn(false);

    assertThrows(UserNotFoundException.class, () -> repository.save(userModel));
  }

  // ── update()

  @Test
  @DisplayName("update() executes UPDATE and returns the refreshed user fetched by email")
  void shouldUpdateUserAndReturnByEmail() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    configureResultSetRow();
    when(resultSet.next()).thenReturn(true);

    // Act
    final UserModel result = repository.update(userEmail, userModel);

    // Assert
    assertAll(
            "update() happy path",
            () -> assertEquals(ID, result.getId()),
            () -> assertEquals(EMAIL, result.getEmail().value()),
            () -> assertEquals(new EnterpriseId(ENTERPRISE_ID), result.getEnterpriseId()));
  }

  @Test
  @DisplayName("update() throws PersistenceException when UPDATE raises SQLException")
  void shouldThrowPersistenceExceptionWhenUpdateFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Update failed"));

    assertThrows(PersistenceException.class, () -> repository.update(userEmail, userModel));
  }

  // ── getByEmail()

  @Test
  @DisplayName("getByEmail() returns Optional.of(user) when a matching row exists")
  void shouldReturnUserWhenFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    configureResultSetRow();
    when(resultSet.next()).thenReturn(true);

    // Act
    final Optional<UserModel> result = repository.getByEmail(userEmail);

    // Assert
    assertAll(
            "getByEmail() found",
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(ID, result.get().getId()),
            () -> assertEquals(EMAIL, result.get().getEmail().value()));
  }

  @Test
  @DisplayName("getByEmail() returns Optional.empty() when no matching row exists")
  void shouldReturnEmptyWhenNotFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act
    final Optional<UserModel> result = repository.getByEmail(userEmail);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("getByEmail() throws PersistenceException when prepareStatement raises SQLException")
  void shouldThrowPersistenceExceptionOnGetByEmailFailure() throws SQLException {
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    assertThrows(PersistenceException.class, () -> repository.getByEmail(userEmail));
  }

  @Test
  @DisplayName("getByEmail() throws PersistenceException when executeQuery raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByEmailExecuteQueryFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenThrow(new SQLException("Execute query failed"));

    assertThrows(PersistenceException.class, () -> repository.getByEmail(userEmail));
  }

  @Test
  @DisplayName("getByEmail() throws PersistenceException when PreparedStatement.close() raises SQLException")
  void shouldThrowPersistenceExceptionWhenGetByEmailStatementCloseFails() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);
    doThrow(new SQLException("Close failed")).when(statement).close();

    assertThrows(PersistenceException.class, () -> repository.getByEmail(userEmail));
  }

  // ── getAll()

  @Test
  @DisplayName("getAll() returns one model per row in the result set")
  void shouldReturnAllUsers() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    configureResultSetRow();
    when(resultSet.next()).thenReturn(true, false);

    // Act
    final List<UserModel> result = repository.getAll();

    // Assert
    assertAll(
            "getAll() happy path",
            () -> assertEquals(1, result.size()),
            () -> assertEquals(ID, result.get(0).getId()),
            () -> assertEquals(EMAIL, result.get(0).getEmail().value()),
            () -> assertEquals(new EnterpriseId(ENTERPRISE_ID), result.get(0).getEnterpriseId()));
  }

  @Test
  @DisplayName("getAll() throws PersistenceException when the query raises SQLException")
  void shouldThrowPersistenceExceptionOnGetAllFailure() throws SQLException {
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    assertThrows(PersistenceException.class, () -> repository.getAll());
  }

  // ── delete()

  @Test
  @DisplayName("delete() executes DELETE without throwing")
  void shouldDeleteUserWithoutThrowing() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);

    assertDoesNotThrow(() -> repository.delete(userEmail));
  }

  @Test
  @DisplayName("delete() throws PersistenceException when DELETE raises SQLException")
  void shouldThrowPersistenceExceptionWhenDeleteFails() throws SQLException {
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Delete failed"));

    assertThrows(PersistenceException.class, () -> repository.delete(userEmail));
  }
}