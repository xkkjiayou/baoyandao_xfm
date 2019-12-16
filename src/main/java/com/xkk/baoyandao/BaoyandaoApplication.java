package com.xkk.baoyandao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableCaching  //开启缓存
//存在注解的包
@ComponentScan(basePackages = {"com.xkk.controller","com.xkk.service","com.xkk.util"})
@MapperScan(basePackages = {"com.xkk.dao"})
public class BaoyandaoApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BaoyandaoApplication.class, args);
	}
	@Override//为了打包springboot项目
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
}
