package org.kolesnikov;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.json.JsonReader;
import org.kolesnikov.json.JsonWriter;
import org.kolesnikov.json.SimpleJsonReader;
import org.kolesnikov.json.SimpleJsonWriter;
import org.kolesnikov.manager.CriteriaManager;
import org.kolesnikov.manager.QueryManager;
import org.kolesnikov.manager.search.SearchManager;
import org.kolesnikov.properties.PropertiesLoader;
import org.kolesnikov.query.QueryExecutor;
import org.kolesnikov.query.statisitc.StatisticQuery;
import org.kolesnikov.repository.statistic.SimpleStatisticDbManager;
import org.kolesnikov.repository.statistic.StatisticDbManager;
import org.kolesnikov.repository.user.SimpleUserDBManager;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.resolver.*;
import org.kolesnikov.service.statisitc.SimpleStatisticService;
import org.kolesnikov.service.statisitc.StatisticService;
import org.kolesnikov.service.statisitc.converter.SimpleStatisticConverter;
import org.kolesnikov.service.statisitc.converter.StatisticConverter;
import org.kolesnikov.service.user.SimpleUserService;
import org.kolesnikov.service.user.UserService;
import org.kolesnikov.service.user.converter.SimpleUserConverter;
import org.kolesnikov.service.user.converter.UserConverter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StoreApp {
    public static void main(String[] args) {


        PropertiesLoader propertiesLoader = new PropertiesLoader();
        final Properties property = propertiesLoader.getProperty("server.properties");

        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(property.getProperty("jdbcUrl"));
        hikariConfig.setUsername(property.getProperty("userName"));
        hikariConfig.setPassword(property.getProperty("password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(property.getProperty("maximumPoolSize")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader jsonReader = new SimpleJsonReader();
        JsonWriter jsonWriter = new SimpleJsonWriter(gson);

        if (args[0].equalsIgnoreCase("search")) {
            UserDBManager userDBManager = new SimpleUserDBManager(dataSource);
            UserConverter userConverter = new SimpleUserConverter();
            UserService userService = new SimpleUserService(userDBManager, userConverter);

            List<ExecutorFactory> factories = new ArrayList<>();
            factories.add(new BadUsersExecutorFactory());
            factories.add(new ProductCountExecutorFactory());
            factories.add(new LastNameExecutorFactory());
            factories.add(new PurchaseSumIntervalExecutorFactory());

            NodeResolver nodeResolver = new NodeResolver(factories);

            QueryManager queryManager = new CriteriaManager(nodeResolver);

            final JsonNode jsonNode = jsonReader.read("test.json");
            final JsonNode criterias = jsonNode.get("criterias");


            List<QueryExecutor> queryExecutors = queryManager.getQueryExecutors(criterias);


            SearchManager searchManager = new SearchManager(userService, gson);
            final JsonObject jsonObject = searchManager.findByQueries(queryExecutors);

            jsonWriter.write(jsonObject, "output.json");
        } else if (args[0].equalsIgnoreCase("stat")) {
            final JsonNode statJson = jsonReader.read("stat.json");// todo обработать null
            final String startDateStr = statJson.get("startDate").asText();
            final String endDateStr = statJson.get("endDate").asText();
            StatisticDbManager statisticDbManager = new SimpleStatisticDbManager(dataSource);
            StatisticConverter statisticConverter = new SimpleStatisticConverter();
            StatisticService statisticService = new SimpleStatisticService(statisticDbManager, statisticConverter);
            final Date startDate = Date.valueOf(startDateStr);
            final Date endDate = Date.valueOf(endDateStr);
            final List<StatisticDto> statisticDtos = statisticService.get(new StatisticQuery(startDate, endDate));
        }
    }
}
