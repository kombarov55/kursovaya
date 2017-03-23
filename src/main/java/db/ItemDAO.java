package db;

import dto.Category;
import dto.Item;
import dto.Shop;
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
        return (long) getHibernateTemplate().execute(session -> session.createQuery("select sum(i.price) from Item i").list()).get(0);
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
