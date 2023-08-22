package com.thoughtworks.springbootemployee.controller;

public class Company {
    private Long companyId;
    private String companyName;

    public Company(Long companyId, String companyName) {

        this.companyId = companyId;
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

}
