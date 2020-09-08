package org.kolesnikov;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kolesnikov.dto.UserDto;
import org.kolesnikov.properties.PropertiesLoader;
import org.kolesnikov.query.QueryExecutor;
import org.kolesnikov.repository.user.SimpleUserDBManager;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.resolver.*;
import org.kolesnikov.service.user.SimpleUserService;
import org.kolesnikov.service.user.UserService;
import org.kolesnikov.service.user.converter.SimpleUserConverter;
import org.kolesnikov.service.user.converter.UserConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

        //=======================
        List<QueryExecutor> queryExecutors = new ArrayList<>();
        try (InputStream inputStream = StoreApp.class.getClassLoader().getResourceAsStream("test.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ExecutorFactory> factories = new ArrayList<>();
            factories.add(new BadUsersExecutorFactory());
            factories.add(new ProductCountExecutorFactory());
            factories.add(new LastNameExecutorFactory());
            factories.add(new PurchaseSumIntervalExecutorFactory());

            if (inputStream != null) {
                final JsonNode jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
                final JsonNode criterias = jsonNode.get("criterias");

                NodeResolver nodeResolver = new NodeResolver(factories);
                for (JsonNode criteria : criterias) {
                    List<String> names = new ArrayList<>();
                    final Iterator<String> stringIterator = criteria.fieldNames();
                    stringIterator.forEachRemaining(names::add);
                    final ExecutorFactory factory = nodeResolver.resolve(names);
                    if (factory != null) { //todo
                        queryExecutors.add(factory.create(criteria));
                    }
                }
            }
            JSONObject jsonObject = new JSONObject();
            for (QueryExecutor queryExecutor : queryExecutors) {
                JSONArray jsonArray = new JSONArray();

                List<Map<String, String>> users = new ArrayList<>();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
