package com.gougou.factory.method;

import com.gougou.factory.IRuleConfigParser;
import com.gougou.factory.JsonRuleConfigParser;

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
