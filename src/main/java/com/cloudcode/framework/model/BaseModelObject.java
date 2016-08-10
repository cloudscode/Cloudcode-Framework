package com.cloudcode.framework.model;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseModelObject implements ModelObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6949902750903507646L;
	@Basic
	@Id
	private String id;
	@Basic
	private String createAuthor;
	@Basic
	private String updateAuthor; 
	@Basic  
	private String department;
	@Basic
	private Date createDateTime; 
	@Basic 
	private Date updateDateTime;
	@Basic
	private Integer isDelete;
	
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
	@Column(name = "id", unique = true)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(length=50)
	public String getCreateAuthor() {
		return createAuthor;
	}
	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	@Column(length=50)
	public String getUpdateAuthor() {
		return updateAuthor;
	}
	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	@Column(length=50)
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Temporal(TemporalType.DATE)
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	@Temporal(TemporalType.DATE)
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	@Column(length=1)
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
}
