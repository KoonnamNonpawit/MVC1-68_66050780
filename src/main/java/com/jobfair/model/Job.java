package com.jobfair.model;

import java.util.Date;

public class Job {
    private String jobId;
    private String jobTitle;
    private String companyName; // เพิ่มเพื่อแสดงชื่อบริษัทใน View
    private String jobType;
    private Date lastApplicationDate;
    private String jobDescription;
    private String status;

    public Job() {

    }

    public Job(String jobId, String jobTitle, String companyName, String jobType, Date lastApplicationDate,
            String jobDescription, String status) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.jobType = jobType;
        this.lastApplicationDate = lastApplicationDate;
        this.jobDescription = jobDescription;
        this.status = status;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Date getLastApplicationDate() {
        return lastApplicationDate;
    }

    public void setLastApplicationDate(Date lastApplicationDate) {
        this.lastApplicationDate = lastApplicationDate;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
