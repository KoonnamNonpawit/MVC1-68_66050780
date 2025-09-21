package com.jobfair.model;

public class Company {
    private String companyId;
    private String companyName;
    private String contactEmail;
    private String location;
    
    public Company(String companyId, String companyName, String contactEmail, String location) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.location = location;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}