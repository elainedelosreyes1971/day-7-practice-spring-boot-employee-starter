package com.thoughtworks.springbootemployee.controller;public class Company {
    private final Long companyId;
    private final String companyName;

    public Company(Long companyId, String companyName) {

        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
