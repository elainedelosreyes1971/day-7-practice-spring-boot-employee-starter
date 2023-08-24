package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/companies")
@RestController
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping()
    public List<Company> listAllCompanies() {
        return companyService.listAllCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findByCompanyId(@PathVariable Long companyId) {
        return companyService.findByCompanyId(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> findEmployeesUnderCompany(@PathVariable Long companyId) {
        return companyService.findEmployeesUnderCompany(companyId);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompanyByPage(@RequestParam Long pageNumber, Long pageSize) {
        return companyService.listCompanyByPage(pageNumber, pageSize);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable long companyId, @RequestBody Company newCompany) {
        return companyService.update(companyId, newCompany);
    }

    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable long companyId) {
        companyService.deleteCompany(companyId);
    }
}
