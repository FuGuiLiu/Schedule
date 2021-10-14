package com.idea.sky.controller;

import com.idea.sky.component.SchedulingRunnable;
import com.idea.sky.utils.CronTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author Administrator
 */
@Controller
public class IndexController {
	@Resource
	private CronTaskRegistrar cronTaskRegistrar;

	@RequestMapping(value = {"/","/index","/index.html"})
	public ModelAndView test() {
		// SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "hello world");
		//
		// cronTaskRegistrar.addCronTask(task, "0/1 * * * * ?");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "你好,springBoot");
		modelAndView.setViewName("index");
		modelAndView.addObject("users", Arrays.asList("富贵", "sky"));
		return modelAndView;
	}
	@RequestMapping(value = "/cancel")
	public String cancel() {
		cronTaskRegistrar.destroy();
		return "index";
	}
}
