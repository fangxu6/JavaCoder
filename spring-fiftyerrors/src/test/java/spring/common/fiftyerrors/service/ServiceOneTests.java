package spring.common.fiftyerrors.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * className: ServiceOneTests
 * package: spring.common.fiftyerrors.service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/4 14:25
 */

@SpringBootTest()
class ServiceOneTests {

    @MockBean
    ServiceOne serviceOne;

    @Test
    public void test(){
        System.out.println(serviceOne);
    }
}

@SpringBootTest()
class ServiceTwoTests {
    @MockBean
    ServiceTwo serviceTwo;
    @Test
    public void test(){
        System.out.println(serviceTwo);
    }
}
