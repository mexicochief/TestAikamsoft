package org.kolesnikov.manager.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.kolesnikov.StatisticResult;
import org.kolesnikov.query.statisitc.StatisticQuery;
import org.kolesnikov.service.statisitc.StatisticService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DateSearchManager {
    private final StatisticService statisticService;
    private final Gson gson;

    public DateSearchManager(StatisticService statisticService, Gson gson) {
        this.statisticService = statisticService;
        this.gson = gson;
    }

    public JsonObject find(StatisticQuery statisticQuery) {
        final long duration = statisticQuery.getEndDate().getTime() - statisticQuery.getStartDate().getTime();
        final long overallDays = TimeUnit.DAYS.convert(duration,TimeUnit.MILLISECONDS);

        final List<StatisticResult> statisticResults = statisticService.get(statisticQuery);

        final Optional<BigDecimal> optionalTotalExpense = statisticResults
                .stream()
                .map(StatisticResult::getTotalExpense)
                .reduce(BigDecimal::add);
        BigDecimal totalExpense;
        BigDecimal avgExpenses;
        if (optionalTotalExpense.isEmpty()) {
            totalExpense = new BigDecimal(0);
            avgExpenses = new BigDecimal(0);
        } else {
            totalExpense = optionalTotalExpense.get();
            avgExpenses = totalExpense.divide(new BigDecimal(statisticResults.size()), RoundingMode.HALF_EVEN);
        }

        JsonObject resultStatJson = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        resultStatJson.addProperty("stat", "search");
        resultStatJson.addProperty("totalDays", overallDays);
        resultStatJson.add("customers", jsonArray);
        resultStatJson.addProperty("TotalExpenses", totalExpense);
        resultStatJson.addProperty("avgExpenses", avgExpenses);

        for (StatisticResult statisticResult : statisticResults) {
            JsonObject data = new JsonObject();
            data.addProperty("name", statisticResult.getName());
            JsonArray purchases = new JsonArray();
            statisticResult.getPurchases().forEach(
                    statResult -> {
                        Map<String, String> purchase = new HashMap<>();
                        purchase.put("name", statResult.getProductName());
                        purchase.put("expenses", statResult.getExpenses().toString());
                        purchases.add(gson.toJsonTree(purchase));
                    }
            );
            data.add("purchases", purchases);
            data.addProperty("TotalExpenses", statisticResult.getTotalExpense());
            jsonArray.add(data);
        }
        return resultStatJson;
    }
}
