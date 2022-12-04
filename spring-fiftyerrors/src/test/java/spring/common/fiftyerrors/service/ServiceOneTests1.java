package spring.common.fiftyerrors.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * className: ServiceOneTests1
 * package: spring.common.fiftyerrors.service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/4 14:29
 */
@SpringBootTest
public class ServiceOneTests1 extends ServiceTests {
    @Test
    public void test() {
        System.out.println(serviceOne);
    }
}
