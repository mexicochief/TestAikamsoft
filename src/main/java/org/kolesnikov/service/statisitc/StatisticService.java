package org.kolesnikov.service.statisitc;

import org.kolesnikov.result.StatisticResult;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.query.statisitc.StatisticQuery;

import java.util.List;

public interface StatisticService {
    StatisticDto put(StatisticDto statisticDto);

    List<StatisticResult> get(StatisticQuery statisticQuery);

    StatisticDto getById(long id);

    StatisticDto update(long id, StatisticDto statisticDto);

    void delete(long id);
}
