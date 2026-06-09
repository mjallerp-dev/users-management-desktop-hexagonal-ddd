package co.edu.udc.desechos_fabrica;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.LocationManagementCli;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.ManagementCli;
import co.edu.udc.desechos_fabrica.config.DependencyContainer;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    final DependencyContainer container = new DependencyContainer();
    try (final Scanner scanner = new Scanner(System.in)) {
      final ConsoleIO io = new ConsoleIO(scanner, System.out);

      final UserManagementCli userCli = new UserManagementCli(container.userController(), io);
      final LocationManagementCli locationCli = new LocationManagementCli(container.locationController(), io);

      final ManagementCli mainApp = new ManagementCli(container.userController(), userCli, locationCli, io);
      mainApp.start();
    }
  }
}