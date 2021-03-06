package org.kolesnikov.model;

import java.math.BigDecimal;

public class Statistic {
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final BigDecimal expenses;
    private final String productName;

    public Statistic(Long userId, String firstName, String lastName, BigDecimal expenses, String productName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expenses = expenses;
        this.productName = productName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public String getProductName() {
        return productName;
    }
}