package com.peisp.exportest;

/**
 * 数据源配置
 * @author peishaopeng
 */
public class DataSource {
    /** 驱动程序类名 */
    private String driverClassName;
    /** url */
    private String url;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** sql */
    private String sql;
    /** sql 参数 */
    private Object[] sqlParams;

    /** 禁止无参构造 */
    private DataSource() {
    }

    public DataSource(String driverClassName,String url, String username, String password, String sql) {
        if (driverClassName == null || url == null || username == null || password == null || sql == null){
            throw new NumberFormatException("The data source configuration item cannot be empty");
        }
        this.url = url;
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
        this.sql = sql;
        this.sqlParams = new Object[0];
    }


    public DataSource(String driverClassName,String url, String username, String password, String sql, Object[] sqlParams) {
        if (driverClassName == null || url == null || username == null || password == null || sql == null){
            throw new NumberFormatException("The data source configuration item cannot be empty");
        }
        this.url = url;
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
        this.sql = sql;
        if (sqlParams == null){
            this.sqlParams = new Object[0];
        }else {
            this.sqlParams = sqlParams;
        }

    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSql() {
        return sql;
    }

    public Object[] getSqlParams() {
        return sqlParams;
    }
}
