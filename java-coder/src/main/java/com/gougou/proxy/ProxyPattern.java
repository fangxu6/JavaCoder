package com.gougou.proxy;

/**
 * @author fangxu
 * on date:2022/1/4
 */
public class ProxyPattern
{
    public static void main(String[] args)
    {
        Proxy proxy = new Proxy();
        proxy.request();
    }
}
//抽象主题
interface Subject
{
    void request();
}
//真实主题
class RealSubject implements Subject
{
    public void request()
    {
        System.out.println("访问真实主题方法...");
    }
}
//代理
class Proxy implements Subject
{
    private RealSubject realSubject;
    public void request()
    {
        if (realSubject==null)
        {
            realSubject=new RealSubject();
        }
        preRequest();
        realSubject.request();
        afterRequest();
    }
    public void preRequest()
    {
        System.out.println("访问真实主题之前的预处理。");
    }
    public void afterRequest()
    {
        System.out.println("访问真实主题之后的后续处理。");
    }
}