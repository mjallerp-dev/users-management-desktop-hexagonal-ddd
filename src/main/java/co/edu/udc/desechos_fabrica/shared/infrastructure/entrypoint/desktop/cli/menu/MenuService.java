package co.edu.udc.desechos_fabrica.shared.infrastructure.entrypoint.desktop.cli.menu;

import co.edu.udc.desechos_fabrica.location.infrastructure.entrypoint.desktop.cli.menu.LocationMenuOption;
import co.edu.udc.desechos_fabrica.user.domain.enums.UserRole;
import co.edu.udc.desechos_fabrica.user.infrastructure.entrypoint.desktop.cli.menu.UserMenuOption;

import java.util.ArrayList;
import java.util.List;

public class MenuService {

    public List<MenuOption> getOptionsForRole(UserRole userRole) {
        List<MenuOption> options = new ArrayList<>();

        options.add(UserMenuOption.LOGIN);
        options.add(UserMenuOption.EXIT);

        if (userRole == null) {
            options.add(UserMenuOption.CREATE_USER);
            options.add(UserMenuOption.LOGIN);
            options.add(UserMenuOption.EXIT);
        } else {
            options.add(UserMenuOption.LOGOUT);
        }
        if (userRole == UserRole.ADMIN || userRole == UserRole.REVIEWER) {
            options.addAll(List.of(
                    UserMenuOption.LIST_USERS,
                    UserMenuOption.FIND_USER,
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
                    UserMenuOption.LIST_USERS,
                    UserMenuOption.FIND_USER,
                    UserMenuOption.CREATE_USER,
                    UserMenuOption.UPDATE_USER,
                    UserMenuOption.UPDATE_ENTERPRISE,
                    UserMenuOption.ACTIVATE_ENTERPRISE,
                    UserMenuOption.DEACTIVATE_ENTERPRISE,
                    LocationMenuOption.MANAGE_LOCATION
            ));
        if (userRole == UserRole.MEMBER)
            options.addAll(List.of(
                    UserMenuOption.LIST_USERS,
                    UserMenuOption.FIND_USER,
                    UserMenuOption.UPDATE_USER,
                    LocationMenuOption.MANAGE_LOCATION

            ));
        return options;
    }
}