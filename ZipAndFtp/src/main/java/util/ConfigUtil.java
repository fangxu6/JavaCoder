package util;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.List;

/**
 * className: ApacheConfigTest
 * package: com.gougou.outsideconfig
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/26 9:40
 */
public class ConfigUtil {
    public static void main(String[] args) throws ConfigurationException {
        File file = new File(System.getProperty("user.dir") + "/res/custno.properties");
        ConfigUtil apacheConfigTest = new ConfigUtil();
        Configuration config = apacheConfigTest.getConfig(file);
        List<String> custCodeList = config.getList(String.class, "custCode");
        List<String> workFlowList = config.getList(String.class, "workFlow");
    }

    public Configuration getConfig(File file) throws ConfigurationException {
//        Configurations configs = new Configurations();
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(file.getAbsolutePath())
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
//        try {
////            Configuration config = configs.properties(file);
            return builder.getConfiguration();
//        } catch (ConfigurationException cex) {
//            // Something went wrong
//        }
////       TODO fix
//        return null;
    }
}
