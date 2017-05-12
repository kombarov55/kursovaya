package db;

import dto.Category;
import dto.Client;
import dto.Item;
import dto.Shop;
import dto.UserRole;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.TimeGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Calendar.DAY_OF_YEAR;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@Component
public class DataInitializer implements InitializingBean {

    @Autowired ItemDAO itemDAO;
    @Autowired ClientDAO clientDAO;
    @Autowired UserRoleDAO userRoleDAO;
    @Autowired CategoryDAO categoryDAO;

    Client client;

    static Random r = new Random();

    @Override public void afterPropertiesSet() throws Exception {
        if (itemDAO.countAll() == 0) {
            generateAndSaveClient();
            clientDAO.save(client);
            saveAllShops();
            saveAllCategories();
            itemDAO.saveAll(generateItemList(r.nextInt(1000)));
        }
    }

    private void generateAndSaveClient() {
        Client client = new Client("admin@mail.ru", "password", userRoleDAO.getByName("user"));
    }

    private void saveAllShops() {
        List<Shop> shops = new ArrayList<>();
        for (CategoryEnumeration each : CategoryEnumeration.values()) {
            shops.addAll(each.shops);
        }
    }

    private void saveAllCategories() {
        categoryDAO.addAll(Arrays.stream(CategoryEnumeration.values()).map(each -> each.category).collect(Collectors.toList()));
    }

    private List<Item> generateItemList(int amount) {
        List<Item> ret = new ArrayList<>();
        for (int i = 0; i < amount; i++)
            ret.add(generateItem());
        return ret;
    }

    private Item generateItem() {
        CategoryEnumeration enumElement = CategoryEnumeration.getPseudoRandomElement();
        Category category = enumElement.category;
        Shop shop = enumElement.getRandomShop();
        return new Item(null, shop, generatePrice(15, 600), generateTimeAgo(3, 365), client);
    }

    private int generatePrice(int base, int maxRandom) {
        return base + r.nextInt(maxRandom);
    }

    private Date generateTimeAgo(int yearSpread, int daySpread) {
        return new TimeGetter(r.nextInt(yearSpread)).addAndGet(DAY_OF_YEAR, -r.nextInt(daySpread));
    }

    private enum CategoryEnumeration {
        ENTERTAINMENT("Отдых и развлечения", 10, "Яндекс такси", "Батутный центр"),
        SUPERMARKET("Супермаркет", 30, "Пятерочка", "Ашан", "Дикси"),
        UTILITIES("Коммунальные платежи", 8, "ЖКХ г. Москвы", "МТС-интернет", "Мегафон"),
        TRANSPORT("Транспорт", 10, "Мосгортранс"),
        TRAVEL("Путешествия", 3, "Уральские авиалинии", "РЖД", "Аэрофлот", "Тез-тур"),
        FURNITURE("Мебель", 2, "Леруа мерлен", "Икея", "Твой дом"),
        RESTARAUNT("Кафе и рестораны", 8, "Шоколадница", "Кофе-хауз"),
        HEALTHCARE("Здоровье", 2, "Больница г. Москвы", "Аптека 36.6", "Доктор Столетов"),
        SPORT("Спорт", 2, "Физика", "we-gym", "Фок");

        public Category category;
        public List<Shop> shops = new ArrayList<>();
        int weight;

        static Random random = new Random();
        static int weightSum = Arrays.stream(values()).mapToInt(elem -> elem.weight).sum();

        CategoryEnumeration(String name, int weight, String... shopNames) {
            category = new Category(name);
            this.weight = weight;
            for (String eachName : shopNames) shops.add(new Shop(eachName));
        }

        Shop getRandomShop() {
            return shops.get(random.nextInt(shops.size()));
        }

        static CategoryEnumeration getPseudoRandomElement() {
            int randomNumber = random.nextInt(weightSum);
            int localSum = 0;
            for (CategoryEnumeration elem : values()) {
                localSum += elem.weight;
                if (randomNumber < localSum) return elem;
            }
            return null;
        }

    }

}
