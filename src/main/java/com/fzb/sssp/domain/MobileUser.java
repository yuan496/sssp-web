/**
 * 
 */
package com.fzb.sssp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhuyuyin
 *
 */
public class MobileUser implements Serializable {

	private static final long serialVersionUID = -3740088164664888154L;

	private Long id;
	private String userName;
	private Date createTime;
	private Date deleteTime;
	private String mobileId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
}
