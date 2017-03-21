package model;

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
}
