package com.idea.sky.tasklist;

import org.springframework.stereotype.Component;

@Component("demoTask")
public class DemoTask {

	public void taskWithParams(String params) {
		System.out.println("执行带参示例任务" + params);
	}

	public void taskNoParams() {
		System.out.println("执行无参示例任务");
	}
}