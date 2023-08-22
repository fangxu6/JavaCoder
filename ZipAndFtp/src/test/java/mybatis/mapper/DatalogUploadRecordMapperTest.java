package mybatis.mapper;

import mybatis.entity.DatalogUploadRecord;
import mybatis.entity.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SqlSessionFactoryUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * User: Homejim
 * Date 2018-9-4
 * Time 20:40
 */
public class DatalogUploadRecordMapperTest {
    private static Logger logger = LoggerFactory.getLogger(DatalogUploadRecordMapperTest.class);


    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        Properties properties = null;
//        try {
//            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
//
//            properties = new Properties();
//            properties.load(reader);
//            properties.setProperty("jdbc.password","Zhz@159357");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,properties);
        try {
//            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            if (sqlSessionFactory == null) {
                sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
            }
//            sqlSession = sqlSessionFactory.openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void addDatalogUploadRecord() throws IOException {

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("192.168.5.244", 22));
        InetAddress localAddress = socket.getLocalAddress();
        socket.close();

        DatalogUploadRecord datalogUploadRecord = new DatalogUploadRecord();
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        datalogUploadRecord.setIp(localAddress.getHostAddress());
        datalogUploadRecord.setZipFile("33test.zip");
        datalogUploadRecord.setStatus(0);
        datalogUploadRecord.setDatalog1("11");
        datalogUploadRecord.setDatalog2("11");
        datalogUploadRecord.setDatalog3("11");
        datalogUploadRecord.setDatalog4("11");
        datalogUploadRecord.setDatalog5("11");
        datalogUploadRecord.setDatalog6("11");
        datalogUploadRecord.setDatalog7("11");
        datalogUploadRecord.setCreateTime(System.currentTimeMillis());

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            DatalogUploadRecordMapper datalogUploadRecordMapper = (DatalogUploadRecordMapper) sqlSession.getMapper(DatalogUploadRecordMapper.class);
            int i1 = datalogUploadRecordMapper.addDatalogUploadRecord(datalogUploadRecord);
            sqlSession.commit();
            logger.info("aaaaaaaaaaaaaaaa" + i1 + "bbbbbbbbbbbbb");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }

}
