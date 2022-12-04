package spring.common.fiftyerrors.service;

import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * className: ServiceTests
 * package: spring.common.fiftyerrors.service
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/4 14:28
 */
public class ServiceTests {
    @MockBean
    ServiceOne serviceOne;
    @MockBean
    ServiceTwo serviceTwo;
}