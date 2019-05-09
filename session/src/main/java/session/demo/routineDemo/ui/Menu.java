package session.demo.routineDemo.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import session.demo.routineDemo.security.AccessControlFactory;

public class Menu extends FlexLayout {
    private Tabs tabs = new Tabs();
    public Menu() {
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        add(tabs);

        Button logInButton = new Button("LogIn",
                VaadinIcon.SIGN_IN.create());

        logInButton.addClickListener(e->{
            logInButton.getUI().ifPresent(ui -> ui.navigate(LoginScreen.class));
        });

        Button logoutButton = new Button("Logout",
                VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(event -> AccessControlFactory
                .getInstance().createAccessControl().signOut());

        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        logInButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        VerticalLayout layout = new VerticalLayout();
        layout.add(logInButton,logoutButton);
        layout.getStyle().set("margin","auto");
        add(layout);
    }

    public void addView(Class<? extends Component> viewClass,String caption, Icon icon) {
        Tab tab = new Tab();
        RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        tab.add(routerLink);
        tabs.add(tab);
    }
}
