package com.idea.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author Administrator
 * 开启swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * 初始化作者信息
	 * 设置作者名称,url,和邮箱地址
	 */
	private static final Contact DEFAULT_CONTACT = new Contact("刘富贵", "http://www.adsencehutter.com/", "2677672@gmail.com");

	@Bean
	public Docket docket(Environment environment) {
		// 设置指定当前应用程序的的运行环境
		Profiles profiles = Profiles.of("dev", "test");
		// 判断当前运行环境是否为接受profile设置的环境
		boolean flag = environment.acceptsProfiles(profiles);
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("富贵")
				.apiInfo(getApiInfo())

				// enable() 是否启用Swagger默认为true,如果为false,则在浏览器中不能访问Swagger
				.enable(flag)
				.select()
				// apis() 根据接口来进行过滤 RequestHandlerSelectors 配置请求选择处理器,扫描接口的方式

				// basePackage()  指定特定的路径去构建指定的接口
				// .apis(RequestHandlerSelectors.basePackage("com.idea.sky"))
				// any() 请求处理选择器处理所有的接口
				// .apis(RequestHandlerSelectors.any())
				// none() 请求处理器不处理任何接口
				// .apis(RequestHandlerSelectors.none())
				// withClassAnnotation() 请求处理器处理标注了指定的注解的类下的API接口
				.apis(RequestHandlerSelectors.any())
				// withMethodAnnotation() 请求处理器处理标注了指定的注解的方法下的API接口
				// .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
				// paths() 设置指定的路径下,来进行过滤  PathSelectors 配置路径选择器

				// ant() 过滤指定路径
				// .paths(PathSelectors.ant("/controller/**"))
				// none() 不配置任何路径
				// .paths(PathSelectors.none())
				// none() 配置所有的路径
				.paths(PathSelectors.any())
				// regex() 根据正则表达式来配置路径
				// .paths(PathSelectors.regex(""))
				.build();
	}

	// @Bean
	// public Docket docketA() {
	// 	return new Docket(DocumentationType.SWAGGER_2).groupName("A");
	// }
	//
	// @Bean
	// public Docket docketB() {
	// 	return new Docket(DocumentationType.SWAGGER_2).groupName("B");
	// }

	public ApiInfo getApiInfo() {
		return new ApiInfo("刘富贵的Swagger", "无可匹敌的力量", "6.0", "urn:tos",
				DEFAULT_CONTACT, "Apache 2.0", "http://www.onmyway.cf/", new ArrayList<VendorExtension>());
	}
}