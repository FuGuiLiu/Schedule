package com.idea.sky.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 相当于这是个springApplication.xml文件 ,标注这个项目是个测试类
 */

@Configuration
public class DruidConfig {

    /**
     * @ConfigurationProperties 与配置文件绑定
     * @Bean 将数据源引入到容器中
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource DruidDataSources() {
        return new DruidDataSource();
    }

    /**
     * 放置springboot容器中
     * 配置 druid 后台监视系统
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletBean() {
        ServletRegistrationBean<Servlet> servletBean = new ServletRegistrationBean<>();
        //设置访问路径
        servletBean.setServlet(new StatViewServlet());
        servletBean.addUrlMappings("/druid/*");
        //初始化初始化参数
        Map<String, String> initParameters = new HashMap<String, String>();
//        配置初始化参数 设置用户名和密码
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "admin_");
//        允许谁可以访问,如果 第一个参数为空,代表所有人都可以访问
        initParameters.put("allow", "127.0.0.1");
        //设置禁止某些客户端访问
//        initParameters.put("用户名", "用户ip");
        servletBean.setInitParameters(initParameters);
        return servletBean;
    }

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean getFilterBean() {
        FilterRegistrationBean<Filter> filterBean = new FilterRegistrationBean<>();
//        设置网络统计过滤器
        filterBean.setFilter(new WebStatFilter());
        //初始化配置参数
        Map<String, String> initParameters = new HashMap<>();
        //排除过滤统计的路径
        initParameters.put("exclusions", "*.css,*.js,/druid/*");
        return filterBean;
    }
}