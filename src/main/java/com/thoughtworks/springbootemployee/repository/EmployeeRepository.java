package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    public static final int START_ID_MINUS_ONE = 0;

    static {
        employees.add(new Employee(100L, 1L, "Alice", 30, "Female", 5000));
        employees.add(new Employee(101L, 2L, "Bob", 31, "Male", 6000));
        employees.add(new Employee(102L, 3L, "Carl", 32, "Male", 7000));
        employees.add(new Employee(101L, 4L, "David", 33, "Male", 8000));
        employees.add(new Employee(100L, 5L, "Elen", 34, "Female", 9000));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee createEmployee(Employee employee) {
        Long maxId = generateId();
        Employee newEmployee = new Employee(employee.getCompanyId(), ++maxId, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(newEmployee);
        return newEmployee;
    }

    private Long generateId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + 1;
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee update(long id, Employee employee) {
        Employee employeeToUpdate = findById(id);
        employeeToUpdate.merge(employee);
        return employeeToUpdate;
    }

    public void delete(Long id) {
        Employee toRemovedEmployee = findById(id);
        employees.remove(toRemovedEmployee);
    }
}
