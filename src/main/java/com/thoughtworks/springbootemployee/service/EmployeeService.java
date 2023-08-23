package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException();
        }
        employee.setActiveStatus(true);
        return employeeRepository.insert(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findById(id);
        matchedEmployee.setActiveStatus(Boolean.FALSE);
        employeeRepository.update(id, matchedEmployee);
    }

    public void update(Employee employee) {
        if (employee.getActiveStatus().equals(false)) {
            throw new InactiveEmployeeException();
        }
        employeeRepository.update(employee.getId(), employee);
    }

    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public void listByPage(Long pageNumber, Long pageSize) {
        employeeRepository.listByPage(pageNumber, pageSize);
    }
}
