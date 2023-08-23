package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
public class CompanyApiTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupCompanyData() {
        companyRepository.cleanAll();
    }

    @Test
    void should_return_all_given_companies_when_perform_get_companies() throws Exception {
        //given
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));
        companyRepository.insert(new Company(2L, "OOCL HongKong"));
        companyRepository.insert(new Company(3L, "OOCL Indonesia"));

        //when //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(newCompany.getId()))
                .andExpect(jsonPath("$[0].name").value(newCompany.getName()));
    }
    @Test
    void should_return_the_company_when_perform_get_company_given_company_id() throws Exception {
        //given
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));

        //when ,  //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + newCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newCompany.getId()))
                .andExpect(jsonPath("$.name").value(newCompany.getName()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_company_given_a_not_existing_id() throws Exception {
        //given
        long notExistingCompanyId = 99L;

        //when , then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistingCompanyId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_company_created_when_perform_post_companies_given_a_new_company_with_JSON_format() throws Exception {
        //given
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newCompany.getName()));
    }

    @Test
    void should_delete_an_company_when_perform_delete_given_company_id() throws Exception {
        //given
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + newCompany.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_list_of_companies_when_perform_get_given_pageNumber_and_pageSize() throws Exception {
        //given
        companyRepository.insert(new Company(2L, "OOCL HongKong"));
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));
        companyRepository.insert(new Company(3L, "OOCL Indonesia"));

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/")
                        .param("pageSize", String.valueOf(1))
                        .param("pageNumber", String.valueOf(2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(newCompany.getId()))
                .andExpect(jsonPath("$[0].name").value(newCompany.getName()));
    }

    @Test
    void should_return_exception_when_perform_delete_given_company_id_is_not_existing() throws Exception {
        //given
        companyRepository.insert(new Company(2L, "OOCL HongKong"));
        long notExistingId = 90L;

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + notExistingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_update_company_name_when_put_given_a_new_company_name() throws Exception {
        //given
        Company newCompany = companyRepository.insert(new Company(1L, "OOCL Philippines"));
        Company companyToUpdate = new Company(1L, "OOCL Indonesia");

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/companies/" + newCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(companyToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newCompany.getName()));
    }

    @Test
    void should_filter_employees_in_a_company_when_get_given_company_id_and_employee_id() throws Exception {
        //given
        long existingCompanyId = 100L;

        //when , //then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + existingCompanyId + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(notNullValue()))
                .andExpect(jsonPath("$[0].companyId").value(notNullValue()));
    }
}
