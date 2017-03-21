package model.db;

import model.dto.Category;
import model.dto.Item;
import model.dto.Shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ItemRepository {

    private static ItemRepository instance = new ItemRepository();
    private ItemRepository() {}
    public static ItemRepository getInstance() {
        return instance;
    }

    List<Item> items = new ArrayList<>();

    {
        Shop shop = new Shop("Pal Zelyeri");
        Category category = new Category("Верхняя одежда");
        items.add(new Item("Куртка", category, shop, 1488, new Date()));
    }

    public List<Item> getAll() {
        return items;
    }

}
