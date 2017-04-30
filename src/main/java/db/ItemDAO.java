package db;

import dto.Category;
import dto.Item;
import dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

public class ItemDAO extends HibernateDaoSupport {

    @Autowired DataSource dataSource;
    List<Category> categoriesCache = new ArrayList<>();
    List<Shop> shopsCache = new ArrayList<>();

//    @Transactional public List<Item> getAll() {
//        return getHibernateTemplate().loadAll(Item.class);
//    }

    @Transactional public long getMoneySpentBetween(Date before, Date after) {
        Long result = (Long) getHibernateTemplate().execute(session -> session.createQuery("" +
                "select sum(i.price) " +
                "from Item i " +
                "where i.purchaseDate > :dateBefore " +
                "and i.purchaseDate < :dateAfter")
                .setDate("dateBefore", before)
                .setDate("dateAfter", after)
                .list()).get(0);
        return result == null ? 0 : result;
    }

    @Transactional
    public List<Item> getAll() {
        List<Item> ret = new ArrayList<>();
        String sql = "SELECT i.price, i.purchasedate, c.name, s.name " +
                "FROM item i " +
                "  JOIN category c ON i.category_id = c.id " +
                "  JOIN shop s ON i.seller_id = s.id";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret.add(extractItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Item extractItem(ResultSet rs) throws Exception {
        int price = rs.getInt(1);
        Date purchaseDate = rs.getDate(2);
        String categoryName = rs.getString(3);
        String shopName = rs.getString(4);

        Category category = extractCategory(categoryName);
        Shop shop = extractShop(shopName);

        return new Item(category, shop, price, purchaseDate);
    }

    private Category extractCategory(String name) {
        return categoriesCache.stream().filter(category -> category.getName().equals(name)).findAny().orElseGet(() -> {
            Category category = new Category(name);
            categoriesCache.add(category);
            return category;
        });
    }

    private Shop extractShop(String name) {
        return shopsCache.stream().filter(shop -> shop.getName().equals(name)).findAny().orElseGet(() -> {
            Shop shop = new Shop(name);
            shopsCache.add(shop);
            return shop;
        });
    }

    @Transactional public void saveAll(List<Item> items) {
        for (Item each : items)
            getHibernateTemplate().save(each);
    }

    @Transactional public Map<String, Long> countItemsByCategory() {
        String sql = "select category.name, count(*)  from item join category on item.category_id = category.id group by category.name";
        Map<String, Long> categoryName_amount = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {
            while (rs.next()) {
                categoryName_amount.put(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception ignored){}
        return categoryName_amount;


//        return (long) getHibernateTemplate().execute(session -> session.createQuery("" +
//                "select count(*) " +
//                "from Item i " +
//                "where i.category = :category")
//                .setParameter("category", category)
//                .list().get(0));
    }
}
