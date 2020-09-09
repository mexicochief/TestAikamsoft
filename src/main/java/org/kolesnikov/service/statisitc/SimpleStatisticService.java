package org.kolesnikov.service.statisitc;

import org.kolesnikov.result.StatisticResult;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.query.statisitc.StatisticQuery;
import org.kolesnikov.repository.statistic.StatisticDbManager;
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
    public StatisticDto put(StatisticDto statisticDto) {
        return null;
    }

    @Override
    public List<StatisticResult> get(StatisticQuery statisticQuery) {
        return statisticConverter.convert(statisticDbManager.get(statisticQuery));
    }

    @Override
    public StatisticDto getById(long id) {
        return null;
    }

    @Override
    public StatisticDto update(long id, StatisticDto statisticDto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
