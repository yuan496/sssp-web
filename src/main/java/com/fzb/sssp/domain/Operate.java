/**
 * 
 */
package com.fzb.sssp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

/**
 * 操作类
 * 
 * @author zhuyuyin
 */
@Entity
public class Operate implements Serializable {

	private static final long serialVersionUID = -6886711865928684439L;

	private Long id;
	private String code;
	private String url;
	private String name;
	private String cssStyle;
	private Menu menu;
	private Date createTime;
	private Date updateTime;
	private Date deleteTime;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
}
