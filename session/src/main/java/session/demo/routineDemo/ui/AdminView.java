package session.demo.routineDemo.ui;


import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.ironlist.IronList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

public class AdminView extends VerticalLayout {

    public static final String VIEW_NAME = "admin";
    private IronList<?> categoryList = new IronList<>();

    public AdminView() {
        add(new Label("This is adminView"));
    }
}
