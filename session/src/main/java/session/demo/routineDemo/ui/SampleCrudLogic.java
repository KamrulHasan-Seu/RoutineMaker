package session.demo.routineDemo.ui;

import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import session.demo.Model.WorkModel;
import session.demo.routineDemo.Service.ModelRepository;
import session.demo.routineDemo.security.AccessControl;
import session.demo.routineDemo.security.AccessControlFactory;

public class SampleCrudLogic {
    private SampleCrudView view;
    @Autowired
    private ModelRepository modelRepository;


    public SampleCrudLogic(SampleCrudView simpleCrudView) {
        view = simpleCrudView;
    }
    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newWork();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    String pid = productId;
                    WorkModel workModel = modelRepository.findWorkModelById(pid);
                    view.selectRow(workModel);
                } catch (Exception e) {
                }
            }
        } else {
            view.showForm(false);
        }
    }
    public void newWork() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new WorkModel());
    }
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        UI.getCurrent().navigate(SampleCrudView.class, fragmentParameter);
    }
    public void rowSelected(WorkModel workModel) {
        if (AccessControlFactory.getInstance().createAccessControl()
                .isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
            editProduct(workModel);
        }
    }
    public void editProduct(WorkModel product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editProduct(product);
    }
}
