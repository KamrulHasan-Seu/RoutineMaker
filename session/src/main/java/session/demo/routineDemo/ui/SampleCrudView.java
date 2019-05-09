package session.demo.routineDemo.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import session.demo.Model.Availability;
import session.demo.Model.WorkModel;
import session.demo.routineDemo.Service.WorkModelService;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Inventory", layout = MainLayout.class)
public class SampleCrudView extends VerticalLayout
        implements HasUrlParameter<String> {

    private WorkModelForm form = new WorkModelForm(this);
    private Grid<WorkModel> grid = new Grid<>();
    private Button newProduct = new Button(new Icon(VaadinIcon.PLUS));
    private HorizontalLayout divider = new HorizontalLayout();
    private HorizontalLayout searchLayout = new HorizontalLayout();
    private TextField searchBar = new TextField("", "Search By name");
    private WorkModelService workModelService = new WorkModelService();
    public static final String VIEW_NAME = "Inventory";

    private SampleCrudLogic viewLogic = new SampleCrudLogic(this);

    public SampleCrudView() {
        title();
        topLayout();
        gridLayout();

        form.setWidth("50%");
        grid.setSizeFull();
    }

    private List<WorkModel> dataProvider() {
        List<WorkModel> dataList = new ArrayList<>();

        dataList.add(new WorkModel("2", "Plan2", "Assignment2", Availability.UPCOMING));
        dataList.add(new WorkModel("3", "Plan3", "Assignment3", Availability.COMPLETED));
        dataList.add(new WorkModel("4", "Plan4", "Assignment4", Availability.UPCOMING));

        return dataList;
    }

    private void topLayout() {

        //searchBar.addKeyPressListener(Key.KEY_F, KeyModifier.CONTROL);
        newProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProduct.addClickListener(e -> insertNewPlan());

        searchLayout.setClassName("searchLayout");
        searchLayout.setWidth("60%");
        searchLayout.expand(searchBar);
        searchLayout.add(searchBar, newProduct);
        add(searchLayout);
    }

    private void insertNewPlan() {
        grid.asSingleSelect().clear();
        form.setWorkModel(new WorkModel());
    }

    private void gridLayout() {

        form.setVisible(false);

        grid.addColumn(WorkModel::getId).setHeader("Id");
        grid.addColumn(WorkModel::getPlanName).setHeader("Name");
        grid.addColumn(WorkModel::getDescription).setHeader("Description");
        grid.addColumn(WorkModel::getAvailable).setHeader("Available");


        //grid.setItems( workModelService.getAllWork());
        grid.setItems(dataProvider());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);


        grid.asSingleSelect().addValueChangeListener(e -> {
            viewLogic.rowSelected(e.getValue());
        });

        divider.setSizeFull();
        divider.add(grid, form);
        divider.setFlexGrow(1, grid);
        add(divider);

    }

    private void title() {
        Label title = new Label("Plan");
        title.setClassName("title");
        add(title);
    }
    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }
    public void updateList() {
        grid.setItems(dataProvider().stream().filter(s -> s.getPlanName().contains(searchBar.getValue())));
    }
    public void selectRow(WorkModel row) {
        grid.getSelectionModel().select(row);
    }
    public void showForm(boolean show) {
        form.setVisible(show);

        /* FIXME The following line should be uncommented when the CheckboxGroup
         * issue is resolved. The category CheckboxGroup throws an
         * IllegalArgumentException when the form is disabled.
         */
        //form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        viewLogic.enter(parameter);
    }

    public void editProduct(WorkModel workModel) {
        showForm(workModel != null);
        form.setWorkModel(workModel);
    }
}
