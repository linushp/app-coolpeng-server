package com.coolpeng.framework.db;

import com.coolpeng.framework.utils.DateUtil;

public class BaseEntity
{
	private String id = null;
	private String createTime = DateUtil.currentTimeFormat();
	private String updateTime = DateUtil.currentTimeFormat();
	private String createUserId;
	private String updateUserId;
	private int status = EntityStatusEnum.OK;

	public BaseEntity(BaseEntity e)
	{
		this.id = e.id;
		this.createTime = e.createTime;
		this.updateTime = e.updateTime;
		this.createUserId = e.createUserId;
		this.updateUserId = e.updateUserId;
		this.status = e.status;
	}

	public BaseEntity() {
	}

	public void copyBaseEntity(BaseEntity e){
		this.createTime = e.createTime;
		this.updateTime = e.updateTime;
		this.createUserId = e.createUserId;
		this.updateUserId = e.updateUserId;
		this.status = e.status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}