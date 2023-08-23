package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
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
}
