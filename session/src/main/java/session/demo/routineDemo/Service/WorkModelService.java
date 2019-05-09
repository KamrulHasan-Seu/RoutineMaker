package session.demo.routineDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import session.demo.Model.WorkModel;

import java.util.List;
import java.util.Optional;

@Service
public class WorkModelService {
    @Autowired
    private ModelRepository modelRepository ;


    public WorkModel saveWork(WorkModel workModel) {
        return modelRepository.save(workModel);
    }

    public List<WorkModel> getAllWork()  {
        return modelRepository.findAll();
    }

    public Optional<WorkModel> findWorkById(String id) {
        return modelRepository.findById(id);
    }

    public WorkModel updateWork(WorkModel workModel) {
        return modelRepository.save(workModel);
    }

    public void deleteById(String id) {
        modelRepository.deleteById(id);
    }
    public void delete(WorkModel workModel){
        modelRepository.delete(workModel);
    }

    public WorkModel getWorkById(String productId) {
        return  modelRepository.findWorkModelById(productId);
    }

//    public void findModelByPlanName(String name) {
////         modelRepository.findWorkModelByPlanName(name);
////    }
}
