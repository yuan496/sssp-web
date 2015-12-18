package com.fzb.sssp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Type;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月7日 上午11:34:06
 */
@Table(name = "SSSP_MENU")
@Entity
@XmlRootElement
public class Menu implements Serializable {
	
	/**
	 * TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3870792703544463500L;
	private Long id;
	private String code;
	private String name;
	private String url;
	private Boolean isAuth = false;
	private Boolean isHide = false;
	private Menu parent;
	private Integer layNo;
	private String layRec;
	protected Set<Menu> children = new LinkedHashSet<Menu>();
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Type(type="yes_no")
	public Boolean getIsAuth() {
		return isAuth;
	}
	
	public void setIsAuth(Boolean isAuth) {
		this.isAuth = isAuth;
	}
	
	@Type(type="yes_no")
	public Boolean getIsHide() {
		return isHide;
	}
	
	public void setIsHide(Boolean isHide) {
		this.isHide = isHide;
	}
	
	@Transient
	public Menu getParent() {
		return parent;
	}
	
	public void setParent(Menu parent) {
		this.parent = parent;
		if (null != this.parent) {
			this.parent.addChild((Menu)this);
		}
	}
	
	public void addChild(Menu child) {
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
	public Set<Menu> getChildren() {
		return children;
	}
	
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
	
	public void setChildren(List<Menu> children) {
		this.children = new LinkedHashSet<Menu>();
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
