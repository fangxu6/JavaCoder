import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * className: DruidCase
 * package: PACKAGE_NAME
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/9/6 17:29
 */
public class DruidCase {
    public static void main(String[] args) {
    DruidDataSource druidDataSource = new DruidDataSource();
    Connection connection = null;
    try {
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

        connection = druidDataSource.getConnection();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select version()");
        if (rs.next()) {
            System.out.println(rs.getString(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        close(connection);
    }

    //实际使用中一般是在应用启动时初始化数据源，应用从数据源中获取连接；并不会关闭数据源。
    druidDataSource.close();
}

    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
