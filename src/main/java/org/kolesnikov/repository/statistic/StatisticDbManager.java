package org.kolesnikov.repository.statistic;

import org.kolesnikov.model.Statistic;
import org.kolesnikov.query.statisitc.StatisticQuery;

import java.util.List;

public interface StatisticDbManager {
    List<Statistic> get(StatisticQuery statisticQuery);
}
