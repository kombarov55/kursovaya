package db;

import dto.Category;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
public class CategoryDAO extends HibernateDaoSupport {

    List<Category> cache = new ArrayList<>();

    @Transactional public List<Category> getAll() {
        return getHibernateTemplate().loadAll(Category.class);
    }

    @Transactional public void addAll(List<Category> categories) {
        for (Category each : categories)
            getHibernateTemplate().save(each);
    }

    @Transactional public Category getById(long id) {
        if (id == 0) return null;
        return getFromCache(id).orElse(findInDb(id));
    }

    private Optional<Category> getFromCache(long id) {
        return cache.stream().filter(each -> each.id == id).findAny();
    }

    private Category findInDb(long id) {
        Category ret = getHibernateTemplate().get(Category.class, id);
        cache.add(ret);
        return ret;
    }

}
