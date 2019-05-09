package session.demo.routineDemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import session.demo.Model.WorkModel;
import session.demo.routineDemo.Service.WorkModelService;

import java.util.Optional;


@RestController
@RequestMapping(value = "api/model")
public class Controller {

    @Autowired
    private WorkModelService workModelService;


        @GetMapping(value = "all")
        public ResponseEntity<?> getAllWork() {
            return ResponseEntity.status(HttpStatus.OK).body(workModelService.getAllWork());
        }

        @GetMapping(value = "{id}")
        public ResponseEntity<?> getWorkById(@PathVariable String id) {

            Optional<WorkModel> work =  workModelService.findWorkById(id);

            if (work.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(work.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Work not present for this matchId!");
            }
        }

        @PostMapping(value = "save")
        public ResponseEntity<?> saveWork(@RequestBody WorkModel workModel) {

            Optional<WorkModel>optionalWorkModel= workModelService.findWorkById(workModel.getId());

            if (optionalWorkModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Work is already exist!");
            }

            try {
                WorkModel workModel1 =  workModelService.saveWork(workModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(workModel1);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing Saved!");
            }

        }

        @PutMapping(value = "update/{id}")
        public ResponseEntity<?> updateWork(@PathVariable String id, @RequestBody WorkModel workModel) {

            Optional<WorkModel> optionalWorkModel = workModelService.findWorkById(id);

            if (!optionalWorkModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Work Id!");
            }

            optionalWorkModel.get().setId(workModel.getId());
            optionalWorkModel.get().setPlanName(workModel.getPlanName());
            optionalWorkModel.get().setDescription(workModel.getDescription());
            optionalWorkModel.get().setAvailable(workModel.getAvailable());

            try {
                WorkModel workModel1 = workModelService.updateWork(workModel);
                return ResponseEntity.status(HttpStatus.OK).body(workModel1);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing updated!");
            }

        }

        @DeleteMapping(value = "delete/{id}")
        public ResponseEntity<?> deleteWork(@PathVariable String id) {

            Optional<WorkModel> optionalWorkModel = workModelService.findWorkById(id);

            if (!optionalWorkModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid  Id!");
            }

            try {
                workModelService.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing Deleted!");
            }

        }

}
