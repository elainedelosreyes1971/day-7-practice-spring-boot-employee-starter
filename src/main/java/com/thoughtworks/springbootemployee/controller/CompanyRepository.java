package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    static {
        companies.add(new Company(100L, "OOCL Philippines"));
        companies.add(new Company(101L, "OOCL Indonesia"));
        companies.add(new Company(102L, "OOCL HongKong"));
    }
    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findByCompanyId(Long id) {
        return companies.stream()
                .filter(employee -> employee.getCompanyId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findEmployeesUnderCompany(Long companyId) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        List<Employee> employees = employeeRepository.listAll();
        return employees.stream()
                .filter(company -> company.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }
}
