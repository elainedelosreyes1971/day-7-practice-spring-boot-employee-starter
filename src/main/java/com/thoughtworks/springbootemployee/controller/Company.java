package com.thoughtworks.springbootemployee.controller;public class Company {
    private final int companyId;
    private final String companyName;

    public Company(int companyId, String companyName) {

        this.companyId = companyId;
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
