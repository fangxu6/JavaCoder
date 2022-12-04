package spring.common.fiftyerrors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import spring.common.fiftyerrors.controller.HelloController;

/**
 * className: ApplicationTests
 * package: spring.common.fiftyerrors
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/4 13:25
 */
//TODO note:测试案例需要和源程序一个package结构
@SpringBootTest()
class ApplicationTests {

    @Autowired
    public HelloController helloController;

    @Test
    public void testController() throws Exception {
        String response = helloController.hi();
        Assert.notNull(response, "not null");
    }

}
