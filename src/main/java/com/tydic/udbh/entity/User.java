package com.tydic.udbh.entity;

import java.util.Date;

public class User {
    public User() {
		super();
    }
    private Date createTime;

    public User(String userName, String password, String realName, int i) {

    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	private Integer id;

	private String userName;

	private String password;

	private String realName;

	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [createTime=" + createTime + ", id=" + id + ", userName=" + userName + ", password=" + password
				+ ", realName=" + realName + ", status=" + status + "]";
	}

	public User(Date createTime, String userName, String password, String realName, Integer status) {
		super();
		this.createTime = createTime;
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.status = status;
	}

	
	


}
