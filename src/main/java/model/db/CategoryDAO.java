package model.db;

import model.dto.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class CategoryDAO {

    private static CategoryDAO ourInstance = new CategoryDAO();
    public static CategoryDAO getInstance() {
        return ourInstance;
    }
    private CategoryDAO() {}

    List<Category> db = new ArrayList<>();

    {
        Category category = new Category("Верхняя одежда");
        db.add(category);
    }

    public List<Category> getAll() {
        return db;
    }
}
