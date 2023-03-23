package com.gwm.avpdatacloud.basicpackages.global.interceptor;

import com.gwm.avpdatacloud.basicpackages.log.GDCLog;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *通过自定义拦截器，为feign全局配置header AUTHORIZATION
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try{
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            requestTemplate.header(HttpHeaders.AUTHORIZATION,httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        }catch (IllegalStateException e){
            GDCLog.error(null,"com.gwm.zjpt.basicpackages.global.interceptor.FeignRequestInterceptor.apply()", e.getMessage());
        }
    }
}
