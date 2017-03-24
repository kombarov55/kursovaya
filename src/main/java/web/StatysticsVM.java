package web;

import db.ItemDAO;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import util.TimeGetter;

import java.util.Date;

import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.YEAR;

/**
 * Created by nikolaykombarov on 23.03.17.
 */

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class StatysticsVM {

    @WireVariable ItemDAO itemDAO;

    long totalSpent;
    long lastDaySpent;
    long lastWeekSpent;
    long lastMonthSpent;
    long lastYearSpent;

    public StatysticsVM() {
    }


    @AfterCompose
    public void loadData() {
        TimeGetter timeGetter = new TimeGetter();
        totalSpent = itemDAO.getMoneySpentBetween(new Date(1), timeGetter.getTodaysMidnight());
        lastDaySpent = itemDAO.getMoneySpentBetween(timeGetter.addAndGet(DAY_OF_YEAR, -1), timeGetter.getTodaysMidnight());
        lastWeekSpent = itemDAO.getMoneySpentBetween(timeGetter.addAndGet(WEEK_OF_YEAR, -1), timeGetter.getTodaysMidnight());
        lastMonthSpent = itemDAO.getMoneySpentBetween(timeGetter.addAndGet(MONTH, -1), timeGetter.getTodaysMidnight());
        lastYearSpent = itemDAO.getMoneySpentBetween(timeGetter.addAndGet(YEAR, -1), timeGetter.getTodaysMidnight());

    }

    public long getTotalSpent() {
        return totalSpent;
    }

    public long getLastDaySpent() {
        return lastDaySpent;
    }

    public long getLastWeekSpent() {
        return lastWeekSpent;
    }

    public long getLastMonthSpent() {
        return lastMonthSpent;
    }

    public long getLastYearSpent() {
        return lastYearSpent;
    }
}
