package web.viewmodel;


import model.db.CategoryDAO;
import model.db.ItemDAO;
import model.db.ShopDAO;
import model.dto.Category;
import model.dto.Item;
import model.dto.Shop;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    String selectedName = "";
    Category selectedCategory;
    Shop selectedShop;
    int beginPrice = 0;
    int endPrice = Integer.MAX_VALUE;
    Date beginDate = new Date(1);
    Date endDate = new Date();


    List<Predicate<Item>> appliedFilters = new ArrayList<>();
    {
        appliedFilters.add(item -> selectedName == null || selectedName.isEmpty() || item.getName().equalsIgnoreCase(selectedName));
        appliedFilters.add(item -> selectedCategory == null || selectedCategory == null || selectedCategory.getName().equalsIgnoreCase(item.getCategory().getName()));
        appliedFilters.add(item -> selectedShop == null || selectedShop.getName().equalsIgnoreCase(item.getSeller().getName()));
    }




    public ItemDumpVM() {
        //TODO: тут нужно управлять транзакцией
        items = itemDao.getAll();
        shops = shopDao.getAll();
        categories = categoryDao.getAll();
    }


    @Command @NotifyChange("items")
    public void notifyItemsAboutFilter() {
    }

    public List<String> getNames() {
        return items.stream()
                .map(Item::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Item> getItems() {
        return items.stream()
                .filter(getANDPredicate())
                .collect(Collectors.toList());
    }

    private Predicate<Item> getANDPredicate() {
        Predicate<Item> ret = p -> true;
        for (Predicate<Item> elem : appliedFilters)
            ret = ret.and(elem);
        return ret;
    }

    private Predicate<Item> getORPredicate() {
        Predicate<Item> ret = p -> true;
        for (Predicate<Item> elem : appliedFilters)
            ret = ret.or(elem);
        return ret;
    }


    public List<Shop> getShops() {
        return shops;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public Shop getSelectedShop() {
        return selectedShop;
    }

    public int getBeginPrice() {
        return beginPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
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
