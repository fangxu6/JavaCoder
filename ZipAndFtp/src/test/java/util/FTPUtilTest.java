package util;

import bean.FTPInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * className: FTPUtil
 * package: util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/9/6 11:09
 */
public class FTPUtilTest {
    private static Logger logger = LoggerFactory.getLogger(FTPUtilTest.class);

    @Test
    public void upload() {
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



}
