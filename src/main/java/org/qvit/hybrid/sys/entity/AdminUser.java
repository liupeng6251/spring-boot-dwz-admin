package org.qvit.hybrid.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_admin_user")
public class AdminUser extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1322979079033818657L;

    @Column(name = "user_name")
    private String userName;
    private String email;
    private String mobile;
    private String department;
    private String occupation;
    @Column(name = "Job_Number")
    private String jobNumber;
    @Column(name = "entry_Date")
    private Date entryDate;
    private String password;
    private Integer status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }



    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
