package org.kolesnikov.service.statisitc;

import org.kolesnikov.result.StatisticResult;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.query.statisitc.StatisticQuery;

import java.util.List;

public interface StatisticService {
    List<StatisticResult> get(StatisticQuery statisticQuery);
}
