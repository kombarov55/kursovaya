package db;

import dto.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ShopDAO {

    private static ShopDAO ourInstance = new ShopDAO();
    public static ShopDAO getInstance() {
        return ourInstance;
    }
    private ShopDAO() {}

    List<Shop> db = new ArrayList<>();

    {
        Shop shop = new Shop("Pal Zelyeri");
        db.add(shop);
    }

    public List<Shop> getAll() {
        return db;
    }
}
