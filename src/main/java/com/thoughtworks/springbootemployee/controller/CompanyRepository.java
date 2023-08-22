package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    static {
        companies.add(new Company(100, "OOCL Philippines"));
        companies.add(new Company(100, "OOCL Indonesia"));
        companies.add(new Company(100, "OOCL HongKong"));
    }
    public List<Company> listAllCompanies() {
        return companies;
    }
}
