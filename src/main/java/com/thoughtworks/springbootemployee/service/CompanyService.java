package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public Company findByCompanyId(Long companyId) {
        return companyRepository.findByCompanyId(companyId);
    }

    public List<Employee> findEmployeesUnderCompany(Long companyId) {
        return companyRepository.findEmployeesUnderCompany(companyId);
    }

    public List<Company> listCompanyByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    public Company createCompany(Company company) {
        return companyRepository.createCompany(company);
    }

    public Company update(long companyId, Company company) {
        return companyRepository.update(companyId, company);
    }

    public void deleteCompany(long companyId) {
        companyRepository.deleteCompany(companyId);
    }
}
