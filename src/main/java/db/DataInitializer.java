package db;

import dto.Category;
import dto.Item;
import dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@Component
public class DataInitializer {

    @Autowired ItemDAO itemDAO;

    List<Category> categories = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    {
        categories.add(new Category("Верхняя одежда"));
        categories.add(new Category("Мобильная техника"));
        categories.add(new Category("Обувь"));
    }

    String[] coatNames = {"Ветровка", "Пуховик", "Куртка", "Пальто"};
    String[] coatCompanyNames = {"Pal Zeliery", "Boss"};
    String[] cellPhoneNames = {"Смартфон", "Планшет"};
    String[] cellPhoneCompanyNames = {"Sony", "Samsung", "Nexus", "IPhone"};
    String[] shoesNames = {"Кроссовки", "Ботинки"};
    String[] shoesCompanyNames = {"Nike", "Reebok"};
    Random r = new Random();

    public void generateDbDataIfEmpty() {
        if (itemDAO.getAll().size() == 0)
            itemDAO.addAll(generateItems(r.nextInt(1000)));
    }

    private List<Item> generateItems(int amount) {
        List<Item> ret = new ArrayList<>();
        for (int i = 0; i < amount; i++)
            ret.add(generateItem());
        return ret;
    }

    private Item generateItem() {
        Category category = categories.get(r.nextInt(categories.size()));
        String name = "";
        Shop shop = null;
        switch (category.getName()) {
            case "Верхняя одежда":
                shop = new Shop(coatCompanyNames[r.nextInt(coatCompanyNames.length)]);
                name = coatNames[r.nextInt(coatNames.length)];
                break;
            case "Мобильная техника":
                shop = new Shop(cellPhoneCompanyNames[r.nextInt(cellPhoneCompanyNames.length)]);
                name = cellPhoneNames[r.nextInt(cellPhoneNames.length)];
                break;
            case "Обувь":
                shop = new Shop(shoesCompanyNames[r.nextInt(shoesCompanyNames.length)]);
                name = shoesNames[r.nextInt(shoesNames.length)];
        }
        return new Item(name, category, shop, r.nextInt(10000), new Date(System.currentTimeMillis() - r.nextInt() * Integer.MAX_VALUE / 2));
    }


}
