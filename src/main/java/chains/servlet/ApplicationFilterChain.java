//package chains.servlet;
//
//
//import org.apache.catalina.core.ApplicationFilterConfig;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//public final class ApplicationFilterChain implements FilterChain {
//    private int pos = 0; //当前执行到了哪个filter
//    private int n; //filter的个数
//    private ApplicationFilterConfig[] filters;
//    private Servlet servlet;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response) throws ServletException, IOException {
//        if (pos < n) {
//            ApplicationFilterConfig filterConfig = filters[pos++];
//            Filter filter = filterConfig.getFilter();
//            filter.doFilter(request, response, this);
//        } else {
//            // filter都处理完毕后，执行servlet
//            servlet.service(request, response);
//        }
//    }
//
//    public void addFilter(ApplicationFilterConfig filterConfig) {
//        for (ApplicationFilterConfig filter:filters)
//            if (filter==filterConfig)
//                return;
//
//        if (n == filters.length) {//扩容
//            ApplicationFilterConfig[] newFilters = new ApplicationFilterConfig[n + INCREMENT];
//            System.arraycopy(filters, 0, newFilters, 0, n);
//            filters = newFilters;
//        }
//        filters[n++] = filterConfig;
//    }
//}