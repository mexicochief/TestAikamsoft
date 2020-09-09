package org.kolesnikov.service.statisitc;

import org.kolesnikov.query.statisitc.StatisticQuery;
import org.kolesnikov.repository.statistic.StatisticDbManager;
import org.kolesnikov.result.StatisticResult;
import org.kolesnikov.service.statisitc.converter.StatisticConverter;

import java.util.List;

public class SimpleStatisticService implements StatisticService {
    private final StatisticDbManager statisticDbManager;
    private final StatisticConverter statisticConverter;

    public SimpleStatisticService(StatisticDbManager statisticDbManager, StatisticConverter statisticConverter) {
        this.statisticDbManager = statisticDbManager;
        this.statisticConverter = statisticConverter;
    }

    @Override
    public List<StatisticResult> get(StatisticQuery statisticQuery) {
        return statisticConverter.convert(statisticDbManager.get(statisticQuery));
    }
}
