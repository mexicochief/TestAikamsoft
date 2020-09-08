package org.kolesnikov.query.statisitc;

import java.sql.Date;

public class StatisticQuery {
    private final Date startDate;
    private final Date endDate;

    public StatisticQuery(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
