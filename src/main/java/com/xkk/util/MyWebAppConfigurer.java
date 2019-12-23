package com.xkk.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
;
        registry.addResourceHandler("/byd_touxiang/**").addResourceLocations("file:C:/byd_touxiang/");
        registry.addResourceHandler("/byd_bbs/**").addResourceLocations("file:C:/byd_bbs/");
        registry.addResourceHandler("/byd_ad/**").addResourceLocations("file:C:/byd_ad/");
        registry.addResourceHandler("/byd_web_ui/**").addResourceLocations("file:C:/byd_web_ui/");
        registry.addResourceHandler("/byd_zfb_qr_code/**").addResourceLocations("file:C:/byd_zfb_qr_code/");

    }

}
