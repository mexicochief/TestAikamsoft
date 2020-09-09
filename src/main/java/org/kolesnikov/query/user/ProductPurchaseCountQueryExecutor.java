package org.kolesnikov.query.user;

import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPurchaseCountQueryExecutor implements UserQueryExecutor {

    private final String QUERY = "select first_name, last_name " +
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
        return QUERY;
    }

    @Override
    public List<User> runScript(DataSource dataSource) {
        List<User> users = new ArrayList<>();
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            preparedStatement.setLong(1, quantity);
            preparedStatement.setString(2, productName);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final String firstName = resultSet.getString(1);
                final String lastName = resultSet.getString(2);
                users.add(new User(null, firstName, lastName));
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
        return users;
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
