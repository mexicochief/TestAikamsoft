package org.kolesnikov;

import java.math.BigDecimal;

public class Purchase {
    private final String productName;
    private final BigDecimal Expenses;


    public Purchase(String productName, BigDecimal expenses) {
        this.productName = productName;
        Expenses = expenses;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getExpenses() {
        return Expenses;
    }
}
