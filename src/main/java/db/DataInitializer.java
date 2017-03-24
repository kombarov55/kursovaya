package db;

import dto.Category;
import dto.Item;
import dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.RandomElement;
import util.TimeGetter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.util.Calendar.DAY_OF_YEAR;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@Component
public class DataInitializer {

    @Autowired ItemDAO itemDAO;

    static Random r = new Random();

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
        CategoryEnumeration enumElement = new RandomElement(CategoryEnumeration.values()).get();
        Category category = enumElement.category;
        Shop shop = enumElement.getRandomShop();
        return new Item(category, shop, generatePrice(15, 600), generateTimeAgo(3, 365));
    }

    private int generatePrice(int base, int maxRandom) {
        return base + r.nextInt(maxRandom);
    }

    private Date generateTimeAgo(int yearSpread, int daySpread) {
        return new TimeGetter(r.nextInt(yearSpread)).addAndGet(DAY_OF_YEAR, -r.nextInt(daySpread));
    }

    private enum CategoryEnumeration {
        ENTERTAINMENT("Отдых и развлечения", "Яндекс такси", "Батутный центр"),
        SUPERMARKET("Супермаркет", "Пятерочка", "Ашан", "Дикси"),
        UTILITIES("Коммунальные платежи", "ЖКХ г. Москвы", "МТС-интернет", "Мегафон"),
        TRANSPORT("Транспорт", "Мосгортранс"),
        TRAVEL("Путешествия", "Уральские авиалинии", "РЖД", "Аэрофлот", "Тез-тур"),
        FURNITURE("Мебель", "Леруа мерлен", "Икея", "Твой дом"),
        RESTARAUNT("Кафе и рестораны", "Шоколадница", "Кофе-хауз"),
        HEALTHCARE("Здоровье", "Больница г. Москвы", "Аптека 36.6", "Доктор Столетов"),
        SPORT("Спорт", "Физика", "we-gym", "Фок");

        Category category;
        List<Shop> shops = new ArrayList<>();

        CategoryEnumeration(String name, String... shopNames) {
            category = new Category(name);
            for (String eachName : shopNames) shops.add(new Shop(eachName));
        }

        Shop getRandomShop() {
            return new RandomElement(shops).get();
        }

    }

}
