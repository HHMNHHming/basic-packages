package com.gwm.avpdatacloud.basicpackages.global.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> orderFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setName("WebFilter");
        filter.setFilter(new WebFilter());
        // 指定优先级
        filter.setOrder(-1);
        return filter;
    }
}
