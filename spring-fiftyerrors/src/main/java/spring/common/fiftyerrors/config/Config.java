package spring.common.fiftyerrors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * className: Config
 * package: spring.common.fiftyerrors.config
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2022/12/4 12:45
 */

@Configuration
//@ImportResource(locations = {"spring.xml"})
@ImportResource(locations = {"classpath:spring.xml"})
//TODO note 编译后/src/main/resources下的文件最终是不带什么 resources 的。
// 关于这点，你可以直接查看编译后的目录（本地编译后是 target\classes 目录），所以spring.xml
// 不是在target\classes\resources下，而是在target\classes
// note2 spring.xml在测试环境下加载和运行环境下不一致
public class Config {
}
