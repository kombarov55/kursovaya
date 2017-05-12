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
    long id = 0;

    @ManyToOne(cascade = ALL) Category category = null;
    @ManyToOne(cascade = ALL) Shop seller = null;
    @ManyToOne Client client;
    int price = 0;
    Date purchaseDate = new Date();

    public Item() {
    }

    public Item(Category category, Shop seller, int price, Date purchaseDate, Client client) {
        this.category = category;
        this.seller = seller;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.client = client;
    }

    public void setId(long id) {
        this.id = id;
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
