package model.dto;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class Shop {

    long id;
    String name;

    public Shop(){}

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
}
