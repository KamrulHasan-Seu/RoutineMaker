package session.demo.routineDemo.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import session.demo.routineDemo.security.AccessControlFactory;


@Theme(value = Lumo.class)
@Route
@RouteAlias(value = "")
public class MainLayout extends FlexLayout implements RouterLayout {


    private Menu menu;
    public MainLayout() {

        this.setSizeFull();

        menu = new Menu();
        menu.addView(SampleCrudView.class, SampleCrudView.VIEW_NAME,
                VaadinIcon.INFO_CIRCLE.create());
        menu.addView(AboutView.class, AboutView.VIEW_NAME,
                VaadinIcon.ALARM.create());


        this.add(menu);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        attachEvent.getUI()
                .addShortcutListener(
                        () -> AccessControlFactory.getInstance()
                                .createAccessControl().signOut(),
                        Key.KEY_L, KeyModifier.CONTROL);

        // add the admin view menu item if/when it is registered dynamically
        Command addAdminMenuItemCommand = () -> menu.addView(AdminView.class,
                AdminView.VIEW_NAME, VaadinIcon.DOCTOR.create());
        RouteConfiguration sessionScopedConfiguration = RouteConfiguration
                .forSessionScope();
        if (sessionScopedConfiguration.isRouteRegistered(AdminView.class)) {
            addAdminMenuItemCommand.execute();
        } else {
            sessionScopedConfiguration.addRoutesChangeListener(event -> {
                for (RouteBaseData data : event.getAddedRoutes()) {
                    if (data.getNavigationTarget().equals(AdminView.class)) {
                        addAdminMenuItemCommand.execute();
                    }
                }
            });
        }
    }
}
