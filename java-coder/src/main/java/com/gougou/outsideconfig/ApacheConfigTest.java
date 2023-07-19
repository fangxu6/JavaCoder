package com.gougou.outsideconfig;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
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
    //客户代码
    private List<String> custCode;
    //工作流代码
    private List<String> workFlow;
    public static void main(String[] args) {

//        System.out.println(System.getProperty("user.dir"));

//        File file = new File("config/custno.properties");
        String fileNameOrPath="custno.properties";
        File file = new File(fileNameOrPath);
        ApacheConfigTest apacheConfigTest = new ApacheConfigTest();
        apacheConfigTest.getConfig(file);
//        System.out.println(file.getAbsolutePath());
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }

    private void getConfig(File file) {
        Configurations configs = new Configurations();
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(file.getName())
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        try
        {
//            Configuration config = configs.properties(file);
            Configuration config = builder.getConfiguration();
            this.custCode = config.getList(String.class, "custCode");
            System.out.println(custCode);
            this.workFlow = config.getList(String.class,"workFlow");
            System.out.println(workFlow);
        }
        catch (ConfigurationException cex)
        {
            // Something went wrong
        }
    }
}
