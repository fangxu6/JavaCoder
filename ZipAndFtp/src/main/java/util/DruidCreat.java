package util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * className: DruidCreat
 * package: util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/9/11 10:51
 */
public class DruidCreat {
    public static DruidDataSource getDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName("测试连接池");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://150.158.214.238:3306/jse?useUnicode=true&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&useSSL=false");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("Zhz@159357");
        druidDataSource.setInitialSize(2);
        druidDataSource.setMinIdle(2);
        druidDataSource.setMaxActive(5);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(true);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setMaxWait(6000);
        druidDataSource.setFilters("slf4j");
        return druidDataSource;
    }

}
