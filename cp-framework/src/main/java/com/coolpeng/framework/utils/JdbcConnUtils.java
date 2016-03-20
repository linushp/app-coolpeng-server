package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Tan Liang
 * @since 2015-07-16
 */
public class JdbcConnUtils {
    private static Logger logger = LoggerFactory.getLogger(JdbcConnUtils.class);

    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
