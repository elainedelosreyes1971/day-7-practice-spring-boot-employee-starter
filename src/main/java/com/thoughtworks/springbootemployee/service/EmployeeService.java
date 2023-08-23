package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public Employee update(Long id, Employee employee) {
        Boolean activeStatus = Optional.ofNullable(employee.getActiveStatus()).orElse(false);
        if (Boolean.FALSE.equals(activeStatus)) {
            throw new InactiveEmployeeException();
        }
        return employeeRepository.update(id, employee);
    }

    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }
}
