package org.kolesnikov.properties;

import org.kolesnikov.StoreApp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

   public Properties getProperty(String path){
        Properties properties = new Properties();
        try (InputStream inputStream =
                     StoreApp.class.getClassLoader().getResourceAsStream("server.properties")
        ) {
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }
}
