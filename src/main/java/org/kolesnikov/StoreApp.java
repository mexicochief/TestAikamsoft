package org.kolesnikov;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.kolesnikov.dto.UserDto;
import org.kolesnikov.query.PurchaseSumIntervalQueryExecutor;
import org.kolesnikov.repository.user.SimpleUserDBManager;
import org.kolesnikov.repository.user.UserDBManager;
import org.kolesnikov.service.user.SimpleUserService;
import org.kolesnikov.service.user.UserService;
import org.kolesnikov.service.user.converter.SimpleUserConverter;
import org.kolesnikov.service.user.converter.UserConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class StoreApp {
    public static void main(String[] args) {

        Properties properties = new Properties();
        try (InputStream inputStream =
                     StoreApp.class.getClassLoader().getResourceAsStream("server.properties")) {
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("jdbcUrl"));
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword(properties.getProperty("password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("maximumPoolSize")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);


        UserDBManager userDBManager = new SimpleUserDBManager(dataSource);
        UserConverter userConverter = new SimpleUserConverter();
        UserService userService = new SimpleUserService(userDBManager, userConverter);
        final List<UserDto> userDtos = userService.get(new PurchaseSumIntervalQueryExecutor(1, 100));
        userDtos.forEach(userDto -> System.out.println(userDto.getFirstName()));

    }
}
