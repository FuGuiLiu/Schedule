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
public class TestController {
	@Resource
	private CronTaskRegistrar cronTaskRegistrar;

	@RequestMapping("/test")
	public ModelAndView test() {
		SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "hello world");

		cronTaskRegistrar.addCronTask(task, "0/1 * * * * ?");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "你好,springBoot");
		modelAndView.setViewName("test");
		modelAndView.addObject("users", Arrays.asList("富贵", "sky"));
		return modelAndView;
	}
}
