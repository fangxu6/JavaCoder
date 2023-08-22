package mybatis.service;

import cn.hutool.core.io.FileUtil;
import mybatis.entity.DatalogUploadRecord;
import mybatis.mapper.DatalogUploadRecordMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FTPUtil;
import util.SqlSessionFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * className: DatalogUploadRecordIml
 * package: mybatis.service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/18 10:23
 */
public class DatalogUploadRecordIml {
    private static Logger logger = LoggerFactory.getLogger(DatalogUploadRecordIml.class);

    private static SqlSessionFactory sqlSessionFactory;

    public static int insertIntoTable(String zipFileName, List<File> fileList) {

        try {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Socket socket = new Socket();
        InetAddress localAddress;
        try {
            socket.connect(new InetSocketAddress("192.168.5.244", 22));
            localAddress = socket.getLocalAddress();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        DatalogUploadRecord datalogUploadRecord = new DatalogUploadRecord();
//        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        datalogUploadRecord.setIp(localAddress.getHostAddress());
        datalogUploadRecord.setZipFile(zipFileName);
//        datalogUploadRecord.setZipFile("33test.zip");
        datalogUploadRecord.setStatus(0);
        if (fileList.size() > 7) {
            logger.warn("file size larger than 7, please check the rule or change the rule!");
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
            datalogUploadRecord.setDatalog4(fileList.get(3).getName());
            datalogUploadRecord.setDatalog5(fileList.get(4).getName());
            datalogUploadRecord.setDatalog6(fileList.get(5).getName());
            datalogUploadRecord.setDatalog7(fileList.get(6).getName());
        }
        if (fileList.size() == 7) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
            datalogUploadRecord.setDatalog4(fileList.get(3).getName());
            datalogUploadRecord.setDatalog5(fileList.get(4).getName());
            datalogUploadRecord.setDatalog6(fileList.get(5).getName());
            datalogUploadRecord.setDatalog7(fileList.get(6).getName());
        } else if (fileList.size() == 6) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
            datalogUploadRecord.setDatalog4(fileList.get(3).getName());
            datalogUploadRecord.setDatalog5(fileList.get(4).getName());
            datalogUploadRecord.setDatalog6(fileList.get(5).getName());
        } else if (fileList.size() == 5) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
            datalogUploadRecord.setDatalog4(fileList.get(3).getName());
            datalogUploadRecord.setDatalog5(fileList.get(4).getName());
        } else if (fileList.size() == 4) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
            datalogUploadRecord.setDatalog4(fileList.get(3).getName());
        } else if (fileList.size() == 3) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
            datalogUploadRecord.setDatalog3(fileList.get(2).getName());
        } else if (fileList.size() == 2) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
            datalogUploadRecord.setDatalog2(fileList.get(1).getName());
        } else if (fileList.size() == 1) {
            datalogUploadRecord.setDatalog1(fileList.get(0).getName());
        }

        datalogUploadRecord.setCreateTime(System.currentTimeMillis());

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            DatalogUploadRecordMapper datalogUploadRecordMapper = (DatalogUploadRecordMapper) sqlSession.getMapper(DatalogUploadRecordMapper.class);
            int i1 = datalogUploadRecordMapper.addDatalogUploadRecord(datalogUploadRecord);
            sqlSession.commit();
            logger.info("DatalogUploadRecord insert：" + datalogUploadRecord.getIp());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

        return 0;
    }

    public static int updateByZipFile(String zipFileName) {

        try {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File zipFile = FileUtil.file(zipFileName);
        String fileName = zipFile.getName();
        DatalogUploadRecord datalogUploadRecord = selectByZipFile(fileName);

        datalogUploadRecord.setUpdateTime(System.currentTimeMillis());
        datalogUploadRecord.setStatus(1);

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            DatalogUploadRecordMapper datalogUploadRecordMapper = (DatalogUploadRecordMapper) sqlSession.getMapper(DatalogUploadRecordMapper.class);
            int i1 = datalogUploadRecordMapper.updateByPrimaryKey(datalogUploadRecord);
            sqlSession.commit();
            logger.info("DatalogUploadRecord update：" + datalogUploadRecord.getIp() + "; zipfile:" + zipFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

        return 0;
    }

    public static DatalogUploadRecord selectByZipFile(String zipFileName) {

        try {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = SqlSessionFactoryUtil.initSqlSessionFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatalogUploadRecord datalogUploadRecord = new DatalogUploadRecord();

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            DatalogUploadRecordMapper datalogUploadRecordMapper = (DatalogUploadRecordMapper) sqlSession.getMapper(DatalogUploadRecordMapper.class);
            datalogUploadRecord = datalogUploadRecordMapper.selectByZipFileName(zipFileName);
            sqlSession.commit();
            logger.info("DatalogUploadRecord select ：" + datalogUploadRecord.getZipFile());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

        return datalogUploadRecord;
    }
}
