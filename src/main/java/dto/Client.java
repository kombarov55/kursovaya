package dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Client {

    @Id @GeneratedValue(strategy = AUTO)
    public long id = 0;
    public String username, password;
    public @ManyToOne UserRole userRole;

    public Client() {

    }

    public Client(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public Client(long id, String username, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
