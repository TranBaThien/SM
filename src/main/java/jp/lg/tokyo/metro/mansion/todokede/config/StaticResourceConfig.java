/*
 * @(#)StaticResourceConfig.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 * 
 * Description: Class config 
 *
 * Create Date: 2019/11/18
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class StaticResourceConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/", "classpath:/static/");
	}
	
	@Bean
    public VelocityEngine viewResolver() {
	    VelocityEngine velocityEngine = new VelocityEngine();
	    velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    return velocityEngine;
    }
}
