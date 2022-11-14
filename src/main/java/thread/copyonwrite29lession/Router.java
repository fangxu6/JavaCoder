package main.java.thread.copyonwrite29lession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

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
    private final String  iface;
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
    }
}
//路由表信息
public class RouterTable {
    //Key:接口名
    //Value:路由集合
    ConcurrentHashMap<String, CopyOnWriteArraySet<Router>>
            rt = new ConcurrentHashMap<>();
    //根据接口名获取路由表
    public Set<Router> get(String iface){
        return rt.get(iface);
    }
    //删除路由
    public void remove(Router router) {
        Set<Router> set=rt.get(router.iface);
        if (set != null) {
            set.remove(router);
        }
    }
    //增加路由
    public void add(Router router) {
        Set<Router> set = rt.computeIfAbsent(
                route.iface, r ->
                        new CopyOnWriteArraySet<>());
        set.add(router);
    }
}
