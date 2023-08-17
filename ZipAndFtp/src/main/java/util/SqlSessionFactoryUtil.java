package util;

import consts.BaseConst;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * className: SqlSessionFactoryUtil
 * package: util
 * Description:
 * 构建SqlSessionFactory
 * 由于数据库连接是宝贵的,需要对数据库连接统一管理,所以使用单例进行管理
 * 这里的单利使用的双重锁
 * SqlSessionFactory为线程不安全类型需要加锁,确保同一时刻,只有一个线程可以使用该对象
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/11 16:19
 */
public class SqlSessionFactoryUtil {


    /**
     * SqlSessionFactory对象
     */
    private static SqlSessionFactory sqlSessionFactory = null;

    /**
     * 类线程锁
     */
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

    /**
     * 单例
     */
    private SqlSessionFactoryUtil() {

    }

    /**
     * @return SqlSessionFactory
     * 初始化SqlSessionFactory对象
     */
    public static SqlSessionFactory initSqlSessionFactory() {
        // 获得输入流
        InputStream cfgStream = null;
        // 阅读流
        Reader cfgReader = null;
        InputStream proStream = null;
        Reader proReader = null;
        // 持久化属性集
        Properties properties = null;
        try {
            // 配置文件流 我这里配置文件做了特殊处理
            cfgStream = Resources.getResourceAsStream(BaseConst.MYBATIS_CONF);
            // 获得阅读流
//            cfgReader = Resources.getResourceAsReader("mybatis-config.xml");
            cfgReader = new InputStreamReader(cfgStream);
            // 读入属性文件 我这里配置文件做了特殊处理
            proStream = Resources.getResourceAsStream(BaseConst.MYBATIS_DB_CONF);
//             #配置文件在resource目录下面采用这种方式
//             #proStream = Resources.getResourceAsStream("db.properties");

            proReader = new InputStreamReader(proStream);
            // 持久化属性集
            properties = new Properties();
            // 流装载进入属性集合
            properties.load(proReader);
            // 获取当前系统ENV TODO 需要手工改
            String key = "coYsBdByZT8De1rNiMl2qq==";
//            String key = System.getenv("DB_ENCRYPT_KEY");
            // 进行解密
            String encryptPwd = properties.getProperty("jdbc.password");
            String decrypt = AES256Utils.decrypt(encryptPwd, key);

            properties.setProperty("jdbc.password", decrypt);
//            properties.setProperty("password", Decode.decryptDecode(properties.getProperty("jdbc.password"), key));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sqlSessionFactory == null) {
            synchronized (CLASS_LOCK) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(cfgReader, properties);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * 打开SqlSession
     *
     * @return SqlSession
     */
    public static SqlSession getSqlSession() {
        // 判空处理
        if (sqlSessionFactory == null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }

    //    /**
//     * 获取SqlSession
//     *
//     * @return
//     * @throws IOException
//     */
//    public static SqlSession getSqlSession() throws IOException {
//        //mybatis的配置文件
//        String resource = BaseConst.MYBATIS_CONF;
//        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
//        InputStream is = Resources.getResourceAsStream(resource);
//        //构建sqlSession的工厂
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//        return sessionFactory.openSession();
//    }
    public static void main(String[] args) {
        String key = System.getenv("DB_ENCRYPT_KEY");
        System.out.println("获取环境变量中的加密key: " + key);
    }


}
