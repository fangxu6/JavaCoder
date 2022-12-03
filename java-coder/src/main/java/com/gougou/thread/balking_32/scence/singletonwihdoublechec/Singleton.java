package com.gougou.thread.balking_32.scence.singletonwihdoublechec;

/**
 * className:SingletonWithDoubleCheck
 * package:thread.balking_32.scence
 * Description:
 *
 * @Date:2022/11/2010:32
 * @Author:fangxu6@gmail.com
 */

class Singleton{
    private static volatile
    Singleton singleton;
    //构造方法私有化
    private Singleton() {}
    //获取实例（单例）
    public static Singleton
    getInstance() {
        //第一次检查
        if(singleton==null){
            synchronized(Singleton.class){
                //获取锁后二次检查
                if(singleton==null){
                    singleton=new Singleton();
                }
            }
        }
        return singleton;
    }
}
