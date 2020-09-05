package org.kolesnikov.query;

import java.util.Map;

public class UserQuery {
    private final String lastName;
    private final long badCustomersCount;
    private final Map<String, Long> productPurchaseCount;
    private final long minExpense;
    private final long maxExpense;

    public UserQuery(String lastName, long badCustomersCount, Map<String, Long> productPurchaseCount, long minExpense, long maxExpense) {
        this.lastName = lastName;
        this.badCustomersCount = badCustomersCount;
        this.productPurchaseCount = productPurchaseCount;
        this.minExpense = minExpense;
        this.maxExpense = maxExpense;
    }

    public String getLastName() {
        return lastName;
    }

    public long getBadCustomersCount() {
        return badCustomersCount;
    }

    public Map<String, Long> getProductPurchaseCount() {
        return productPurchaseCount;
    }

    public long getMinExpense() {
        return minExpense;
    }

    public long getMaxExpense() {
        return maxExpense;
    }
}
