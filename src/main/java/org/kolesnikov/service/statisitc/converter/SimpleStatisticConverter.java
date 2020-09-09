package org.kolesnikov.service.statisitc.converter;

import org.kolesnikov.Purchase;
import org.kolesnikov.StatisticResult;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.model.Statistic;

import java.math.BigDecimal;
import java.util.*;

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

    @Override
    public List<StatisticResult> convert(List<Statistic> statisticList) {
        final Map<String, ArrayList<Purchase>> usePurchasesMap = new HashMap<>();
        final List<StatisticResult> statisticResults = new ArrayList<>();

        statisticList
                .stream()
                .map(statDto -> statDto.getFirstName() + " " + statDto.getLastName())
                .forEach(name -> usePurchasesMap.put(name, new ArrayList<>()));

        for (Statistic statistic : statisticList) {
            final String name = statistic.getFirstName() + " " + statistic.getLastName();
            usePurchasesMap.get(name).add(new Purchase(statistic.getProductName(), statistic.getExpenses()));
        }
        BigDecimal overallExpenses = new BigDecimal(0);
        for (String name : usePurchasesMap.keySet()) {
            final ArrayList<Purchase> purchases = usePurchasesMap.get(name);
            final Optional<BigDecimal> reduce = purchases.stream().map(Purchase::getExpenses).reduce(BigDecimal::add);
            final BigDecimal totalExpenses = reduce.get();//todo
            overallExpenses = overallExpenses.add(totalExpenses);
            statisticResults.add(new StatisticResult(name, purchases, totalExpenses));
        }
        return statisticResults;
    }


}
