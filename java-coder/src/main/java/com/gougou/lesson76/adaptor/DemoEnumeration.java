package com.gougou.lesson76.adaptor;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

public class DemoEnumeration {
    public static void main(String[] args) {
//        Collections.enumeration();
        StringBuilder stringBuilder = new StringBuilder();
        String ss = "abc";
        char[] chs = new char[5];
        ss.getChars(0,3,chs,2);

//        System.out.println(ss);
        System.out.println(chs);
    }


}
