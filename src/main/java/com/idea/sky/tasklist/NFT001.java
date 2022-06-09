package com.idea.sky.tasklist;

import com.idea.sky.request.OkHttpUtils;
import com.idea.sky.thread.ThreadPoolExecutorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component("NFT001")
public class NFT001 {

	public static final Logger logger = LoggerFactory.getLogger(NFT001.class);

	public void taskWithParams(String params) {
		ThreadPoolExecutor poll = ThreadPoolExecutorUtil.getPoll();

		logger.error(String.valueOf(poll.getCorePoolSize()));

		String async = OkHttpUtils.builder().url("https://www.baidu.com").get().async();
		logger.error(async);
		System.out.println("执行带参示例任务" + params);
	}

	public void taskNoParams() {
		ThreadPoolExecutor poll = ThreadPoolExecutorUtil.getPoll();

		logger.error(String.valueOf(poll.getCorePoolSize()));

		System.out.println("执行无参示例任务");
	}
}