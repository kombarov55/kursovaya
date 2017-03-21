package db;

import model.Item;
import model.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ItemRepository {

    Shop shop = new Shop("Pal Zelyeri");

    public List<Item> getAll() {
        List<Item> ret = new ArrayList<>();
//        ret.add(new Item("Куртка", ));
        return ret;
    }

}
