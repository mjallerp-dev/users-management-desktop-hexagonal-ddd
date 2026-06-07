package co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.mapper;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.model.UserModel;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.*;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.dto.UserPersistenceDto;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserPersistenceMapper {

  public UserPersistenceDto fromModelToDto(final UserModel user) {
    final Long enterpriseId = user.getEnterpriseId() != null ? user.getEnterpriseId().value() : null;
    return new UserPersistenceDto(
        null,
        user.getFirstName().value(),
        user.getLastName().value(),
        user.getEmail().value(),
        user.getPassword().value(),
        enterpriseId,
        user.getRole().name(),
        user.getStatus().name(),
        null,
        null);
  }

  public UserEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new UserEntity(
        getNullableLong(resultSet,"id"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("email"),
        resultSet.getString("password"),
        resultSet.getString("role"),
        resultSet.getString("status"),
        getNullableLong(resultSet,"enterprise_id"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public UserModel fromEntityToModel(final UserEntity entity) {
    return new UserModel(
        entity.id(),
        new UserFirstName(entity.firstName()),
        new UserLastName(entity.lastName()),
        new UserEmail(entity.email()),
        UserPassword.fromHash(entity.password()),
        UserRole.fromString(entity.role()),
        UserStatus.fromString(entity.status()),
        entity.enterpriseId() != null ? new EnterpriseId(entity.enterpriseId()) : null);
  }

  public UserModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<UserModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    final List<UserModel> users = new ArrayList<>();
    while (resultSet.next()) {
      users.add(fromResultSetToModel(resultSet));
    }
    return users;
  }

  private Long getNullableLong(ResultSet resultSet, String column) throws SQLException {
    long value = resultSet.getLong(column);
    return resultSet.wasNull() ? null : value;
  }
}
