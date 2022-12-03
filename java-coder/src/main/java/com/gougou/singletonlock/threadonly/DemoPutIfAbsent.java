package com.gougou.singletonlock.threadonly;

import java.util.concurrent.ConcurrentHashMap;

public class DemoPutIfAbsent {
    public static void main(String[] args) {
        ConcurrentHashMap<Long,String> hashMap = new ConcurrentHashMap<>();
        hashMap.putIfAbsent(1L,"111");
        hashMap.putIfAbsent(1L,"111");
        System.out.println(hashMap.values());
//        System.out.println(hashMap.);
    }
}
