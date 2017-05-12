package db;

import dto.Shop;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopDAO extends HibernateDaoSupport {

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
}
