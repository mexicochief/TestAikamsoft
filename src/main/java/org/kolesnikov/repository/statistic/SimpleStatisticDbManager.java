package org.kolesnikov.repository.statistic;

import org.kolesnikov.exception.DbException;
import org.kolesnikov.model.Statistic;
import org.kolesnikov.query.statisitc.StatisticQuery;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleStatisticDbManager implements StatisticDbManager {
    private final DataSource dataSource;

    private final String QUERY = "select userId ,first_name, last_name, sum(overalCost), product_name " +
            "from (select users.id userId, first_name, last_name, sum(cost) overalCost, order_date, product_name " +
            "      from store.users " +
            "               left join store.purchases purch on users.id = purch.user_id " +
            "               left join store.products prod on purch.product_id = prod.id " +
            "      group by first_name, last_name, userId, order_date, product_name) as userWithOveralSum " +
            "where order_date >= ? and order_date <= ? " +
            "group by first_name, last_name, overalCost, product_name,userId";

    public SimpleStatisticDbManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Statistic put(Statistic statistic) {
        return null;
    }

    @Override
    public List<Statistic> get(StatisticQuery statisticQuery) {
        try (final PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            List<Statistic> statistics = new ArrayList<>();

            preparedStatement.setDate(1, statisticQuery.getStartDate());
            preparedStatement.setDate(2, statisticQuery.getEndDate());
            final ResultSet statResultSet = preparedStatement.executeQuery();
            while (statResultSet.next()) {
                final Long userId = statResultSet.getLong(1);
                final String lastName = statResultSet.getString(2);
                final String firstName = statResultSet.getString(3);
                final BigDecimal expenses = statResultSet.getBigDecimal(4);
                final String productName = statResultSet.getString(5);
                statistics.add(new Statistic(userId, lastName, firstName, expenses, productName));
            }
            return statistics;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Statistic getById(long id) {
        return null;
    }

    @Override
    public Statistic update(Statistic statistic) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
