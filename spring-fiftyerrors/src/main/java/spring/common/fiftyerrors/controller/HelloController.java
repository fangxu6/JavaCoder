package spring.common.fiftyerrors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.common.fiftyerrors.service.HelloWorldService;

/**
 * className: HelloController
 * package: spring.common.fiftyerrors.controller
 * Description:
 *
 * @since 2022/12/4 10:41
 * @author fangxu6@gmail.com
 */

@RestController
@Repository
public class HelloController {

    @Autowired
    HelloWorldService helloWorldService;

    @RequestMapping(path = "hi", method = RequestMethod.GET)
    public String hi() throws Exception{
        return  helloWorldService.toString() ;
    };

}
