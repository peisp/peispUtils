package com.peisp.exportest;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据数据库连接信息获取 sql 执行结果
 * @author peishaopeng
 */
public class DBUtils {

    /**
     * 根据数据库连接信息，返回 sql 结果集
     * @param dataSource
     * @return
     */
    public static List<Map<String, Object>> getSqlData(DataSource dataSource) {
        try (
                Connection connection = getConnection(dataSource.getDriverClassName(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
                 // 创建代表sql语句的对象
                Statement statement = connection.createStatement();
                // 执行sql语句
                ResultSet resultSet = statement.executeQuery(dataSource.getSql());
            ){
            // 获取结果集
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()){
                Map<String, Object> map = new LinkedHashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object object = resultSet.getObject(i);
                    map.put(columnName,object);
                }
                list.add(map);
            }
            return list;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private static Connection getConnection(String driverClassName, String url, String username, String passwd) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName(driverClassName);
        //2.获得数据库的连接
        return DriverManager.getConnection(url, username, passwd);
    }

}
