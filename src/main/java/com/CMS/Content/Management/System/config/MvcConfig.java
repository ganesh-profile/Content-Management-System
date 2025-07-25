package com.CMS.Content.Management.System.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {

    public void addResourceHandler(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/uploads/");

    }
}
