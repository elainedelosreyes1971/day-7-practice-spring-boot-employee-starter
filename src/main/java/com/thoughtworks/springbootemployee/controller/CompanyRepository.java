package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    public static final int START_ID_MINUS_ONE = 0;

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

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        int index = 1;
        List<Company> filteredCompanyList = new ArrayList<>();
        for(Company company : companies){
            if(index >= pageNumber && index <= pageSize){
                filteredCompanyList.add(company);
            }
            index++;
        }
        return filteredCompanyList;
    }

    public Company createCompany(Company company) {
        Long maxId = generateNextCompanyId();
        Company newCompany = new Company(maxId, company.getCompanyName());
        companies.add(newCompany);
        return newCompany;
    }

    private Long generateNextCompanyId() {
        return companies.stream()
                .mapToLong(Company::getCompanyId)
                .max()
                .orElse(START_ID_MINUS_ONE) + 1;
    }

    public void deleteCompany(long companyId) {
        companies.removeIf(company -> company.getCompanyId().equals(companyId));
    }
}
