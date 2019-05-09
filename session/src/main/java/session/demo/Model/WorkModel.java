package session.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WorkModel {
    @Id
    private String id;
    private String planName;
    private String description;
    private Availability available;

    public boolean isPersisted() {
        return id!=null;
    }
}
