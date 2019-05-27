package org.qvit.hybrid.sys.dto;

import java.util.Date;

import org.qvit.hybrid.enums.InvalidStatus;

public class AdminUserDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = -1322979079033818657L;

    private String userName;
    private String email;
    private String mobile;
    private String department;
    private String occupation;
    private String jobNumber;
    private Date entryDate;
    private String password;
    private InvalidStatus status;

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

    public InvalidStatus getStatus() {
        return status;
    }

    public void setStatus(InvalidStatus status) {
        this.status = status;
    }

}
