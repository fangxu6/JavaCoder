package com.gougou.factory.method;

import com.gougou.factory.IRuleConfigParser;

/**
 * @author fangxu
 * on date:2022/1/19
 */
public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}
