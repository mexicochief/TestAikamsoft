package org.kolesnikov;

import java.util.List;
import java.util.Map;

public class Result {
    private final Map<String, String> criteria;
    private final List<Map<String, String>> result;

    public Result(Map<String, String> criteria, List<Map<String, String>> users) {
        this.criteria = criteria;
        this.result = users;
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }

    public List<Map<String, String>> getResult() {
        return result;
    }
}
