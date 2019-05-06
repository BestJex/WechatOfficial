package com.jex.official.config;

import com.jex.official.common.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebContextConfiguration extends WebMvcConfigurationSupport {


    /**
     * 添加静态文件映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico");
    }


    /**
     * 添加登录拦截
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/", "/admin/user/login", "/admin/noSession");
        super.addInterceptors(registry);
    }

    /**
     * 设置post数据到实体类的映射
     * @param argumentResolvers
     */
//    @Override
//    protected void addArgumentResolvers( List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(renamingProcessor());
//    }
//
//    @Bean
//    protected RenamingProcessor renamingProcessor() {
//        return new RenamingProcessor(true);
//    }
}
