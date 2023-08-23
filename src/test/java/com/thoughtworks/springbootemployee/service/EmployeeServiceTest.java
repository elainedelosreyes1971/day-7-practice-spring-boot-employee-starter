package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setup(){
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }
    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age(){
        //given
        Employee employee = new Employee(1L,null, "Lucy", 20, "Female", 3000);
        Employee savedEmployee = new Employee(1L,1L, "Lucy", 20, "Female", 3000);

        when(mockedEmployeeRepository.insert(employee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employeeResponse.getName());
        assertEquals(20, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(3000, employeeResponse.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18(){
        //given
        Employee employee = new Employee(1L, null, "Lucy", 17, "Female", 3000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });

        //then
        assertEquals("Employee must be 18 ~ 65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_greater_than_65(){
        //given
        Employee oldEmployee = new Employee(1L,null, "Dan", 66, "Male", 3000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(oldEmployee);
        });

        //then
        assertEquals("Employee must be 18 ~ 65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_return_active_status_default_to_true_when_create_given_employee_service_and_newly_created_employee(){
        //given
        Employee newEmployee = new Employee(1L,null, "Keith", 25, "Male", 2000);
        Employee savedEmployee = new Employee(1L, 1L, "Keith", 25, "Male", 2000);
        when(mockedEmployeeRepository.insert(newEmployee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(newEmployee);

        //then
        assertTrue(newEmployee.getActiveStatus());
        assertEquals(savedEmployee.getCompanyId(), employeeResponse.getCompanyId());
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Keith", employeeResponse.getName());
        assertEquals(25, employeeResponse.getAge());
        assertEquals("Male", employeeResponse.getGender());
        assertEquals(2000, employeeResponse.getSalary());
    }
}
