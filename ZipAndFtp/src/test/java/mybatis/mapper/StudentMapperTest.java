package mybatis.mapper;

import mybatis.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import util.SqlSessionFactoryUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * User: Homejim
 * Date 2018-9-4
 * Time 20:40
 */
public class StudentMapperTest {
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
    public void addStudent() {

        Student student = new Student();
        student.setEmail("11@test.com");
        student.setName("11test");
        student.setLocked((byte) 0);
        student.setSex((byte)1);
        student.setGmtCreated(new Date());

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = (StudentMapper) sqlSession.getMapper(StudentMapper.class);
            int i1 = studentMapper.addStudent(student);
            sqlSession.commit();
            System.out.println("aaaaaaaaaaaaaaaa"+i1+"bbbbbbbbbbbbb");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }
    @Test
    public void testSelectList() {
        SqlSession sqlSession = null;
        if (sqlSessionFactory == null) {
            sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
        }
        try {
            sqlSession = sqlSessionFactory.openSession();

            List<Student> students = sqlSession.selectList("selectAll");
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testSelectBtweenCreatedTimeMap() {

        Map<String, Object> params = new HashMap<>();
        Calendar bTime = Calendar.getInstance();
        // month 是从0~11， 所以9月是8
        bTime.set(2018, Calendar.AUGUST, 29);
        params.put("bTime", bTime.getTime());

        Calendar eTime = Calendar.getInstance();
        eTime.set(2018,Calendar.SEPTEMBER,2);
        params.put("eTime", eTime.getTime());
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = (StudentMapper) sqlSession.getMapper(StudentMapper.class);
            List<Student> students = studentMapper.selectBetweenCreatedTime(params);
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }

    @Test
    public void testSelectBtweenCreatedTimeAnno() {

        Map<String, Object> params = new HashMap<>();
        Calendar bTime = Calendar.getInstance();
        // month 是从0~11， 所以9月是8
        bTime.set(2018, Calendar.AUGUST, 29);


        Calendar eTime = Calendar.getInstance();
        eTime.set(2018,Calendar.SEPTEMBER,2);

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            StudentMapper studentMapper = (StudentMapper) sqlSession.getMapper(StudentMapper.class);
            List<Student> students = studentMapper.selectBetweenCreatedTimeAnno(bTime.getTime(), eTime.getTime());
            for (int i = 0; i < students.size(); i++) {
                System.out.println(students.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

    }
}
