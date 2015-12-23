package com.engineer.sssp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:06:53
 */
@Cacheable
@Table(name = "SSSP_DEPARTMENT")
@Entity
@XmlRootElement
public class Department implements Serializable {
	
	/**
	 * TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1802075439320846093L;
	private Long id;
	private String code;
	private String name;
	private Department parent;
	private Integer layNo;
	private String layRec;
	protected Set<Department> children = new LinkedHashSet<Department>();
	protected Boolean isParent = false;
	private Boolean leaf = false;
	private Date createTime;
	private Date updateTime;
	private Date deleteTime;
	
	@GeneratedValue
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	
	@Transient
	public Department getParent() {
		return parent;
	}
	
	public void setParent(Department parent) {
		this.parent = parent;
		if (null != this.parent) {
			this.parent.addChild((Department)this);
		}
	}
	
	public void addChild(Department child) {
		children.add(child);
	}
	
	public Integer getLayNo() {
		return layNo;
	}
	
	public void setLayNo(Integer layNo) {
		this.layNo = layNo;
	}
	
	public String getLayRec() {
		return layRec;
	}
	
	public void setLayRec(String layRec) {
		this.layRec = layRec;
	}
	
	@Transient
	public Set<Department> getChildren() {
		return children;
	}
	
	public void setChildren(Set<Department> children) {
		this.children = children;
	}
	
	public void setChildren(List<Department> children) {
		this.children = new LinkedHashSet<Department>();
		this.children.addAll(children);
	}
	
	public void removeChildren() {
		this.children.clear();
	}
	
	@Transient
	public Boolean getIsParent() {
		Boolean isLeaf = getLeaf();
		if (null != isLeaf && isLeaf == Boolean.TRUE) {
			return isParent;
		} else {
			return null == isLeaf || !isLeaf;
		}
	}
	
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	@Transient
	public Boolean isRoot() {
		return null == parent;
	}
	
	public Boolean getLeaf() {
		if (null == this.leaf) {
			return null == children || children.isEmpty();
		} else {
			return leaf;
		}
	}
	
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeleteTime() {
		return deleteTime;
	}
	
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
}
