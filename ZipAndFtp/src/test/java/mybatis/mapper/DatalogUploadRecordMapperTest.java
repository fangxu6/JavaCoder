package mybatis.mapper;

import mybatis.entity.DatalogUploadRecord;
import mybatis.entity.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import util.SqlSessionFactoryUtil;

import java.util.*;

/**
 * Created by IntelliJ IDEA
 * User: Homejim
 * Date 2018-9-4
 * Time 20:40
 */
public class DatalogUploadRecordMapperTest {
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
    public void addDatalogUploadRecord() {

        DatalogUploadRecord datalogUploadRecord = new DatalogUploadRecord();
        datalogUploadRecord.setIp("192.168.5.11");
        datalogUploadRecord.setZipFile("11test.zip");
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

            DatalogUploadRecordMapper studentMapper = (DatalogUploadRecordMapper) sqlSession.getMapper(DatalogUploadRecordMapper.class);
            int i1 = studentMapper.addDatalogUploadRecord(datalogUploadRecord);
            sqlSession.commit();
            System.out.println("aaaaaaaaaaaaaaaa" + i1 + "bbbbbbbbbbbbb");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }

}
