package com.idea.sky.component;

import com.idea.sky.utils.CronTaskRegistrar;
import com.idea.sky.utils.spring.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class SchedulingRunnable implements Runnable {

	private static final Logger logger = LogManager.getLogger(SchedulingRunnable.class);

	private final String beanName;

	private final String methodName;

	private final String params;

	private CronTaskRegistrar cronTaskRegistrar;

	public SchedulingRunnable(String beanName, String methodName) {
		this(beanName, methodName, null);
	}

	public SchedulingRunnable(String beanName, String methodName, String params) {
		this.beanName = beanName;
		this.methodName = methodName;
		this.params = params;
	}

	public SchedulingRunnable(String beanName, String methodName, String params, CronTaskRegistrar cronTaskRegistrar) {
		this.beanName = beanName;
		this.methodName = methodName;
		this.params = params;
		this.cronTaskRegistrar = cronTaskRegistrar;
	}

	@Override
	public void run() {
		logger.error("定时任务开始执行 - bean:{},方法:{},参数:{}", beanName, methodName, params);
		long startTime = System.currentTimeMillis();

		try {
			Object target = SpringContextUtils.getBean(beanName);

			Method method = null;
			if (StringUtils.isNotEmpty(params)) {
				method = target.getClass().getDeclaredMethod(methodName, String.class);
			} else {
				method = target.getClass().getDeclaredMethod(methodName);
			}

			ReflectionUtils.makeAccessible(method);
			if (StringUtils.isNotEmpty(params)) {
				method.invoke(target, params);
			} else {
				method.invoke(target);
			}
		} catch (Exception ex) {
			logger.error(String.format("定时任务执行异常 - bean:%s,方法:%s,参数:%s ", beanName, methodName, params), ex);
			logger.error("发生异常,自动停止任务 killed this task" + beanName);
			cronTaskRegistrar.removeCronTask(this);
			// TODO: 2021/10/26 记录异常记录到日志表
		}

		long times = System.currentTimeMillis() - startTime;
		logger.error("定时任务执行结束 - bean:{},方法:{},参数:{},耗时:{} 毫秒", beanName, methodName, params, times);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SchedulingRunnable that = (SchedulingRunnable) o;
		if (params == null) {
			return beanName.equals(that.beanName) &&
					methodName.equals(that.methodName) &&
					that.params == null;
		}

		return beanName.equals(that.beanName) &&
				methodName.equals(that.methodName) &&
				params.equals(that.params);
	}

	@Override
	public int hashCode() {
		if (params == null) {
			return Objects.hash(beanName, methodName);
		}

		return Objects.hash(beanName, methodName, params);
	}
}