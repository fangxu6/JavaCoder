package singletonlock;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private FileWriter writer;

    public Logger() throws IOException {
        File file = new File("/Users/wangzheng/log.txt");
        writer = new FileWriter(file, true); //true表示追加写入
    }

    public void log(String message) throws IOException {
        writer.write(message);
    }
}
//
//// Logger类的应用示例：
//public class UserController {
//    private Logger logger = new Logger();
//
//    public void login(String username, String password) {
//        // ...省略业务逻辑代码...
//        logger.log(username + " logined!");
//    }
//}
//
//public class OrderController {
//    private Logger logger = new Logger();
//
//    public void create(OrderVo order) {
//        // ...省略业务逻辑代码...
//        logger.log("Created an order: " + order.toString());
//    }
//}
