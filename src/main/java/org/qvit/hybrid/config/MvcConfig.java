package org.qvit.hybrid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index");
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new UserCheckInterceptor());
    	registry.addInterceptor(new MenuCheckInterceptor());
	}

}
