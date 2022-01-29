package actiondesign.observer.regesiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class UserController {
    private UserService userService; // 依赖注入
    private List<RegObserver> regObservers = new ArrayList<>();
    private Executor executor;

    public UserController(Executor executor) {
        this.executor = executor;
    }

    // 一次性设置好，之后也不可能动态的修改
    public void setRegObservers(List<RegObserver> observers) {
        regObservers.addAll(observers);
    }

    public Long register(String telephone, String password) {
        //省略输入参数的校验代码
        //省略userService.register()异常的try-catch代码
        long userId = userService.register(telephone, password);


        for (RegObserver observer : regObservers) {
            //同步或者异步第一种，在相应的observer中修改，参考ReqPromotionObserver
//            observer.handleRegSuccess(userId);
            // 第二种实现方式，其他类代码不变，就没有再重复罗列
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observer.handleRegSuccess(userId);
                }
            });
        }

        return userId;
    }
}
