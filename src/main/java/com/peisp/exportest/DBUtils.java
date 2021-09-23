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

        Connection connection = null;
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(dataSource.getDriverClassName(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            // 创建代表sql语句的对象
            preparedStatement = connection.prepareStatement(dataSource.getSql());
            for (int i = 0; i < dataSource.getSqlParams().length; i++) {
                preparedStatement.setObject(i + 1,dataSource.getSqlParams()[i]);
            }
            // 执行sql语句
            resultSet = preparedStatement.executeQuery();

            // 获取结果集
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> list = new ArrayList<>();
            while (resultSet.next()){
                Map<String, Object> map = new LinkedHashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object object = resultSet.getObject(i);
                    map.put(columnName,object);
                }
                list.add(map);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,resultSet);
        }
        return null;
    }
    /**
     *  关闭数据连接
     * @param con
     * @param sta
     * @param rs    //针对查询
     */
    private static void close(Connection con, PreparedStatement sta, ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (sta != null) {
                sta.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Connection getConnection(String driverClassName, String url, String username, String passwd) throws ClassNotFoundException, SQLException {
        //1.加载驱动程序
        Class.forName(driverClassName);
        //2.获得数据库的连接
        return DriverManager.getConnection(url, username, passwd);
    }

}
