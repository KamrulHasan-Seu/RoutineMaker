package session.demo.routineDemo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import session.demo.Model.Availability;
import session.demo.Model.WorkModel;
import session.demo.routineDemo.Service.WorkModelService;


public class WorkModelForm extends VerticalLayout {
    private Binder<WorkModel> binder = new Binder<>(WorkModel.class);
    private WorkModel workModel = new WorkModel();
    private WorkModelService workModelService = new WorkModelService();

    private TextField id = new TextField("Id");
    private TextField plan = new TextField("Plan Name");
    private TextField desc = new TextField("Description");
    private RadioButtonGroup<Availability> availabilitySelect = new RadioButtonGroup<>();

    private Button save = new Button("Save");
    private Button discard = new Button("Discard");
    private Button delete = new Button("Delete");

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private SampleCrudView mainLayout;
    public WorkModelForm(SampleCrudView mainLayout) {

        this.mainLayout = mainLayout;

        availabilitySelect.setLabel("Availability");
        availabilitySelect.setItems(Availability.values());

        buttonLayout.add(save,discard,delete);
        add(id,plan,desc,availabilitySelect,buttonLayout);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> this.save());

        discard.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        discard.addClickListener(e ->{
            getUI().get().navigate("home");
        });

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e-> this.delete());

        binder.forField(id)
                .bind(WorkModel::getId,WorkModel::setId);
        binder.forField(plan)
                .bind(WorkModel::getPlanName,WorkModel::setPlanName);
        binder.forField(desc)
                .bind(WorkModel::getDescription,WorkModel::setDescription);
        binder.forField(availabilitySelect)
                .bind(WorkModel::getAvailable,WorkModel::setAvailable);

    }



    public void setWorkModel(WorkModel workModel){
        this.workModel = workModel;

        binder.readBean(workModel);
        delete.setVisible(workModel.isPersisted());
        setVisible(true);
    }
    private void delete() {
        workModelService.delete(workModel);
        setVisible(false);
        notifyMainView();
    }


    private void save() {
        WorkModel workModel = new WorkModel();
        try {
            binder.writeBean(workModel);
            if (workModelService.findWorkById(workModel.getId()).isPresent()) {
                workModelService.updateWork(workModel);
                binder.readBean(new WorkModel());
            } else {
                workModelService.saveWork(workModel);
                binder.readBean(new WorkModel());
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        notifyMainView();
    }

    private void notifyMainView() {
        if (mainLayout != null) {
            Notification.show("Updated");
            mainLayout.updateList();
        }

    }
}
