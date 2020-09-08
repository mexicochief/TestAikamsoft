package org.kolesnikov.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductPurchaseCountQueryExecutor implements QueryExecutor {

    private final String GET_BY_PURCHASE_COUNT = "select first_name, last_name " +
            "from (select first_name, last_name, product_name, count(product_name) count " +
            "      from store.users " +
            "               join store.purchases purchse on users.id = purchse.user_id " +
            "               join store.products prod on prod.id = purchse.product_id " +
            "      group by first_name, last_name, product_name) as table1 " +
            "where count >= ? and product_name like ? ";

    private Map<String, String> criteria;
    private final String productName;
    private final long quantity;

    public ProductPurchaseCountQueryExecutor(String productName, long quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }


    @Override
    public String getSqlQuery() {
        return GET_BY_PURCHASE_COUNT;
    }

    @Override
    public ResultSet execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, quantity);
        preparedStatement.setString(2, productName);
        return preparedStatement.executeQuery();
    }

    @Override
    public Map<String, String> getCriteria() {
        if (criteria == null) {
            criteria = new HashMap<>();
            criteria.put("productName", productName);
            criteria.put("minTimes", String.valueOf(quantity));
        }
        return criteria;
    }
}
