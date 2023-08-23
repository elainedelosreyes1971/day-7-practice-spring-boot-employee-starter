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
}
