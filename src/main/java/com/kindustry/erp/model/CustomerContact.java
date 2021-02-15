package com.kindustry.erp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * CustomerContact entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CUSTOMER_CONTACT")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CustomerContact implements java.io.Serializable {
  private static final long serialVersionUID = -7809181941868071559L;
  private Integer contactId;
  private String customerId;
  private Integer classId;
  private String name;
  private Integer sexId;
  private String className;
  private String sexName;
  private String organization;
  private String duty;
  private String tel;
  private String email;
  private String mobile;
  private String description;
  private String status;
  private Date created;
  private Date lastmod;
  private String creater;
  private String modifyer;
  private String fax;
  private String qq;
  private Date birthday;

  // Constructors

  /** default constructor */
  public CustomerContact() {}

  // Property accessors
  @Id
  @GeneratedValue
  @Column(name = "CONTACT_ID", unique = true, nullable = false)
  public Integer getContactId() {
    return this.contactId;
  }

  public void setContactId(Integer contactId) {
    this.contactId = contactId;
  }

  @Column(name = "CUSTOMER_ID")
  public String getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  @Column(name = "CLASS_ID")
  public Integer getClassId() {
    return this.classId;
  }

  public void setClassId(Integer classId) {
    this.classId = classId;
  }

  @Column(name = "NAME", nullable = false, length = 55)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "SEX_ID")
  public Integer getSexId() {
    return this.sexId;
  }

  public void setSexId(Integer sexId) {
    this.sexId = sexId;
  }

  @Column(name = "CLASS_NAME")
  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  @Column(name = "SEX_NAME", length = 10)
  public String getSexName() {
    return this.sexName;
  }

  public void setSexName(String sexName) {
    this.sexName = sexName;
  }

  @Column(name = "ORGANIZATION")
  public String getOrganization() {
    return this.organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  @Column(name = "DUTY")
  public String getDuty() {
    return this.duty;
  }

  public void setDuty(String duty) {
    this.duty = duty;
  }

  @Column(name = "TEL", length = 55)
  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  @Column(name = "EMAIL")
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "MOBILE", length = 20)
  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Column(name = "DESCRIPTION", length = 2000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(name = "STATUS", length = 1)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "CREATED", length = 10)
  public Date getCreated() {
    return this.created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "LASTMOD", length = 10)
  public Date getLastmod() {
    return this.lastmod;
  }

  public void setLastmod(Date lastmod) {
    this.lastmod = lastmod;
  }

  @Column(name = "CREATER")
  public String getCreater() {
    return this.creater;
  }

  public void setCreater(String creater) {
    this.creater = creater;
  }

  @Column(name = "MODIFYER")
  public String getModifyer() {
    return this.modifyer;
  }

  public void setModifyer(String modifyer) {
    this.modifyer = modifyer;
  }

  @Column(name = "FAX", length = 55)
  public String getFax() {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  @Column(name = "QQ", length = 25)
  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "BIRTHDAY", length = 10)
  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

}