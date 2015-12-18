/**
 * 
 */
package com.fzb.sssp.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 权限类
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年12月9日 上午9:55:11
 */
@Table(name = "SSSP_PERMISSION")
@Entity
@XmlRootElement
public class Permission implements Serializable {
	
	private static final long serialVersionUID = 6872521982969850456L;
	private Long id;
	private Role role;
	private String operateCodes;
	private String menuCodes;
	
	@GeneratedValue
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Role getRole() {
		return role;
	}
	
	@JoinColumn(name = "ROLE_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getOperateCodes() {
		return operateCodes;
	}
	
	public void setOperateCodes(String operateCodes) {
		this.operateCodes = operateCodes;
	}
	
	public String getMenuCodes() {
		return menuCodes;
	}
	
	public void setMenuCodes(String menuCodes) {
		this.menuCodes = menuCodes;
	}
}
