package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/companies")
@RestController
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping()
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findByCompanyId(@PathVariable Long companyId){
        return companyRepository.findByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> findEmployeesUnderCompany(@PathVariable Long companyId){
        return companyRepository.findEmployeesUnderCompany(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompanyByPage(@RequestParam Long pageNumber, Long pageSize){
        return companyRepository.listCompanyByPage(pageNumber, pageSize);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody Company company){
        return companyRepository.createCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable long companyId, @RequestBody Company newCompany){
        Company company = companyRepository.findByCompanyId(companyId);
        company.setCompanyName(newCompany.getCompanyName());
        return company;
    }
}
