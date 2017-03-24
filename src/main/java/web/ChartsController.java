package web;

import db.CategoryDAO;
import db.ItemDAO;
import dto.Category;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChartsController extends SelectorComposer {

    @WireVariable ItemDAO itemDAO;
    @WireVariable CategoryDAO categoryDAO;

    @Wire Charts pie;


    @Override public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        initPie();
    }

    private void initPie() {
        Series series = pie.getSeries();
        series.setType("pie");
        series.setName("По категориям");
        for (Map.Entry<Category, Long> pair : getCategoryCountersMap().entrySet())
            series.addPoint(pair.getKey().getName(), pair.getValue());
    }

    private Map<Category, Long> getCategoryCountersMap() {
        Map<Category, Long> ret = new HashMap<>();
        for (Category category : categoryDAO.getAll())
            ret.put(category, itemDAO.countItemsOfCategory(category));
        return ret;
    }

}
