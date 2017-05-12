package dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class UserRole {

    @Id @GeneratedValue(strategy = AUTO)
    long id = 0;
    public String role;

    public UserRole() {

    }

    public UserRole(long id, String role) {
        this.id = id;
        this.role = role;
    }

}
