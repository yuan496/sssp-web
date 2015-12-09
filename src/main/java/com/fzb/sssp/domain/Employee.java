package com.fzb.sssp.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:06:46
 */
@Table(name = "SSSP_EMPLOYEE")
@Entity
@XmlRootElement
public class Employee implements Serializable {
	
	/** 
    * TODO(用一句话描述这个变量表示什么) 
    */ 
    private static final long serialVersionUID = 3146358340155292516L;
	private Long id;
	private String lastName;
	private String email;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birth;
	private Date createTime;
	private Department department;
	
	@GeneratedValue
	@Id
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JoinColumn(name = "DEPARTMENT_ID")
	@ManyToOne(fetch = FetchType.EAGER)
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	/**
	 * {简述，保留点号}.
	 * <p>
	 * {详述}
	 * <p>
	 * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
	 * @author fangzhibin 2015年10月6日 下午12:08:34
	 * @return
	 * @modify {上次修改原因} by fangzhibin 2015年10月6日 下午12:08:34
	 */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", birth=" + birth + ", createTime=" + createTime + ", department=" + department + "]";
	}
}
