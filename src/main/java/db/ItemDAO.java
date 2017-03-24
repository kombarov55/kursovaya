package db;

import dto.Category;
import dto.Item;
import dto.Shop;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

public class ItemDAO extends HibernateDaoSupport {

    @Transactional public List<Item> getAll() {
        return getHibernateTemplate().loadAll(Item.class);
    }

    @Transactional public long getMoneySpentBetween(Date before, Date after) {
        return (long) getHibernateTemplate().execute(session -> session.createQuery(
                "select sum(i.price) " +
                        "from Item i " +
                        "where i.purchaseDate > :dateBefore " +
                        "and i.purchaseDate < :dateAfter")
                .setDate("dateBefore", before)
                .setDate("dateAfter", after)
                .list()).get(0);
    }



    @Transactional public void addAll(List<Item> items) {
        for (Item each : items)
            getHibernateTemplate().save(each);
    }
}
