package com.idea.sky;

import com.idea.sky.component.SchedulingRunnable;
import com.idea.sky.utils.CronTaskRegistrar;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ScheduleApplicationTests {
	@Autowired
	private CronTaskRegistrar cronTaskRegistrar;

	@Test
	void contextLoads() {
		SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "hello world");

		cronTaskRegistrar.addCronTask(task, "0/1 * * * * ?");

		task.run();
	}
}
