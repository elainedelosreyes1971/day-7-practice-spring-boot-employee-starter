package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
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
                .filter(employee -> employee.getId().equals(id))
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
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company createCompany(Company company) {
        Long maxId = generateNextCompanyId();
        Company newCompany = new Company(maxId, company.getName());
        companies.add(newCompany);
        return newCompany;
    }

    private Long generateNextCompanyId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + 1;
    }

    public void deleteCompany(long companyId) {
        companies.removeIf(company -> company.getId().equals(companyId));
    }

    public Company update(long id, Company company) {
        Company companyToUpdate = findByCompanyId(id);
        companyToUpdate.setName(company.getName());
        return companyToUpdate;
    }

    public void cleanAll() {
        companies.clear();
    }

    public Company insert(Company company) {
        company.setName(company.getName());
        companies.add(company);
        return company;
    }
}
