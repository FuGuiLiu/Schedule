package com.idea.sky.component;

import com.idea.sky.entity.SystemJob;
import com.idea.sky.service.impl.SystemJobService;
import com.idea.sky.utils.CronTaskRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @packageName:com.idea.sky.component
 * @ClassName:SysJobRunner
 * @Description:
 * @Author:
 * @Data:2021/10/26@Time:16:41
 */
@Service
public class SysJobRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

	@Autowired
	private SystemJobService systemJobService;

	@Autowired
	private CronTaskRegistrar cronTaskRegistrar;

	@Override
	public void run(String... args) {
		// 初始加载数据库里状态为正常的定时任务
		List<SystemJob> jobList = systemJobService.queryActivedJob();
		if (!CollectionUtils.isEmpty(jobList)) {
			for (SystemJob job : jobList) {
				SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
				cronTaskRegistrar.addCronTask(task, job.getCronExpression());
			}

			logger.info("定时任务已加载完毕...");
		}
	}
}