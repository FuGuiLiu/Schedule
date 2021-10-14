package com.idea.sky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表
 */
@ApiModel(value = "com-idea-sky-entity-SystemJob")
public class SystemJob implements Serializable {
	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键id")
	private Long id;

	/**
	 * bean名称
	 */
	@ApiModelProperty(value = "bean名称")
	private String beanName;

	/**
	 * 方法名称
	 */
	@ApiModelProperty(value = "方法名称")
	private String methodName;

	/**
	 * 方法参数
	 */
	@ApiModelProperty(value = "方法参数")
	private String methodParams;

	/**
	 * cron表达式
	 */
	@ApiModelProperty(value = "cron表达式")
	private String cronExpression;

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private Boolean jobStatus;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 创建日期
	 */
	@ApiModelProperty(value = "创建日期")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(String methodParams) {
		this.methodParams = methodParams;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Boolean getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Boolean jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", beanName=").append(beanName);
		sb.append(", methodName=").append(methodName);
		sb.append(", methodParams=").append(methodParams);
		sb.append(", cronExpression=").append(cronExpression);
		sb.append(", jobStatus=").append(jobStatus);
		sb.append(", remark=").append(remark);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append("]");
		return sb.toString();
	}
}