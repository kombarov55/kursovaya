package web.viewmodel;


import db.ItemRepository;
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

    @WireVariable ItemRepository itemRepository;

    List<Item> items;
    Set<String> names = new HashSet<>();
    Set<Shop> shops = new HashSet<>();
    Set<Category> categories = new HashSet<>();

    String selectedName;
    Category selectedCategory;
    Shop selectedShop;
    int beginPrice;
    int endPrice;
    Date beginDate;
    Date endDate;


    Predicate<Item> itemPredicate = item -> true;

    {
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getName(), selectedName));
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getCategory(), selectedCategory));
        itemPredicate = itemPredicate.and(item -> eqOrIsNull(item.getSeller(), selectedShop));

    }

    private boolean eqOrIsNull(Object expected, Object str) {
        return str == null || str.equals(expected);
    }


    @AfterCompose
    public void loadData() {
        items = itemRepository.getAllItems();

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
