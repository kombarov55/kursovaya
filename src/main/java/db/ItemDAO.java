package db;

import dto.Item;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */

@Repository
public class ItemDAO {

    private static ItemDAO instance = new ItemDAO();
    private ItemDAO() {}
    public static ItemDAO getInstance() {
        return instance;
    }

    @Autowired HibernateUtil hibUtil;

    public List<Item> getAll() {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        List<Item> ret = session.createQuery("from Item").list();
        session.getTransaction().commit();
        return ret;
    }

//    List<Item> items = new ArrayList<>();
//    List<Category> categories = new ArrayList<>();
//    String[] coatNames = {"Ветровка", "Пуховик", "Куртка", "Пальто"};
//    String[] coatCompanyNames = {"Pal Zeliery", "Boss"};
//    String[] cellPhoneNames = {"Смартфон", "Планшет"};
//    String[] cellPhoneCompanyNames = {"Sony", "Samsung", "Nexus", "IPhone"};
//    String[] shoesNames = {"Кроссовки", "Ботинки"};
//    String[] shoesCompanyNames = {"Nike", "Reebok"};
//    Random r = new Random();
//    public Item generateItem() {
//        Category category = categories.get(r.nextInt(categories.size()));
//        String name = "";
//        Shop shop = null;
//        switch (category.getName()) {
//            case "Верхняя одежда":
//                shop = new Shop(coatCompanyNames[r.nextInt(coatCompanyNames.length)]);
//                name = coatNames[r.nextInt(coatNames.length)];
//                break;
//            case "Мобильная техника":
//                shop = new Shop(cellPhoneCompanyNames[r.nextInt(cellPhoneCompanyNames.length)]);
//                name = cellPhoneNames[r.nextInt(cellPhoneNames.length)];
//                break;
//            case "Обувь":
//                shop = new Shop(shoesCompanyNames[r.nextInt(shoesCompanyNames.length)]);
//                name = shoesNames[r.nextInt(shoesNames.length)];
//        }
//        return new Item(name, category, shop, r.nextInt(10000) + 1000, new Date(System.currentTimeMillis() - r.nextInt() * 10000));
//    }


}
