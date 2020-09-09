package org.kolesnikov;

import java.math.BigDecimal;
import java.util.ArrayList;

public class StatisticResult {
    private final String name;
    private final ArrayList<Purchase> purchases;
    private final BigDecimal totalExpense;

    public StatisticResult(String name, ArrayList<Purchase> purchases, BigDecimal totalExpense) {
        this.name = name;
        this.purchases = purchases;
        this.totalExpense = totalExpense;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }
}
