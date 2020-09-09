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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateSearchManager {
    private final StatisticService statisticService;
    private final Gson gson;

    public DateSearchManager(StatisticService statisticService, Gson gson) {
        this.statisticService = statisticService;
        this.gson = gson;
    }

    public JsonObject find(StatisticQuery statisticQuery) {
        final Date overallDays =
                new Date(statisticQuery.getEndDate().getTime() - statisticQuery.getStartDate().getTime());
        final List<StatisticResult> statisticResults = statisticService.get(statisticQuery);

        BigDecimal totalExpense = statisticResults
                .stream()
                .map(StatisticResult::getTotalExpense)
                .reduce(BigDecimal::add).get();//todo обработать
        BigDecimal avgExpenses =
                totalExpense.divide(new BigDecimal(statisticResults.size()), RoundingMode.HALF_EVEN);

        JsonObject resultStatJson = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        resultStatJson.addProperty("stat", "search");
        resultStatJson.addProperty("totalDays", overallDays.toString());
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
