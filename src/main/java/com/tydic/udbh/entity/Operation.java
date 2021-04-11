package com.tydic.udbh.entity;

import java.util.Date;

/**
 * 日志实体类
 * 
 * @author Xie
 * @date2020年4月16日
 */

public class Operation {
	private Integer operationId;

	// 日志操作时间
	private Date operationTime;
	// 日志操作内容
	private String operationContent;
	// 日志操作者

	// 日志操作ip

	private String userName;

	// 请求的ip

	private String operationIp;

	// 日志操作参数
	private String param;

	@Override
	public String toString() {
		return "Operation [operationId=" + operationId + ", operationTime=" + operationTime + ", operationContent="
				+ operationContent + ", userName=" + userName + ", operationIp=" + operationIp + ", param=" + param
				+ "]";
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperationIp() {
		return operationIp;
	}

	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
