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


    List<Category> categories;

    Category selectedCategory;
    String selectedShopName;
    Integer beginPrice;
    Integer endPrice;
    Date beginDate;
    Date endDate;

    @AfterCompose
    public void loadData() {
        categories = categoryDAO.getAll();
    }

    @Command @NotifyChange("items")
    public void notifyItemsAboutFilter() {
    }

    public List<Item> getItems() {
        return itemDAO.getByFilter(selectedCategory == null ? "" : selectedCategory.getName(), selectedShopName, beginPrice, endPrice, beginDate, endDate);
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

    public Integer getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(Integer beginPrice) {
        this.beginPrice = beginPrice;
    }

    public Integer getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Integer endPrice) {
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
