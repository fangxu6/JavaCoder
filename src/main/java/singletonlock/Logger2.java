package singletonlock;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger2 {
    private FileWriter writer;
    private static final Logger2 instance = new Logger2();

    private Logger2() {
        File file = new File("/Users/wangzheng/log.txt");
        try {
            writer = new FileWriter(file, true); //true表示追加写入
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger2 getInstance() {
        return instance;
    }

    public void log(String message) {
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//// Logger类的应用示例：
//public class UserController {
//    public void login(String username, String password) {
//        // ...省略业务逻辑代码...
//        Logger2.getInstance().log(username + " logined!");
//    }
//}
//
//public class OrderController {
//    public void create(String order) {
//        // ...省略业务逻辑代码...
//        Logger2.getInstance().log("Created a order: " + order.toString());
//    }
//}
