package db;

import dto.Category;
import dto.Item;
import dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.TimeGetter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.util.Calendar.*;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@Component
public class DataInitializer {

    @Autowired ItemDAO itemDAO;

    //TODO: магазины тоже должны создаваться тут
    List<Category> categories = new ArrayList<>();
    List<Shop> shops = new ArrayList<>();

    String[] coatNames = {"Ветровка", "Пуховик", "Куртка", "Пальто"};
    String[] coatCompanyNames = {"Pal Zeliery", "Boss"};
    String[] cellPhoneNames = {"Смартфон", "Планшет"};
    String[] cellPhoneCompanyNames = {"Sony", "Samsung", "Nexus", "IPhone"};
    String[] shoesNames = {"Кроссовки", "Ботинки"};
    String[] shoesCompanyNames = {"Nike", "Reebok"};
    Random r = new Random();

    {
        categories.add(new Category("Верхняя одежда"));
        categories.add(new Category("Мобильная техника"));
        categories.add(new Category("Обувь"));

        for (String shopName : coatCompanyNames) shops.add(new Shop(shopName));
        for (String shopName : cellPhoneCompanyNames) shops.add(new Shop(shopName));
        for (String shopName : shoesCompanyNames) shops.add(new Shop(shopName));
    }

    public void generateDataIfEmpty() {
        if (itemDAO.getAll().size() == 0) itemDAO.saveAll(generateItemList(r.nextInt(1000)));
    }

    private List<Item> generateItemList(int amount) {
        List<Item> ret = new ArrayList<>();
        for (int i = 0; i < amount; i++)
            ret.add(generateItem());
        return ret;
    }

    private Item generateItem() {
        String name = "";
        Shop shop = null;
        Category category = randomValueFrom(categories);
        switch (category.getName()) {
            case "Верхняя одежда":
                name = randomValueFrom(coatNames);
                shop = getShopByName(randomValueFrom(coatCompanyNames));
                break;
            case "Мобильная техника":
                name = randomValueFrom(cellPhoneNames);
                shop = getShopByName(randomValueFrom(cellPhoneCompanyNames));
                break;
            case "Обувь":
                name = randomValueFrom(shoesNames);
                shop = getShopByName(randomValueFrom(shoesCompanyNames));
        }
        return new Item(name, category, shop, getRandomPrice(15, 500), getRandomDateAgo());
    }

    private <T> T randomValueFrom(T[] array) {
        return array[r.nextInt(array.length)];
    }

    private <T> T randomValueFrom(List<T> list) {
        return list.get(r.nextInt(list.size()));
    }

    private int getRandomPrice(int base, int maxRandom) {
        return base + r.nextInt(maxRandom);
    }

    private Date getRandomDateAgo() {
        return new TimeGetter(r.nextInt(3)).addAndGet(DAY_OF_YEAR, - r.nextInt(365));
    }

    private Shop getShopByName(String name) {
        return shops.stream()
                .filter(shop -> shop.getName().equals(name))
                .findFirst().get();
    }

}
