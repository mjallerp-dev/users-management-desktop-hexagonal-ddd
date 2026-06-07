package co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.menu;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.menu.LocationMenuOption;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.menu.UserMenuOption;

import java.util.ArrayList;
import java.util.List;

public class MenuService {

    public List<UserMenuOption> getOptionsForRole(UserRole userRole) {
        List<UserMenuOption> options = new ArrayList<>();

        options.add(UserMenuOption.LOGIN);
        options.add(UserMenuOption.EXIT);

        if (userRole == null) {
            return options;
        }
        if (userRole == UserRole.ADMIN || userRole == UserRole.REVIEWER) {
            options.addAll(List.of(
                    UserMenuOption.CREATE_USER,
                    UserMenuOption.UPDATE_USER,
                    UserMenuOption.CREATE_ENTERPRISE,
                    UserMenuOption.UPDATE_ENTERPRISE,
                    UserMenuOption.ACTIVATE_ENTERPRISE,
                    UserMenuOption.DEACTIVATE_ENTERPRISE,
                    LocationMenuOption.MANAGE_LOCATION
            ));
        }
        if (userRole == UserRole.ENTERPRISE_ADMIN)
            options.addAll(List.of(
                    UserMenuOption.CREATE_USER,
                    UserMenuOption.UPDATE_USER,
                    UserMenuOption.UPDATE_ENTERPRISE,
                    UserMenuOption.ACTIVATE_ENTERPRISE,
                    UserMenuOption.DEACTIVATE_ENTERPRISE,
                    LocationMenuOption.MANAGE_LOCATION
            ));
        if (userRole == UserRole.MEMBER)
            options.addAll(List.of(
                    UserMenuOption.UPDATE_USER,
                    LocationMenuOption.MANAGE_LOCATION

            ));
        return options;
    }
}