package com.proj0.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.proj0.logger.CarDealershipLogger;

import org.apache.log4j.Logger;

public class DBConnector {
    public static Connection conn = null;
    public static Logger logger = CarDealershipLogger.logger;

    public static Connection getConnection() {
        try {
            if (conn == null) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Properties props = new Properties();
                FileInputStream input = new FileInputStream("src/main/resources/connection.properties");
                props.load(input);
                String endpoint = props.getProperty("url");
                String username = props.getProperty("username");
                String password = props.getProperty("password");
                conn = DriverManager.getConnection(endpoint, username, password);
            }
        } catch (ClassNotFoundException e) {
            logger.fatal(e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.fatal(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.fatal(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
