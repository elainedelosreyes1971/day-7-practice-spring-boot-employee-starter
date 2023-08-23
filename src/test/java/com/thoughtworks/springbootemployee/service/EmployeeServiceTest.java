package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setup() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //given
        Employee employee = new Employee(1L, null, "Lucy", 20, "Female", 3000);
        Employee savedEmployee = new Employee(1L, 1L, "Lucy", 20, "Female", 3000);

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
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
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
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_greater_than_65() {
        //given
        Employee oldEmployee = new Employee(1L, null, "Dan", 66, "Male", 3000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(oldEmployee);
        });

        //then
        assertEquals("Employee must be 18 ~ 65 years old.", employeeCreateException.getMessage());
    }

    @Test
    void should_return_active_status_default_to_true_when_create_given_employee_service_and_newly_created_employee() {
        //given
        Employee newEmployee = new Employee(1L, null, "Keith", 25, "Male", 2000);
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

    @Test
    void should_set_active_status_to_false_when_delete_given_employee_service_and_deleted_employee() {
        //given
        Employee employee = new Employee(1L, 1L, "Lucy", 30, "Female", 3000);
        employee.setActiveStatus(Boolean.TRUE);
        when(mockedEmployeeRepository.findById(employee.getId())).thenReturn(employee);

        //when
        employeeService.delete(employee.getId());

        //then
        verify(mockedEmployeeRepository).update(eq(employee.getId()), argThat(tempEmployee -> {
            assertFalse(tempEmployee.getActiveStatus());
            assertEquals(1L, tempEmployee.getCompanyId());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(30, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(3000, tempEmployee.getSalary());
            return true;
        }));
    }

    @Test
    void should_return_exception_when_updating_employee_data_given_employee_service_and_employee() {
        //given
        Employee newEmployee = new Employee(1L, 2L, "Jennifer", 45, "Female", 3000);
        Employee employeeToUpdate = new Employee(1L, 2L, "Jennifer", 47, "Female", 4000);

        when(mockedEmployeeRepository.findById(newEmployee.getId())).thenReturn(employeeToUpdate);
        //when
        employeeService.create(newEmployee);
        employeeService.delete(employeeToUpdate.getId());
        InactiveEmployeeException inactiveEmployeeException = assertThrows(InactiveEmployeeException.class, () -> {
            employeeService.update(employeeToUpdate);
        });

        //then
        assertEquals("Employee is inactive.", inactiveEmployeeException.getMessage());
    }

    @Test
    void should_return_updated_employee_data_when_update_given_employee_service_and_employee() {
        //given
        Employee newEmployee = new Employee(1L, 2L, "Jennifer", 45, "Female", 3000);
        Employee employeeToUpdate = new Employee(1L, 2L, "Jennifer", 47, "Female", 4000);

        when(mockedEmployeeRepository.update(newEmployee.getId(), newEmployee)).thenReturn(employeeToUpdate);

        //when
        employeeToUpdate.setActiveStatus(true);
        employeeService.update(employeeToUpdate);

        //then
        verify(mockedEmployeeRepository).update(eq(employeeToUpdate.getId()), argThat(tempEmployee -> {
            assertTrue(tempEmployee.getActiveStatus());
            assertEquals(1L, tempEmployee.getCompanyId());
            assertEquals(2L, tempEmployee.getId());
            assertEquals("Jennifer", tempEmployee.getName());
            assertEquals(47, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(4000, tempEmployee.getSalary());
            return true;
        }));
    }

    @Test
    void should_return_all_employees_when_perform_find_all_given_list_of_employees() {
        //given
        List<Employee> employees = Arrays.asList(new Employee(100L, 1L, "Alice", 30, "Female", 5000),
                new Employee(101L, 2L, "John", 30, "Male", 5000));

        when(mockedEmployeeRepository.listAll()).thenReturn(employees);

        //when
        List<Employee> newEmployees = employeeService.listAll();

        //then
        assertEquals(2, newEmployees.size());
    }
}
