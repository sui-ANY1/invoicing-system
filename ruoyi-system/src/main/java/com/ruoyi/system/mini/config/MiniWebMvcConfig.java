package com.ruoyi.system.mini.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ruoyi.system.mini.interceptor.MiniAuthInterceptor;

/**
 * 小程序 Web MVC 配置：注册小程序鉴权拦截器
 *
 * @author sui-ANY1
 */
@Configuration
public class MiniWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private MiniAuthInterceptor miniAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(miniAuthInterceptor)
                .addPathPatterns("/api/mini/**");
    }
}
