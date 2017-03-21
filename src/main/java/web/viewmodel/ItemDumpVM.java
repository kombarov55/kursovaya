package web.viewmodel;


import model.db.CategoryDAO;
import model.db.ItemDAO;
import model.db.ShopDAO;
import model.dto.Category;
import model.dto.Item;
import model.dto.Shop;
import org.zkoss.bind.annotation.Command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
public class ItemDumpVM {

    ItemDAO itemDao = ItemDAO.getInstance();
    ShopDAO shopDao = ShopDAO.getInstance();
    CategoryDAO categoryDao = CategoryDAO.getInstance();

    List<Item> items;
    List<Shop> shops;
    List<Category> categories;

    String selectedName;
    Category selectedCategory;
    Shop selectedShop;
    int beginPrice = 0;
    int endPrice = Integer.MAX_VALUE;
    Date beginDate = new Date(1);
    Date endDate = new Date();


    List<Predicate<Item>> predicates = new ArrayList<>();
    {
        predicates.add(item -> selectedName != null || !selectedName.isEmpty() || item.getName().equals(selectedName));
        predicates.add(item -> selectedCategory != null || item.getCategory().getName().equals(selectedCategory.getName()));
        predicates.add(item -> selectedShop != null || item.getSeller().getName().equals(selectedShop.getName()));
        predicates.add(item -> item.getPrice() > beginPrice && item.getPrice() < endPrice);
        predicates.add(item ->  item.getPurchaseDate().getTime() > beginDate.getTime() & item.getPurchaseDate().getTime() < endDate.getTime());
    }

    public ItemDumpVM() {
        //TODO: тут нужно управлять транзакцией
        items = itemDao.getAll();
        shops = shopDao.getAll();
        categories = categoryDao.getAll();
    }


    @Command
    public void applyFilter() {
        items = items.stream()
                .filter(predicates.get(0))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ItemDumpVM vm = new ItemDumpVM();
        List<Item> unfiltered = vm.getItems();
        System.out.println(unfiltered);
        vm.selectedName = "НеКуртка";
        vm.applyFilter();
        List<Item> filtered = vm.getItems();
        System.out.println(filtered);
    }


    public List<String> getNames() {
        return items.stream()
                .map(Item::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void setSelectedShop(Shop selectedShop) {
        this.selectedShop = selectedShop;
    }

    public void setBeginPrice(int beginPrice) {
        this.beginPrice = beginPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
