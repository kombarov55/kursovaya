package db;

import dto.Category;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
public class CategoryDAO extends HibernateDaoSupport {

    @Transactional public List<Category> getAll() {
        return getHibernateTemplate().loadAll(Category.class);
    }

}
