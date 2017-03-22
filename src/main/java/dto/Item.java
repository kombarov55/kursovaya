package dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
@Entity
public class Item {

    @Id @GeneratedValue(strategy = AUTO)
    long id;
    String name;

    @ManyToOne(cascade = ALL) Category category;
    @ManyToOne(cascade = ALL) Shop seller;
    int price;
    Date purchaseDate;

    public Item() {
    }

    public Item(String name, Category category, Shop seller, int price, Date purchaseDate) {
        this.name = name;
        this.category = category;
        this.seller = seller;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSeller(Shop seller) {
        this.seller = seller;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Shop getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

}
