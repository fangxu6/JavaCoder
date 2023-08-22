package util;

import bean.FTPInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import mybatis.entity.DatalogUploadRecord;
import mybatis.mapper.DatalogUploadRecordMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * className: FTPUtil
 * package: com.xijie.FT.util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/27 17:30
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);


    public static void main(String[] args) {
//        文件压缩 ok
        String zipFile = "D:\\data\\datalog\\RABBIT-EMT683-CP3-16-230812-230234.zip";
//        List<String> fileList = new ArrayList<>();
//        fileList.add("D:\\datalog\\iML-D23236-01-FT1-FT01.xls");
//        fileList.add("D:\\datalog\\iML-D23236-01-FT1-FT01.cvs");
//        fileList.add("D:\\datalog\\iML-D23236-01-FT1-FT01.STD");
//        XJZipUtil.zipMultiFiles(fileList,outPath);
        FTPInfo ftpInfo = new FTPInfo();
        File ftpFile = new File(System.getProperty("user.dir") + "/res/ftp2.properties");
        ftpInfo.getFTPINFOConfig(ftpFile);
        boolean upload = upload(ftpInfo, zipFile);
        logger.info(String.valueOf(upload));

//        zipFile = "D:\\datalog\\iML-D23236-01-FT1-FT02.zip";
//        upload = upload(ftpInfo, zipFile);
//        System.out.println(upload);
//        zipFile = "D:\\datalog\\iML-D23236-02-FT1-FT01.zip";
//        upload = upload(ftpInfo, zipFile);
//        System.out.println(upload);


    }

    /**
     * 上传到Ftp
     *
     * @param fileName
     */
    public static boolean upload(FTPInfo ftpInfo, String fileName) {

        Ftp ftp = null;
        try {
            //服务器不需要代理访问
            String host = ftpInfo.host;
            Integer port = ftpInfo.port;
            String user = ftpInfo.user;
            String password = ftpInfo.password;
            ftp = new Ftp(host, port, user, password);
//            ftp = new Ftp("127.0.0.1", 21, "admin", "12345680");
            //服务器需要代理访问，才能对外访问
//            ftp = new Ftp(host, port, user, password, CharsetUtil.CHARSET_UTF_8, FtpMode.Passive);
//            ftp = new Ftp("127.0.0.1", 21, "admin", "12345680", CharsetUtil.CHARSET_UTF_8, FtpMode.Passive);

            File zipFile = FileUtil.file(fileName);
            String zipFileName = zipFile.getName();
            List<String> list = XJSplitUtil.split(zipFileName, '-');
            List<String> subList = new ArrayList<String>(list.subList(0, 4));
            String lastDir = String.join("-", subList);
//            subList.set(subList.size() - 1, lastDir);
            String destPath = ftpInfo.destPath;
            if (!ftp.existFile(destPath)) {
                ftp.mkdir(destPath);
                ftp.cd(destPath);
            } else {
                ftp.cd(destPath);
            }
            mkdir(ftp, subList);
//            String destPath = ftpInfo.destPath;
            boolean upload = ftp.upload("", FileUtil.file(zipFile));
            logger.info("upload status:" + upload + "; uploadfile:" + fileName);
            return upload;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ftp != null) {
                    ftp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Ftp创建目录
     *
     * @param dir
     */
    public static boolean mkdir(Ftp ftp, String dir) {
        ftp.mkdir(dir);
        return true;
    }

    /**
     * Ftp创建目录
     *
     * @param dirList
     */
    public static boolean mkdir(Ftp ftp, List<String> dirList) {
        for (String dir : dirList) {
            ftp.mkdir(dir);
            ftp.cd(dir);
        }
        return true;
    }



}
