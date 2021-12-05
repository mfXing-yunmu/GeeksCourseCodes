package com.yunmu.geek;

import java.sql.*;

/**
 * @author mfXing
 */
public class JdbcUtil {
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/test";
    private static String user = "root";
    private static String password = "root";

    static{
        try {
            Class.forName(driverName);
            System.out.println("start...");
        } catch (ClassNotFoundException e) {
            System.out.println("Can't find mysql jdbc driver");
            e.printStackTrace();
            throw new RuntimeException("获取数据库驱动异常！");
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 关闭 JDBC 相关资源
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if (connection != null){
                connection.close();
            }
            if (statement != null){
                statement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e){
            System.out.println("Connection close failed!");
            e.printStackTrace();
        }
    }
}
