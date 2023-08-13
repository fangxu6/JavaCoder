package mybatis;

import mybatis.entity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.SqlSessionFactoryUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * className: TestMybatis
 * package: mybatis
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/11 14:54
 */
public class TestMybatis {
    private static SqlSessionFactory sqlSessionFactory;
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
//            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            if (sqlSessionFactory == null) {
                sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
            }
//                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        SqlSession sqlSession = null;
//        try {
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
}
