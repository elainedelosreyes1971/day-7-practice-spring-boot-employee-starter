package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping()
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.insert(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        return employeeRepository.update(id, employee);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.delete(id);
    }
}
