package dto;

import java.util.Date;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

public class Item {

    long id;
    String name;
    Category category;
    Shop seller;
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

    @Override public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", seller=" + seller +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}
