package com.workdoc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Value("${upload.css}")
    private String uploadCss;
    @Value("${upload.scss}")
    private String uploadScss;
    @Value("${upload.js}")
    private String uploadJs;
    @Value("${upload.img}")
    private String uploadImg;
    @Value("${upload.libs}")
    private String uploadLibs;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + uploadImg + "/");
        registry.addResourceHandler("/css/**").addResourceLocations("file:" + uploadCss + "/");
        registry.addResourceHandler("/scss/**").addResourceLocations("file:" + uploadScss + "/");
        registry.addResourceHandler("/js/**").addResourceLocations("file:" + uploadJs + "/");
        registry.addResourceHandler("/libs/**").addResourceLocations("file:" + uploadLibs + "/");
    }
}