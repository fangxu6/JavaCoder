package proxy.dynamic;

public class DemoDynamicMetricsCollector {
    //MetricsCollectorProxy使用举例
    MetricsCollectorProxy proxy = new MetricsCollectorProxy();
    IUserController userController = (IUserController) proxy.createProxy(new UserController());
}
