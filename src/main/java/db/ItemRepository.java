package db;

import model.dto.Item;
import model.dto.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ItemRepository {

    Shop shop = new Shop("Pal Zelyeri");

    public List<Item> getAll() {
        List<Item> ret = new ArrayList<>();
        return ret;
    }

}
