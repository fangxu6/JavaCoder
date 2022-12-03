package com.gougou.Singleton;

/**
 * @author fangxu
 * on date:2022/1/4
 */
public class Singleton
{
    private static volatile Singleton instance=null;    //保证 instance 在所有线程中同步
    private Singleton(){}    //private 避免类在外部被实例化
    public static synchronized Singleton getInstance()
    {
        //getInstance 方法前加同步
        if(instance == null)
        {
            instance = new Singleton();
        }
        return instance;
    }
}
