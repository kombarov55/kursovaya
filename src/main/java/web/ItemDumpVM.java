package web;


import db.CategoryDAO;
import db.DataInitializer;
import db.ItemDAO;
import dto.Category;
import dto.Item;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ItemDumpVM {

    @WireVariable ItemDAO itemDAO;
    @WireVariable CategoryDAO categoryDAO;


    List<Item> items;
    List<Category> categories;

    Category selectedCategory;
    String selectedShopName;
    String beginPrice;
    String endPrice;
    Date beginDate;
    Date endDate;

    @AfterCompose
    public void loadData() {
        items = itemDAO.getAll();
        categories = categoryDAO.getAll();
    }

    @Command @NotifyChange("items")
    public void notifyItemsAboutFilter() {
    }

    Predicate<Item> itemPredicate = item ->
            eqOrIsNull(item.getCategory(), selectedCategory) &&
                    eqOrIsNull(item.getSeller().getName(), selectedShopName) &&
                    isNumberBetween(item.getPrice(), beginPrice, endPrice) &&
                    (beginDate == null || item.getPurchaseDate().getTime() > beginDate.getTime()) &&
                    (endDate == null || item.getPurchaseDate().getTime() < endDate.getTime());

    private boolean eqOrIsNull(Object expected, Object got) {
        return got == null || got.equals(expected);
    }

    private boolean eqOrIsNull(String expected, String got) {
        return got == null || got.isEmpty() || areSimilar(expected, got);
    }

    private boolean areSimilar(String expected, String got) {
        return expected.toLowerCase().matches(".*" + got.toLowerCase() + ".*");
    }

    private boolean isNumberBetween(int comparee, int begin, int end) {
        return comparee > begin && comparee < end;
    }

    private boolean isNumberBetween(int comparee, String beginStr, String endStr) {
        return isNumberBetween(comparee,
                parseIntOrDefault(beginStr, 0), parseIntOrDefault(endStr, Integer.MAX_VALUE));
    }

    private int parseIntOrDefault(String str, int defaultValue) {
        return str == null || str.isEmpty() ? defaultValue : Integer.parseInt(str);
    }

    public List<Item> getItems() {
        return items.stream()
                .filter(itemPredicate)
                .collect(toList());
    }

    public List<Category> getCategories() {
        return categories;
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
