package web;


import db.CategoryDAO;
import db.DataInitializer;
import db.ItemDAO;
import dto.Category;
import dto.Item;
import dto.Shop;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import util.TimeGetter;

import java.util.Calendar;
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

    //TODO: куда его поставить?
    @WireVariable DataInitializer dataInitializer;

    @WireVariable ItemDAO itemDAO;
    @WireVariable CategoryDAO categoryDAO;



    List<Item> items;
    List<Category> categories;

    String selectedName;
    Category selectedCategory;
    String selectedShopName;
    String beginPrice;
    String endPrice;
    Date beginDate;
    Date endDate;

    Predicate<Item> itemPredicate = item ->
            eqOrIsNull(item.getName(), selectedName) &&
                    eqOrIsNull(item.getCategory(), selectedCategory) &&
                    eqOrIsNull(item.getSeller().getName(), selectedShopName) &&
                    isNumberBetween(item.getPrice(), beginPrice, endPrice) &&
                    (beginDate == null || item.getPurchaseDate().getTime() > beginDate.getTime()) &&
                    (endDate == null || item.getPurchaseDate().getTime() < endDate.getTime());

    private boolean eqOrIsNull(Object expected, Object got) {
        return got == null || got.equals(expected);
    }

    private boolean eqOrIsNull(String expected, String got) {
        return got == null || got.isEmpty() || got.equalsIgnoreCase(expected);
    }

    private boolean isNumberBetween(long comparee, long begin, long end) {
        return comparee > begin && comparee < end;
    }

    private boolean isNumberBetween(long comparee, String beginStr, String endStr) {
        return isNumberBetween(
                comparee,
                beginStr == null || beginStr.isEmpty() ? 0 :  Long.parseLong(beginStr),
                endStr == null || endStr.isEmpty() ? Long.MAX_VALUE : Long.parseLong(endStr));
    }



    @AfterCompose
    public void loadData() {
        dataInitializer.generateDbDataIfEmpty();
        items = itemDAO.getAll();
        categories = categoryDAO.getAll();
    }

    @Command @NotifyChange("items")
    public void notifyItemsAboutFilter() {
    }

    public List<Item> getItems() {
        return items.stream()
                .filter(itemPredicate)
                .collect(Collectors.toList());
    }

    public List<Category> getCategories() {
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

    public String getSelectedShopName() {
        return selectedShopName;
    }

    public void setSelectedShopName(String selectedShopName) {
        this.selectedShopName = selectedShopName;
    }

    public String getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(String beginPrice) {
        this.beginPrice = beginPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
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
