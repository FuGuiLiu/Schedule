package com.idea.sky.controller;

import com.alibaba.fastjson.JSON;
import com.idea.sky.component.SchedulingRunnable;
import com.idea.sky.entity.SystemJob;
import com.idea.sky.service.SystemJobServiceImpl;
import com.idea.sky.utils.CronTaskRegistrar;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
public class IndexController {

	public static final Logger logger = LogManager.getLogger(IndexController.class);

	// 校验正则表达式是否正确 cron表达式正则规则
	public static final String regEx = "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$";

	@Resource
	private CronTaskRegistrar cronTaskRegistrar;

	@Resource
	private SystemJobServiceImpl systemJobService;

	@RequestMapping(value = {"/", "/index", "/index.html"})
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/getAllData")
	@ResponseBody
	public Object getAllData() {
		List<SystemJob> systemJobs = systemJobService.queryAllSystemJob();


		if (!CollectionUtils.isEmpty(systemJobs)) {
			return JSON.toJSON(systemJobs);
		}

		return "";
	}


	@RequestMapping(value = "/cancel")
	public String cancel() {
		cronTaskRegistrar.destroy();
		return "index";
	}

	@RequestMapping(value = "/insertNew")
	@ResponseBody
	public Object insertNew(SystemJob systemJob) {
		HashMap<String, Object> resultMap = new HashMap();
		if (!systemJob.getCronExpression().matches(regEx)) {
			resultMap.put("errorMsg", "cron表达式语法错误!");
			return resultMap;
		}

		Date date = new Date();
		systemJob.setCreateTime(date);
		systemJob.setUpdateTime(date);

		try {
			int i = systemJobService.insertSelective(systemJob);
			if (i == 0) {
				resultMap.put("errorMsg", "添加任务失败,请联系管理员");
			} else {
				resultMap.put("success", "新增成功");
				if (systemJob.getJobStatus()) {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					cronTaskRegistrar.addCronTask(task, systemJob.getCronExpression());
				}
			}

		} catch (Exception e) {
			resultMap.put("errorMsg", e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/getDataByBeanName")
	@ResponseBody
	public Object getDataByBeanName(String id) {
		SystemJob systemJob = systemJobService.selectByPrimaryKey(Long.valueOf(id));

		if (null != systemJob) {
			return JSON.toJSON(systemJob);
		}

		return "";
	}

	@RequestMapping(value = "/getAllBeanName")
	@ResponseBody
	public Object getAllBeanName() {
		List<SystemJob> systemJobs = systemJobService.queryAllSystemJob();

		if (!CollectionUtils.isEmpty(systemJobs)) {

			HashMap<String, Object>[] arr = new HashMap[systemJobs.size()];
			for (int i = 0; i < systemJobs.size(); i++) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("title", systemJobs.get(i).getBeanName());
				map.put("id", systemJobs.get(i).getId());
				arr[i] = map;
			}
			return JSON.toJSON(arr);
		}

		return "";
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(String id) {
		HashMap<String, Object> resultMap = new HashMap();
		try {
			int i = systemJobService.deleteByPrimaryKey(Long.valueOf(id));
			if (i == 0) {
				resultMap.put("errorMsg", "删除失败,请联系管理员");
			} else {
				SystemJob systemJob = systemJobService.selectByPrimaryKey(Long.valueOf(id));
				if (systemJob.getJobStatus()) {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					cronTaskRegistrar.removeCronTask(task);
				}

				resultMap.put("success", "删除成功");
				return JSON.toJSON(resultMap);
			}

		} catch (Exception e) {
			resultMap.put("errorMsg", e.getMessage());
		}

		return "";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(SystemJob systemJob) {
		HashMap<String, Object> resultMap = new HashMap();
		if (!systemJob.getCronExpression().matches(regEx)) {
			resultMap.put("errorMsg", "cron表达式语法错误!");
			return resultMap;
		}

		try {
			int i = systemJobService.updateByPrimaryKeySelective(systemJob);
			if (i == 0) {
				resultMap.put("errorMsg", "修改失败,请联系管理员");
			} else {
				//先移除再添加
				if (systemJob.getJobStatus()) {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					cronTaskRegistrar.removeCronTask(task);
					cronTaskRegistrar.addCronTask(task, systemJob.getCronExpression());
				}

				if (!systemJob.getJobStatus()) {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					cronTaskRegistrar.removeCronTask(task);
				}


				resultMap.put("success", "修改成功");
				return JSON.toJSON(resultMap);
			}

		} catch (Exception e) {
			resultMap.put("errorMsg", e.getMessage());
		}

		return "";
	}

	@RequestMapping(value = "/statusChange")
	@ResponseBody
	public Object statusChange(String id) {
		HashMap<String, Object> resultMap = new HashMap();
		if (StringUtils.isNotBlank(id)) {
			try {
				logger.error("根据id查询任务信息...");
				SystemJob systemJob = systemJobService.selectByPrimaryKey(Long.valueOf(id));
				if (!systemJob.getJobStatus()) {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					systemJob.setJobStatus(true);
					systemJobService.updateByPrimaryKey(systemJob);
					cronTaskRegistrar.addCronTask(task, systemJob.getCronExpression());
					logger.error("启动任务... | 任务名称:" + systemJob.getBeanName());
					resultMap.put("success", "启动任务...");
					return resultMap;
				} else {
					SchedulingRunnable task = new SchedulingRunnable(systemJob.getBeanName(), systemJob.getMethodName(), systemJob.getMethodParams(),cronTaskRegistrar);
					systemJob.setJobStatus(false);
					systemJobService.updateByPrimaryKey(systemJob);
					cronTaskRegistrar.removeCronTask(task);
					logger.error("暂停任务... | 任务名称:" + systemJob.getBeanName());
					resultMap.put("success", "暂停任务...");
					return resultMap;
				}
			} catch (NumberFormatException e) {
				logger.error("将id转换为数字格式化,发生异常--->" + e.getMessage());
				resultMap.put("errorMsg", "将id转换为数字格式化,发生异常--->" + e.getMessage());
			} catch (Exception e) {
				logger.error("任务状态变更,发生异常--->" + e.getMessage());
				resultMap.put("errorMsg", "任务状态变更,发生异常--->" + e.getMessage());
			}
			logger.error("状态切换成功...");
		}
		return "";
	}
}
