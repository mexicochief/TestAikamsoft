package org.kolesnikov.manager.search;

import com.google.gson.*;
import org.kolesnikov.dto.UserDto;
import org.kolesnikov.query.user.UserQueryExecutor;
import org.kolesnikov.service.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaSearchManager {
    private final UserService userService;
    private final Gson gson;

    public CriteriaSearchManager(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    public JsonObject find(List<UserQueryExecutor> queryExecutors) {
        JsonObject resultJson = new JsonObject();
        resultJson.addProperty("type", "search");
        JsonArray results = new JsonArray();
        resultJson.add("results", results);

        for (UserQueryExecutor queryExecutor : queryExecutors) {
            JsonObject usersAndCriteria = new JsonObject();
            JsonArray users = new JsonArray();
            final List<UserDto> userDtos = userService.get(queryExecutor);
            userDtos.forEach(userDto -> {
                Map<String, String> user = new HashMap<>();
                user.put("firstName", userDto.getFirstName());
                user.put("lastName", userDto.getLastName());
                users.add(gson.toJsonTree(user));
            });
            final JsonElement criteria = gson.toJsonTree(queryExecutor.getCriteria());
            usersAndCriteria.add("criteria", criteria);
            usersAndCriteria.add("result", users);
            results.add(usersAndCriteria);
        }
        return resultJson;
    }
}
