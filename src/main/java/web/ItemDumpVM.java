package web;


import db.CategoryDAO;
import db.ClientDAO;
import db.ItemDAO;
import dto.Category;
import dto.Client;
import dto.Item;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by nikolaykombarov on 21.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ItemDumpVM {

    @WireVariable ItemDAO itemDAO;
    @WireVariable CategoryDAO categoryDAO;
    @WireVariable ClientDAO clientDAO;


    List<Category> categories;

    Client client;

    Category selectedCategory;
    String selectedShopName;
    Integer beginPrice;
    Integer endPrice;
    Date beginDate;
    Date endDate;

    @AfterCompose
    public void loadData() {
        categories = categoryDAO.getAll();
        String username = (String) ((HttpSession)(Executions.getCurrent()).getDesktop().getSession().getNativeSession()).getAttribute("username");
        client = clientDAO.findByUsername(username);
    }

    @Command @NotifyChange("items")
    public void notifyItemsAboutFilter() {
    }

    public List<Item> getItems() {
        return itemDAO.getByFilter(selectedCategory == null ? "" : selectedCategory.getName(), selectedShopName, beginPrice, endPrice, beginDate, endDate, client);
    }

    @GlobalCommand
    public void onCategoryChanged(@BindingParam("item") Item item) {
        itemDAO.update(item);
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
