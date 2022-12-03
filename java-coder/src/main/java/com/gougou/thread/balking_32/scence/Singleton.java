package com.gougou.thread.balking_32.scence;

/**
 * className:Singleton
 * package:thread.balking_32.scence
 * Description:
 *
 * @Date:2022/11/2010:32
 * @Author:fangxu6@gmail.com
 */

class Singleton{
    private static
    Singleton singleton;
    //构造方法私有化
    private Singleton(){}
    //获取实例（单例）
    public synchronized static
    Singleton getInstance(){
        if(singleton == null){
            singleton=new Singleton();
        }
        return singleton;
    }
}
