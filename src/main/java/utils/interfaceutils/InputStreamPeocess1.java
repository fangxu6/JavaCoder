package utils.interfaceutils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Description :
 * @Author: Liruilong
 * @Date: 2020/4/14 16:39
 */
@FunctionalInterface
public interface InputStreamPeocess1 {
    /**
     * @Author Liruilong
     * @Description 方法签名 BufferedReader ->String
     * @Date 15:47 2020/3/17
     * @Param [inputStream]
     * @return com.liruilong.demotext.service.utils.InputStream
     **/

    void peocess(BufferedReader bufferedReader) throws IOException;
}


