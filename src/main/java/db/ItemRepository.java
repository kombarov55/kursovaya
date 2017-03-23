package db;

import dto.Item;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nikolaykombarov on 23.03.17.
 */
@Repository
public class ItemRepository {

    @Autowired ItemDAO itemDAO;

    @Transactional public List<Item> getAllItems() {
        return itemDAO.getAll();
    }

}
