package org.kolesnikov.repository.statistic;

import org.kolesnikov.model.Statistic;
import org.kolesnikov.query.statisitc.StatisticQuery;

import java.util.List;

public interface StatisticDbManager {
    Statistic put(Statistic statistic);

    List<Statistic> get(StatisticQuery statisticQuery);

    Statistic getById(long id);

    Statistic update(Statistic statistic);

    void delete(long id);
}
