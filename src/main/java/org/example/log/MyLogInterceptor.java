package org.example.log;

import org.example.log.MyLog;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 自定义日志拦截器
 */
public class MyLogInterceptor extends HandlerInterceptorAdapter {
    /**
     * 使用ThreadLocal记录用时
     */
    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 在controller方法执行之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 判断拦截的方法是否加了@MyLog注解
        if (handlerMethod.getMethodAnnotation(MyLog.class) != null) {
            // 记录开始时间
            TIME_THREAD_LOCAL.set(System.currentTimeMillis());
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 在controller方法执行之后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 判断拦截的方法是否加了@MyLog注解
        if (handlerMethod.getMethodAnnotation(MyLog.class) != null) {
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            // 计算用时
            long time = endTime - TIME_THREAD_LOCAL.get();
            // 输出 请求uri、方法名、方法描述、用时
            System.out.println("请求uri：" + request.getRequestURI());
            System.out.println("方法名：" + handlerMethod.getMethod().getName());
            System.out.println("方法描述：" + Objects.requireNonNull(handlerMethod.getMethodAnnotation(MyLog.class)).value());
            System.out.println("用时：" + time + "ms");
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
