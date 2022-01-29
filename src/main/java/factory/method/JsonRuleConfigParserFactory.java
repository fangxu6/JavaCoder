package factory.method;

import factory.IRuleConfigParser;
import factory.JsonRuleConfigParser;

/**
 * @author fangxu
 * on date:2022/1/19
 */
public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new JsonRuleConfigParser();
    }
}
