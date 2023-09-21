package util;

import bean.FTPInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import mybatis.entity.DatalogUploadRecord;
import mybatis.mapper.DatalogUploadRecordMapper;
import mybatis.service.DatalogUploadRecordIml;
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

            File zipFile = FileUtil.file(fileName);

            if (ftpInfo.custNo.equals("aisino")) {
                String zipFileName = zipFile.getName().toUpperCase();
                List<String> list = XJSplitUtil.split(zipFileName, '-');
                List<String> subList = new ArrayList<String>(list.subList(0, 4));
                String destPath = ftpInfo.destPath;
                if (!ftp.existFile(destPath)) {
                    ftp.mkdir(destPath);
                    ftp.cd(destPath);
                } else {
                    ftp.cd(destPath);
                }
                mkdir(ftp, subList);
            }

            boolean upload = ftp.upload("", FileUtil.file(zipFile));
            logger.info("upload status:" + upload + "; uploadfile:" + fileName);

            boolean del = FileUtil.del(zipFile);
            logger.info("delete local file status:" + del);

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
     * @param dirList
     */
    public static boolean mkdir(Ftp ftp, List<String> dirList) {
        for (String dir : dirList) {
            ftp.mkdir(dir);
            ftp.cd(dir);
        }
        return true;
    }


    //Aisino FTP
    public static void ftpUpload(List<String> zipFileList) {
        FTPInfo ftpInfo = new FTPInfo();
        File ftpConfigFile = new File(System.getProperty("user.dir") + "/res/ftp.properties");
        ftpInfo.getFTPINFOConfig(ftpConfigFile);
        for (String zipFile : zipFileList) {
            boolean upload = FTPUtil.upload(ftpInfo, zipFile);
//            if (upload) {
//                DatalogUploadRecordIml.updateByZipFile(zipFile);
//            }
        }
    }

    public static String getHostAddress() {
        Socket socket = new Socket();
        InetAddress localAddress;
        try {
            socket.connect(new InetSocketAddress("192.168.5.244", 22));
            localAddress = socket.getLocalAddress();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String hostAddress = localAddress.getHostAddress();

        return hostAddress;
    }


}
