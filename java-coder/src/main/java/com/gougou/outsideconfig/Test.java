package com.gougou.outsideconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * className: Test
 * package: com.gougou.outsideconfig
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/26 9:06
 */
public class Test {

    public static void main(String[] args) throws IOException {

        Properties prop = new Properties();

        InputStream inputStream = null;

        try {

            prop.load(new FileInputStream("res/custno.properties"));

            /* 注释：也可以直接在src/main/resources目录下新建配置文件，但是new FileInputStream("res/myCanal.properties")需要改为new FileInputStream("src/main/resources/myCanal.properties") */

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        String mysqlSchemaName = prop.getProperty("mysqlSchemaName");
        System.out.println(prop.getProperty("mysqlSchemaName"));

        System.out.println(prop.getProperty("mysqlInstanceName"));

    }


}
