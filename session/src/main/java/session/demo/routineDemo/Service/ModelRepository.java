package session.demo.routineDemo.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import session.demo.Model.WorkModel;


@Repository
public interface ModelRepository extends JpaRepository<WorkModel,String> {
    WorkModel findWorkModelById(String id);
}
