package db;

import dto.Category;
import dto.Item;
import dto.Shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ItemDAO {

    private ItemDAO() {}
    private static ItemDAO instance = new ItemDAO();
    public static ItemDAO getInstance() {
        return instance;
    }

    List<Item> items = new ArrayList<>();

    {
        Shop shop = new Shop("Pal Zelyeri");
        Category category = new Category("Верхняя одежда");
        Category category2 = new Category("Что то другое");
        Item item = new Item("Куртка", category, shop, 1488, new Date());
        Item item2 = new Item("Не Куртка", category2, shop, 1488, new Date());
        for (int i = 0; i < 5; i++) {
            items.add(item);
        }
        items.add(item2);
    }

    public List<Item> getAll() {
        return items;
    }

}
