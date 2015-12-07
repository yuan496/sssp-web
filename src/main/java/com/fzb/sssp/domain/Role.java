package com.fzb.sssp.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 上午10:45:33
 */
@Table(name = "SSSP_ROLE")
@Entity
@XmlRootElement
public class Role implements Serializable {
	
	/**
	 * TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2638442790902536124L;
	private Integer id;
	private String code;
	private String name;
	private String addPm;
	private String modifyPm;
	private String deletePm;
	private String queryPm;
	private Date createTime;
	private Date updateTime;
	private Date deleteTime;
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddPm() {
		return addPm;
	}
	
	public void setAddPm(String addPm) {
		this.addPm = addPm;
	}
	
	public String getModifyPm() {
		return modifyPm;
	}
	
	public void setModifyPm(String modifyPm) {
		this.modifyPm = modifyPm;
	}
	
	public String getDeletePm() {
		return deletePm;
	}
	
	public void setDeletePm(String deletePm) {
		this.deletePm = deletePm;
	}
	
	public String getQueryPm() {
		return queryPm;
	}
	
	public void setQueryPm(String queryPm) {
		this.queryPm = queryPm;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	public Date getDeleteTime() {
		return deleteTime;
	}
	
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
}
