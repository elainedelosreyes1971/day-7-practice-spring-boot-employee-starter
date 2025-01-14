package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeApiTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_all_given_employees_when_perform_get_employees() throws Exception {
        //given
        Employee alice = employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        employeeRepository.insert(new Employee("Jennifer", 25, "Female", 8000));
        employeeRepository.insert(new Employee("Wee", 25, "Female", 10000));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
    }

    @Test
    void should_return_the_employee_when_perform_get_employee_given_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        employeeRepository.insert(new Employee("Bob", 28, "Male", 8000));

        //when ,  //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_employee_given_a_not_existing_id() throws Exception {
        //given
        long notExistingEmployeeId = 99L;

        //when , then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistingEmployeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_employees_by_given_gender_when_perform_get_employees() throws Exception {
        //given
        Employee alice = employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        employeeRepository.insert(new Employee("Bob", 28, "Male", 8000));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/").param("gender", "Female"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()));
    }

    @Test
    void should_update_an_employee_salary_and_age_when_perform_put_given_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        Employee newEmployeeData = new Employee(alice.getName(), 25, alice.getGender(), 10000);

        newEmployeeData.setActiveStatus(true);
        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/" + alice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployeeData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(newEmployeeData.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployeeData.getSalary()));
    }

    @Test
    void should_return_employee_created_when_perform_post_employees_given_a_new_employee_with_JSON_format() throws Exception {
        //given
        Employee newEmployee = new Employee("Alice", 24, "Female", 9000);

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
    }

    @Test
    void should_delete_an_employee_when_perform_delete_given_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + alice.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_list_of_employees_when_perform_get_given_pageNumber_and_pageSize() throws Exception {
        //given
        employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        Employee jane = employeeRepository.insert(new Employee("Jane", 25, "Female", 9000));
        employeeRepository.insert(new Employee("David", 26, "Male", 9000));
        employeeRepository.insert(new Employee("Emily", 27, "Female", 9000));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/")
                        .param("pageSize", String.valueOf(1))
                        .param("pageNumber", String.valueOf(2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(jane.getId()))
                .andExpect(jsonPath("$[0].name").value(jane.getName()))
                .andExpect(jsonPath("$[0].age").value(jane.getAge()))
                .andExpect(jsonPath("$[0].gender").value(jane.getGender()))
                .andExpect(jsonPath("$[0].salary").value(jane.getSalary()));
    }

    @Test
    void should_return_exception_when_perform_delete_given_employee_id_is_not_existing() throws Exception {
        //given
        employeeRepository.insert(new Employee("Alice", 24, "Female", 9000));
        long notExistingId = 90L;

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + notExistingId))
                .andExpect(status().isNotFound());
    }
}
