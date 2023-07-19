import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * className: ApacheConfigTest
 * package: com.gougou.outsideconfig
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/26 9:40
 */
public class ApacheConfigTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        Configurations configs = new Configurations();
//        File file = new File("config/custno2.properties");
        File file = new File(System.getProperty("user.dir") + "/res/custno.properties");
        System.out.println(file.getAbsolutePath());
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(System.getProperty("user.dir") + "/res/custno.properties")
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));


        try {

//            FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder(new File("custno2.properties"));
//            Configuration config = builder.getConfiguration();
//            printElements(config);
            Configuration config = builder.getConfiguration();
//            Configuration config = configs.properties(file);



            // access configuration properties
            List<String> mysqlInstanceName = config.getList(String.class, "datalogfilePath");
            System.out.println(mysqlInstanceName);

        } catch (ConfigurationException cex) {
            // Something went wrong
        }
    }

    private static void printElements(Configuration config) {
        Iterator<String> keys = config.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = config.getString(key);
            System.out.println("key=" + key + ",value=" + value);
        }
    }
}
