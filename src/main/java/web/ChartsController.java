package web;

import db.ClientDAO;
import db.ItemDAO;
import dto.Client;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Series;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by nikolaykombarov on 24.03.17.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChartsController extends SelectorComposer {

    @WireVariable ItemDAO itemDAO;
    @WireVariable ClientDAO clientDAO;

    @Wire Charts pie;


    @Override public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        initPie();
    }

    private void initPie() {
        Client client = clientDAO.findByUsername((String) ((HttpSession)(Executions.getCurrent()).getDesktop().getSession().getNativeSession()).getAttribute("username"));
        Series series = pie.getSeries();
        series.setType("pie");
        series.setName("По категориям");
        for (Map.Entry<String, Long> pair : itemDAO.countItemsByCategory(client).entrySet())
            series.addPoint(pair.getKey(), pair.getValue());
    }

}
