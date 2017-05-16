package dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

@Entity
public class Shop {

    @Id @GeneratedValue(strategy = AUTO)
    public long id;
    public String name;

    public Shop() {
    }

    public Shop(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Shop(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        return name != null ? name.equals(shop.name) : shop.name == null;
    }

    @Override public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
