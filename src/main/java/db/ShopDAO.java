package db;

import dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopDAO extends HibernateDaoSupport {

    @Autowired DataSource ds;

    List<Shop> cache = new ArrayList<>();

    public Shop getById(long shopId) {
        return findFromCache(shopId).orElse(getFromDb(shopId));
    }

    private Optional<Shop> findFromCache(long shopId) {
        return cache.stream().filter(each -> each.id == shopId).findAny();
    }

    private Shop getFromDb(long shopId) {
        Shop ret = getHibernateTemplate().get(Shop.class, shopId);
        cache.add(ret);
        return ret;
    }

    public Shop getByName(String name) {
        return getFromCache(name).orElse(getFromDbAndSave(name));
    }

    private Optional<Shop> getFromCache(String name) {
        return cache.stream().filter(each -> each.name.equals(name)).findAny();
    }

    private Shop getFromDbAndSave(String name) {
        Shop ret = getFromDb(name);
        cache.add(ret);
        return ret;
    }

    private Shop getFromDb(String name) {
        String sql = "select distinct id from shop where name like ?";
        Shop ret = null;
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            long id = rs.getLong(1);
            ret = new Shop(id, name);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return ret;
    }
}
