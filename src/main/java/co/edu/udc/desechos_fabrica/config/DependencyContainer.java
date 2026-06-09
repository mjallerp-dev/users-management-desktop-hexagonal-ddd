package co.edu.udc.desechos_fabrica.config;

import co.edu.udc.desechos_fabrica.location.application.port.in.*;
import co.edu.udc.desechos_fabrica.location.application.service.*; 
import co.edu.udc.desechos_fabrica.location.infrastructure.adapter.persistence.repository.LocationRepositoryPostgresSQL;
import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.controller.LocationController;

import co.edu.udc.desechos_fabrica.user.application.port.in.*;
import co.edu.udc.desechos_fabrica.user.application.service.*;
import co.edu.udc.desechos_fabrica.user.domain.service.UserRoleManager;
import co.edu.udc.desechos_fabrica.user.domain.service.UserRoleManagerService;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.email.JavaMailEmailSenderAdapter;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.email.SmtpConfig;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.config.DatabaseConfig;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.config.DatabaseConnectionFactory;
import co.edu.udc.desechos_fabrica.user.infrastructure.adapter.persistence.repository.UserRepositoryPostgresSQL;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.controller.UserController;

import java.sql.Connection;
import jakarta.validation.Validator;

public final class DependencyContainer {

  private static final String DB_HOST = "db.host";
  private static final String DB_PORT = "db.port";
  private static final String DB_NAME = "db.name";
  private static final String DB_USER = "db.username";
  private static final String DB_PASSWORD = "db.password";

  private static final String SMTP_HOST = "smtp.host";
  private static final String SMTP_PORT = "smtp.port";
  private static final String SMTP_USER = "smtp.username";
  private static final String SMTP_PASSWORD = "smtp.password";
  private static final String SMTP_FROM = "smtp.from.address";
  private static final String SMTP_FROM_NAME = "smtp.from.name";
  
  private final UserController userController;
  private final LocationController locationController; 

  public DependencyContainer() {
    final AppProperties properties = new AppProperties();

    final Connection connection = buildDatabaseConnection(properties);
    final Validator validator = ValidatorProvider.buildValidator();
    
    final UserRepositoryPostgresSQL userRepository = new UserRepositoryPostgresSQL(connection);

    final JavaMailEmailSenderAdapter emailSender =
            new JavaMailEmailSenderAdapter(buildSmtpConfig(properties));
    final EmailNotificationService emailNotification = new EmailNotificationService(emailSender);

    final UserRoleManager userRoleManager = new UserRoleManagerService();

    final CreateUserUseCase createUserUseCase =
            new CreateUserService(userRepository, userRepository, emailNotification, validator);
    final UpdateUserUseCase updateUserUseCase =
            new UpdateUserService(userRepository, userRepository, emailNotification, validator, userRoleManager);
    final DeleteUserUseCase deleteUserUseCase =
            new DeleteUserService(userRepository, userRepository, validator, userRoleManager);
    final GetUserByEmailUseCase getUserByEmailUseCase = new GetUserByEmailService(userRepository, validator);
    final GetAllUsersUseCase getAllUsersUseCase = new GetAllUsersService(userRepository);
    final LoginUseCase loginUseCase = new LoginService(userRepository, validator);

    this.userController =
            new UserController(
                    createUserUseCase,
                    updateUserUseCase,
                    deleteUserUseCase,
                    getUserByEmailUseCase,
                    getAllUsersUseCase,
                    loginUseCase);


    final LocationRepositoryPostgresSQL locationRepository = new LocationRepositoryPostgresSQL(connection);
    
    final CreateLocationUseCase createLocationUseCase = new CreateLocationService(locationRepository, validator);
    final UpdateLocationUseCase updateLocationUseCase = new UpdateLocationService(locationRepository, locationRepository, validator);
    final ActivateLocationUseCase activateLocationUseCase = new ActivateLocationService(locationRepository, locationRepository, validator);
    final DeactivateLocationUseCase deactivateLocationUseCase = new DeactivateLocationService(locationRepository, locationRepository, validator);
    final GetLocationByIdUseCase getLocationByIdUseCase = new GetLocationByIdService(locationRepository, validator);
    final GetAllLocationsUseCase getAllLocationsUseCase = new GetAllLocationsService(locationRepository);
    
    this.locationController =
            new LocationController(
                    createLocationUseCase,
                    updateLocationUseCase,
                    activateLocationUseCase,
                    deactivateLocationUseCase,
                    getLocationByIdUseCase,
                    getAllLocationsUseCase);
  }
  
  public UserController userController() {
    return userController;
  }

  public LocationController locationController() {
    return locationController;
  }

  private static Connection buildDatabaseConnection(final AppProperties properties) {
    final DatabaseConfig config =
            new DatabaseConfig(
                    properties.get(DB_HOST),
                    properties.getInt(DB_PORT),
                    properties.get(DB_NAME),
                    properties.get(DB_USER),
                    properties.get(DB_PASSWORD));
    return DatabaseConnectionFactory.createConnection(config);
  }

  private static SmtpConfig buildSmtpConfig(final AppProperties properties) {
    return new SmtpConfig(
            properties.get(SMTP_HOST),
            properties.getInt(SMTP_PORT),
            properties.get(SMTP_USER),
            properties.get(SMTP_PASSWORD),
            properties.get(SMTP_FROM),
            properties.get(SMTP_FROM_NAME));
  }
}
