package org.kolesnikov;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.*;
import org.kolesnikov.json.JsonReader;
import org.kolesnikov.json.JsonWriter;
import org.kolesnikov.json.SimpleJsonReader;
import org.kolesnikov.json.SimpleJsonWriter;
import org.kolesnikov.manager.CriteriaManager;
import org.kolesnikov.manager.QueryManager;
import org.kolesnikov.manager.search.DateSearchManager;
import org.kolesnikov.manager.search.CriteriaSearchManager;
import org.kolesnikov.parser.DateParser;
import org.kolesnikov.properties.PropertiesLoader;
import org.kolesnikov.query.user.UserQueryExecutor;
import org.kolesnikov.query.statisitc.StatisticQuery;
import org.kolesnikov.repository.statistic.SimpleStatisticDbManager;
import org.kolesnikov.repository.statistic.StatisticDbManager;
import org.kolesnikov.repository.user.SimpleUserDBManager;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.resolver.*;
import org.kolesnikov.resolver.factory.*;
import org.kolesnikov.service.statisitc.SimpleStatisticService;
import org.kolesnikov.service.statisitc.StatisticService;
import org.kolesnikov.service.statisitc.converter.SimpleStatisticConverter;
import org.kolesnikov.service.statisitc.converter.StatisticConverter;
import org.kolesnikov.service.user.SimpleUserService;
import org.kolesnikov.service.user.UserService;
import org.kolesnikov.service.user.converter.SimpleUserConverter;
import org.kolesnikov.service.user.converter.UserConverter;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StoreApp {
    static Logger logger = Logger.getLogger(StoreApp.class);
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        if (args.length < 3) {
            throw new RuntimeException("Недостаточно параметров");
        }
        final String type = args[0];
        final String inputFileName = args[1];
        final String outputFileName = args[2];
        Layout layout = new PatternLayout();
        FileAppender appender;
        try {
            appender = new FileAppender(layout, outputFileName, false);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        logger.addAppender(appender);
        logger.setLevel(Level.DEBUG);

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        final Properties property = propertiesLoader.getProperty("server.properties");

        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(property.getProperty("jdbcUrl"));
        hikariConfig.setUsername(property.getProperty("userName"));
        hikariConfig.setPassword(property.getProperty("password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(property.getProperty("maximumPoolSize")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        JsonReader jsonReader = new SimpleJsonReader(logger, gson);
        JsonWriter jsonWriter = new SimpleJsonWriter(gson);
        final JsonNode jsonNode = jsonReader.read(inputFileName);
        validate(jsonNode);

        if (type.equalsIgnoreCase("search")) {
            UserDBManager userDBManager = new SimpleUserDBManager(dataSource);
            UserConverter userConverter = new SimpleUserConverter();
            UserService userService = new SimpleUserService(userDBManager, userConverter);

            List<ExecutorFactory> factories = new ArrayList<>();
            factories.add(new BadUsersExecutorFactory());
            factories.add(new ProductCountExecutorFactory());
            factories.add(new LastNameExecutorFactory());
            factories.add(new PurchaseSumIntervalExecutorFactory());

            NodeResolver nodeResolver = new NodeResolver(factories, logger, gson);

            QueryManager queryManager = new CriteriaManager(nodeResolver);

            final JsonNode criterias = jsonNode.get("criterias");
            validate(criterias);

            List<UserQueryExecutor> queryExecutors = queryManager.getQueryExecutors(criterias);


            CriteriaSearchManager searchManager = new CriteriaSearchManager(userService, gson);
            final JsonObject jsonObject = searchManager.find(queryExecutors);
            jsonWriter.write(jsonObject, outputFileName);

        } else if (type.equalsIgnoreCase("stat")) {

            final StatisticDbManager statisticDbManager = new SimpleStatisticDbManager(dataSource);
            final StatisticConverter statisticConverter = new SimpleStatisticConverter();
            final StatisticService statisticService = new SimpleStatisticService(statisticDbManager, statisticConverter);
            final DateSearchManager searchManager = new DateSearchManager(statisticService, gson);

            DateParser dateParser = new DateParser(logger, gson);
            final Date startDate = dateParser.getProperty("startDate", jsonNode);
            final Date endDate = dateParser.getProperty("endDate", jsonNode);

            final JsonObject resultStatJson = searchManager.find(new StatisticQuery(startDate, endDate));

            jsonWriter.write(resultStatJson, outputFileName);
        }
    }

    static void validate(JsonNode jsonNode) {
        if (jsonNode == null) {
            logger.info(gson.toJson(Map.of("error", "Missing json property")));
            throw new RuntimeException("Missing json property");
        }
    }
}
