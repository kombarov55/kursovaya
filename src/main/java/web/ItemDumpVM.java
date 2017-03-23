package web;


import db.ItemDAO;
import dto.Category;
import dto.Item;
import dto.Shop;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ItemDumpVM {

    @WireVariable ItemDAO itemDAO;

    List<Item> items;
    Set<String> names = new HashSet<>();
    Set<Shop> shops = new HashSet<>();
    Set<Category> categories = new HashSet<>();

    String selectedName;
    Category selectedCategory;
    Shop selectedShop;
    int beginPrice;
    int endPrice = Integer.MAX_VALUE;
    Date beginDate = new Date(1);
    Date endDate = new Date();
    Predicate<Item> itemPredicate = item -> true;

    {
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getName(), selectedName));
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getCategory(), selectedCategory));
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getSeller(), selectedShop));
        itemPredicate = itemPredicate.and(item -> isNumberBetween(item.getPrice(), beginPrice, endPrice));
        itemPredicate = itemPredicate.and(item -> isNumberBetween(
                item.getPurchaseDate().getTime(), beginDate.getTime(), endDate.getTime()));
    }

    private boolean eqOrIsNull(Object expected, Object str) {
        return str == null || str.equals(expected);
    }

    private boolean isNumberBetween(long comparee, long begin, long end) {
        return comparee > begin && comparee < end;
    }

    @AfterCompose
    public void loadData() {
        items = itemDAO.getAll();

        for (Item each : items) {
            names.add(each.getName());
            shops.add(each.getSeller());
            categories.add(each.getCategory());
        }

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
                .filter(itemPredicate)
                .collect(Collectors.toList());
    }


    public Set<Shop> getShops() {
        return shops;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Shop getSelectedShop() {
        return selectedShop;
    }

    public void setSelectedShop(Shop selectedShop) {
        this.selectedShop = selectedShop;
    }

    public int getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(int beginPrice) {
        this.beginPrice = beginPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
