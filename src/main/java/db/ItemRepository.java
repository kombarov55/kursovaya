package db;

import model.Item;
import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
@Repository
public class ItemRepository {

    Shop shop = new Shop("Pal Zelyeri");

    public List<Item> getAll() {
        List<Item> ret = new ArrayList<>();
//        ret.add(new Item("Куртка", ));
        return ret;
    }

}
