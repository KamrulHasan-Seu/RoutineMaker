package session.demo.routineDemo.ui;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import session.demo.routineDemo.security.AccessControl;
import session.demo.routineDemo.security.AccessControlFactory;

@Route
public class LoginScreen extends FlexLayout {
    private LoginForm loginForm;
    private AccessControl accessControl;
    public LoginScreen() {

        accessControl = AccessControlFactory.getInstance().createAccessControl();
        loginForm = new LoginForm();
        add(loginForm);
        loginForm.addLoginListener(this::login);
    }
    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            registerAdminViewIfApplicable();
            getUI().get().navigate("");

        } else {
            event.getSource().setError(true);
        }
    }

    private void registerAdminViewIfApplicable() {
        // register the admin view dynamically only for any admin user logged in
        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            RouteConfiguration.forSessionScope().setRoute(AdminView.VIEW_NAME,
                    AdminView.class, MainLayout.class);
            // as logout will purge the session route registry, no need to
            // unregister the view on logout
        }
    }

}
