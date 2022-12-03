package com.gougou.thread.copyonwrite29lession;

/**
 * className:Router
 * package:main.java.thread.copyonwrite29lession
 * Description:
 *
 * @Date:2022/11/1421:47
 * @Author:fangxu6@gmail.com
 */

//路由信息
public final class Router{
    private final String  ip;
    private final Integer port;
    final String  iface;
    //构造函数
    public Router(String ip,
                  Integer port, String iface){
        this.ip = ip;
        this.port = port;
        this.iface = iface;
    }
    //重写equals方法
    public boolean equals(Object obj){
        if (obj instanceof Router) {
            Router r = (Router)obj;
            return iface.equals(r.iface) &&
                    ip.equals(r.ip) &&
                    port.equals(r.port);
        }
        return false;
    }
    public int hashCode() {
        //省略hashCode相关代码
        return 0;
    }
}

