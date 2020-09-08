package org.kolesnikov.manager.search;

import com.google.gson.*;
import org.kolesnikov.dto.UserDto;
import org.kolesnikov.query.QueryExecutor;
import org.kolesnikov.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchManager {
    private final UserService userService;
    private final Gson gson;

    public SearchManager(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    public JsonObject findByQueries(List<QueryExecutor> queryExecutors) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "search");
        JsonArray results = new JsonArray();
        jsonObject.add("results", results);

        for (QueryExecutor queryExecutor : queryExecutors) {
            JsonObject usersAndCriteria = new JsonObject();
            JsonArray users = new JsonArray();
            final List<UserDto> userDtos = userService.get(queryExecutor);
            userDtos.forEach(userDto -> {
                Map<String, String> consumer = new HashMap<>();
                consumer.put("firstName", userDto.getFirstName());
                consumer.put("lastName", userDto.getLastName());
                users.add(gson.toJsonTree(consumer));
            });
            final JsonElement criteria = gson.toJsonTree(queryExecutor.getCriteria());
            usersAndCriteria.add("criteria", criteria);
            usersAndCriteria.add("result", users);
            results.add(usersAndCriteria);
        }
        return jsonObject; //todo сделать нормальные названия
    }
}
