package org.kolesnikov.service.statisitc.converter;

import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.model.Statistic;

public class SimpleStatisticConverter implements StatisticConverter {
    @Override
    public StatisticDto convert(Statistic statisitc) {
        return new StatisticDto(
                statisitc.getUserId(),
                statisitc.getFirstName(),
                statisitc.getLastName(), statisitc.getExpenses(),
                statisitc.getProductName());
    }

    @Override
    public Statistic convert(StatisticDto statisticDto) {
        return new Statistic(
                statisticDto.getUserId(),
                statisticDto.getFirstName(),
                statisticDto.getLastName(),
                statisticDto.getExpenses(),
                statisticDto.getProductName());
    }
}
