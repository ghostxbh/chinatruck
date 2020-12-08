package com.uzykj.chinatruck.config;

import com.uzykj.chinatruck.interceptor.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author ghostxbh handler
 */
@Configuration
@EnableWebMvc
public class HandlerConfiguration implements WebMvcConfigurer {
    private static String baseUrl;
    @Resource
    private LoginIntercepter loginIntercepter;

    public HandlerConfiguration(String baseUrl) {
        HandlerConfiguration.baseUrl = baseUrl;
    }

    public HandlerConfiguration(){}
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(HandlerConfiguration.baseUrl, '/');
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:META-INF/resource/wenjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(baseUrl + "/swagger-ui/")
                .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }
}
