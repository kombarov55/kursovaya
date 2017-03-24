package web;

import db.ItemDAO;
import dto.Category;
import dto.Item;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import sun.java2d.xr.MutableInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChartsController extends SelectorComposer {

    @WireVariable ItemDAO itemDAO;
    @Wire Charts pie;


    @Override public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        initPie();
    }

    private void initPie() {
        Series series = pie.getSeries();
        series.setType("pie");
        series.setName("По категориям");
        for (Map.Entry<Category, MutableInteger> pair : getCategoryCounters(itemDAO.getAll()).entrySet())
            series.addPoint(pair.getKey().getName(), pair.getValue().getValue());
    }

    private Map<Category, MutableInteger> getCategoryCounters(List<Item> items) {
        Map<Category, MutableInteger> ret = new HashMap<>();
        for (Item item : items) {
            MutableInteger newValue = new MutableInteger(1);
            MutableInteger oldValue = ret.put(item.getCategory(), newValue);
            if (oldValue != null) newValue.setValue(oldValue.getValue() + 1);
        }
        return ret;
    }

}
