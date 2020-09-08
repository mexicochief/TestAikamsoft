package org.kolesnikov;

import com.fasterxml.jackson.databind.JsonNode;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kolesnikov.dto.StatisticDto;
import org.kolesnikov.dto.UserDto;
import org.kolesnikov.json.JsonReader;
import org.kolesnikov.json.SimpleJsonReader;
import org.kolesnikov.manager.CriteriaManager;
import org.kolesnikov.manager.QueryManager;
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
import java.util.*;

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


        UserDBManager userDBManager = new SimpleUserDBManager(dataSource);
        UserConverter userConverter = new SimpleUserConverter();
        UserService userService = new SimpleUserService(userDBManager, userConverter);


        StatisticDbManager statisticDbManager = new SimpleStatisticDbManager(dataSource);
        StatisticConverter statisticConverter = new SimpleStatisticConverter();
        StatisticService statisticService = new SimpleStatisticService(statisticDbManager, statisticConverter);
        final Date startDate = Date.valueOf("2020-09-05");
        final Date endDate = Date.valueOf("2020-09-05");
        final List<StatisticDto> statisticDtos = statisticService.get(new StatisticQuery(startDate, endDate));

        List<ExecutorFactory> factories = new ArrayList<>();
        factories.add(new BadUsersExecutorFactory());
        factories.add(new ProductCountExecutorFactory());
        factories.add(new LastNameExecutorFactory());
        factories.add(new PurchaseSumIntervalExecutorFactory());

        NodeResolver nodeResolver = new NodeResolver(factories);

        QueryManager queryManager = new CriteriaManager(nodeResolver);

        JsonReader jsonReader = new SimpleJsonReader();
        final JsonNode jsonNode = jsonReader.read("test.json");
        final JsonNode criterias = jsonNode.get("criterias");


        List<QueryExecutor> queryExecutors = queryManager.getQueryExecutors(criterias);

        JSONObject jsonObject = new JSONObject();
        for (QueryExecutor queryExecutor : queryExecutors) {
            JSONArray jsonArray = new JSONArray();
            final List<UserDto> userDtos = userService.get(queryExecutor);
            userDtos.forEach(userDto -> {
                Map<String, String> map = new HashMap<>();
                map.put("firstName", userDto.getFirstName());
                map.put("lastName", userDto.getLastName());
                jsonArray.put(map);
            });
            jsonObject.put("criteria", queryExecutor.getCriteria());
            jsonObject.put("result", jsonArray);

            System.out.println(jsonObject.toString());
        }
    }
}
