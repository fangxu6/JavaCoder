import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;

/**
 * className: Test2
 * package: PACKAGE_NAME
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/26 16:55
 */
public class Test2 {
    public static void main(String[] args) {
        String properties=System.getProperty("user.dir") + "/res/custno.properties";
        String key="mysqlInstanceName";
        setProperty2(properties,key);

    }
    public static void setProperty2(String properties, String key) {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(properties)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        try {
            Configuration config = builder.getConfiguration();
            String[] colors = config.getStringArray(key);
            List<Object> colorList = config.getList(key);
            System.out.println(colors);
            System.out.println(colorList);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
