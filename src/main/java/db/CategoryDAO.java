package db;

import dto.Category;

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
        Category category2 = new Category("Что то другое");

        db.add(category);
        db.add(category2);
    }

    public List<Category> getAll() {
        return db;
    }
}
