package web;

import db.ClientDAO;
import db.ItemDAO;
import dto.Client;
import dto.Item;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import util.CategoryListener;
import util.ChartsNotifier;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChartsController extends SelectorComposer implements CategoryListener {

    @WireVariable ItemDAO itemDAO;
    @WireVariable ClientDAO clientDAO;
    @WireVariable ChartsNotifier chartsNotifier;

    @Wire Charts pie;

    Series series;


    @Override public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        initPie();
        chartsNotifier.addListener(this);
    }

    public void initPie() {
        Client client = clientDAO.findByUsername((String) ((HttpSession) (Executions.getCurrent()).getDesktop().getSession().getNativeSession()).getAttribute("username"));
        series = pie.getSeries();
        series.setType("pie");
        series.setName("По категориям");
        for (Map.Entry<String, Long> pair : itemDAO.countItemsByCategory(client).entrySet())
            series.addPoint(pair.getKey(), pair.getValue());
    }

    @Override public void onChangeCategory(Item item) {
        boolean pointFound = false;
        try {
            tryToIncrementExistingPoint(item);
        } catch (Exception e) {
            createNewPoint(item);
        }
    }

    private void tryToIncrementExistingPoint(Item item) {
        for (int i = 0; ; ++i) {
            Point point = series.getPoint(i);
            if (point.getName().equals(item.category.name)) {
                point.setY(point.getY().intValue() + 1);
                break;
            }
        }
    }

    private void createNewPoint(Item item) {
        series.addPoint(item.category.name, 1);
    }


}
