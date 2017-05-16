package db;

import dto.Category;
import dto.Client;
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
    @Autowired ClientDAO clientDAO;
    @Autowired CategoryDAO categoryDAO;
    @Autowired ShopDAO shopDAO;

    @Transactional public int getMoneySpentBetween(Date before, Date after, Client client) {
        int ret = 0;
        String sql = "SELECT sum(price) FROM item WHERE purchaseDate > ? AND purchaseDate < ? AND client_id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, parseDate(before));
            ps.setDate(2, parseDate(after));
            ps.setLong(3, client.id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ret = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return ret;
    }


    @Transactional public int countAll() {
        String sql = "SELECT count(*) FROM item";
        int ret = 1;
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            ret = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return ret;
    }

    @Transactional public List<Item> getByFilter(String categoryName, String shopName, Integer beginPrice, Integer endPrice, Date from, Date to, Client client) {
        List<Item> ret = new ArrayList<>();
        String sql = "SELECT i.price, i.purchasedate, c.id, s.id " +
                "FROM item i " +
                "  LEFT JOIN category c ON i.category_id = c.id " +
                "  LEFT JOIN shop s ON i.seller_id = s.id " +
                "WHERE " +
                "(c IS NULL OR c.name LIKE ? || '%') " +
                "AND " +
                "(s IS NULL OR  s.name LIKE ? || '%') " +
                "AND " +
                "i.price > ? " +
                "AND " +
                "i.price < ? " +
                "AND " +
                "i.purchasedate > ? " +
                "AND " +
                "i.purchasedate < ? " +
                "ORDER BY i.purchasedate DESC";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, substituteIfNeeded(categoryName, ""));
            ps.setString(2, substituteIfNeeded(shopName, ""));
            ps.setInt(3, substituteIfNeeded(beginPrice, 0));
            ps.setInt(4, substituteIfNeeded(endPrice, 99999999));
            ps.setDate(5, substituteIfNeeded(parseDate(from), new java.sql.Date(1)));
            ps.setDate(6, substituteIfNeeded(parseDate(to), parseDate(new Date())));
//            ps.setLong(7, client.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret.add(extractItem(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Transactional public void saveAll(List<Item> items) {
        for (Item each : items)
            getHibernateTemplate().save(each);
    }

    @Transactional public Map<String, Long> countItemsByCategory(Client client) {
        String sql = "" +
                "SELECT " +
                "   category.name, count(*) " +
                "FROM item " +
                "JOIN category ON item.category_id = category.id  " +
                "WHERE " +
                "client_id = ?" +
                "GROUP BY category.name ";
        Map<String, Long> categoryName_amount = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, client.id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categoryName_amount.put(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
            System.out.println(ignored.getMessage());
        }
        return categoryName_amount;
    }

    @Transactional public void update(Item item) {
        getHibernateTemplate().saveOrUpdate(item);
    }

    private Item extractItem(ResultSet rs) throws Exception {
        int price = rs.getInt(1);
        Date purchaseDate = rs.getDate(2);
        long categoryId = rs.getLong(3);
        long shopId = rs.getLong(4);
//        long clientId = rs.getLong(5);

        Category category = categoryDAO.getById(categoryId);
        Shop shop = shopDAO.getById(shopId);
//        Client client = clientDAO.getById(clientId);
        Client client = clientDAO.findByUsername("admin@mail.ru");

        return new Item(category, shop, price, purchaseDate, client);
    }

    private java.sql.Date parseDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    private <T> T substituteIfNeeded(T original, T substitution) {
        return original == null ? substitution : original;
    }
}
