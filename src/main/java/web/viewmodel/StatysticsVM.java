package web.viewmodel;

import db.ItemDAO;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import java.util.Date;

/**
 * Created by nikolaykombarov on 23.03.17.
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StatysticsVM {

    @WireVariable ItemDAO itemDAO;

    long totalSpent;

    @AfterCompose
    public void loadData() {
        totalSpent = itemDAO.getMoneySpentBetween(new Date(), new Date());
    }

    public long getTotalSpent() {
        return totalSpent;
    }
}
