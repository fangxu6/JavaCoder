package com.gougou.adaptor.multiinterface.improve;

// 使用适配器模式进行改造
public interface ISensitiveWordsFilter { // 统一接口定义
    String filter(String text);
}