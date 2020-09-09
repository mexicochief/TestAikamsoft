package org.kolesnikov.resolver.factory;

public enum Criterias {
    LAST_NAME("lastName"),
    PRODUCT_NAME("productName"),
    MIN_TIMES("minTimes"),
    MIN_EXPENSES("minExpenses"),
    MAX_EXPENSES("maxExpenses"),
    BAD_CUSTOMERS("badCustomers");

    private String value;

    Criterias(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
