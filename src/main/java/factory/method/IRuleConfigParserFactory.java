package factory.method;

import factory.IRuleConfigParser;

/**
 * @author fangxu
 * on date:2022/1/19
 */
public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}
