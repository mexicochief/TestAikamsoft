package org.kolesnikov.service.statisitc.converter;

import org.kolesnikov.result.StatisticResult;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.model.Statistic;

import java.util.List;

public interface StatisticConverter {
    StatisticDto convert(Statistic statisitc);

    Statistic convert(StatisticDto statisticDto);

    List<StatisticResult> convert(List<Statistic> statisticList);

}
