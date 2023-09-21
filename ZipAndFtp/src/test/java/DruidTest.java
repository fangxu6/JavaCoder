import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * className: DruidTest
 * package: PACKAGE_NAME
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/9/7 16:54
 */
public class DruidTest {
    @Test
    public void druidTest() throws Exception {
        //配置文件的方式使用Druid连接池
        //1. 创建Properties对象
        Properties properties = new Properties();
        //2. 将配置文件转换成字节输入流
        InputStream is = DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");
        //3. 使用properties对象加载is
        properties.load(is);
        //druid底层是使用的工厂设计模式，去加载配置文件，创建DruidDataSource对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection conn1 = dataSource.getConnection();
        Connection conn2 = dataSource.getConnection();
        Connection conn3 = dataSource.getConnection();
        Connection conn4 = dataSource.getConnection();
        Connection conn5 = dataSource.getConnection();
        Connection conn6 = dataSource.getConnection();
        Connection conn7 = dataSource.getConnection();
        Connection conn8 = dataSource.getConnection();
        Connection conn9 = dataSource.getConnection();
        Connection conn10 = dataSource.getConnection();
        conn10.close();
        Connection conn11 = dataSource.getConnection();
        is.close();//关流

    }
}
