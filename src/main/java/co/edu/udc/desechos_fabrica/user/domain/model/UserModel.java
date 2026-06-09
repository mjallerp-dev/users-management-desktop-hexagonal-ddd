package co.edu.udc.desechos_fabrica.user.domain.model;

import co.edu.udc.desechos_fabrica.enterprise.domain.valueobject.EnterpriseId;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserStatus;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserEmail;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserFirstName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserLastName;
import co.edu.udc.desechos_fabrica.user.domain.valueobject.UserPassword;
import lombok.Value;
import lombok.With;

import java.util.Objects;

@Value
@With
public class UserModel {

  Long id;
  UserFirstName firstName;
  UserLastName lastName;
  UserEmail email;
  UserPassword password;
  UserRole role;
  UserStatus status;
  EnterpriseId enterpriseId;

  public UserModel(Long id, UserFirstName firstName, UserLastName lastName, UserEmail email,
                    UserPassword password, UserRole role, UserStatus status,
                    EnterpriseId enterpriseId) {
    this.id = id;
    this.firstName = Objects.requireNonNull(firstName, "FirstName cannot be null");
    this.lastName = Objects.requireNonNull(lastName, "LastName cannot be null");
    this.email = Objects.requireNonNull(email, "Email cannot be null");
    this.password = Objects.requireNonNull(password, "Password cannot be null");
    this.role = Objects.requireNonNull(role, "Role cannot be null");
    this.status = Objects.requireNonNull(status, "Status cannot be null");
    this.enterpriseId = enterpriseId;
  }

  public static UserModel create(
      final Long id,
      final UserFirstName firstName,
      final UserLastName lastName,
      final UserEmail email,
      final UserPassword password,
      final UserRole role,
      final Long enterpriseId) {
    EnterpriseId enterpriseIdValue = (enterpriseId != null)
            ? new EnterpriseId(enterpriseId)
            : null;
    return new UserModel(id, firstName, lastName, email, password, role, UserStatus.PENDING, enterpriseIdValue);
  }

  public UserModel activate() {
    return new UserModel(id, firstName, lastName, email, password, role, UserStatus.ACTIVE, this.enterpriseId);
  }

  public UserModel deactivate() {
    return new UserModel(id, firstName, lastName, email, password, role, UserStatus.INACTIVE, this.enterpriseId);
  }

  public UserModel updateWith(
          final UserFirstName newFirstName,
          final UserLastName newLastName,
          final UserEmail newEmail,
          final UserPassword newPassword,
          final UserRole newRole,
          final UserStatus newStatus,
          final EnterpriseId newEnterpriseId) {

    UserStatus finalStatus = (newStatus != null) ? newStatus : this.status;
    UserRole finalRole = (newRole != null) ? newRole : this.role;
    
    boolean enterpriseIsChanging = !Objects.equals(this.enterpriseId, newEnterpriseId);

    if (enterpriseIsChanging) {
      if (newEnterpriseId != null && newRole == UserRole.MEMBER) {
        finalRole = UserRole.ENTERPRISE_ADMIN;
        finalStatus = UserStatus.PENDING;
      }
      if (newEnterpriseId == null && newRole == UserRole.ENTERPRISE_ADMIN){
        finalRole = UserRole.MEMBER;
      }
    }

    return new UserModel(id, newFirstName, newLastName, newEmail, newPassword, finalRole, finalStatus, newEnterpriseId);
  }
}
