package db;

import dto.Category;
import dto.Item;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

public class ItemDAO extends HibernateDaoSupport {

    @Transactional public List<Item> getAll() {
        return getHibernateTemplate().loadAll(Item.class);
    }

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

    @Transactional public void saveAll(List<Item> items) {
        for (Item each : items)
            getHibernateTemplate().save(each);
    }

    @Transactional public long countItemsOfCategory(Category category) {
        return (long) getHibernateTemplate().execute(session -> session.createQuery("" +
                "select count(*) " +
                "from Item i " +
                "where i.category = :category")
                .setParameter("category", category)
                .list().get(0));
    }
}
