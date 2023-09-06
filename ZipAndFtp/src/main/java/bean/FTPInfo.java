package bean;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import util.ConfigUtil;

import java.io.File;

/**
 * className: FTPInfo
 * package: com.xijie.FT.bean
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/28 9:40
 */
public class FTPInfo {
    public String host;

    public int port;

    public String user;

    public String password;

    public String destPath;

    public String custNo;

    public FTPInfo(String host, int port, String user, String password, String destPath) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.destPath = destPath;
    }

    public FTPInfo() {

    }

    public void getFTPINFOConfig(File ftpFile) {
        ConfigUtil configUtil = new ConfigUtil();

        Configuration config = null;

        try {
            config = configUtil.getConfig(ftpFile);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        this.host = config.getString("host");
        this.port = Integer.valueOf(config.getString("port"));
        this.user = config.getString("user");
        this.password = config.getString("password");
        this.destPath = config.getString("destPath");
        this.custNo = config.getString("custNo");
    }
}
