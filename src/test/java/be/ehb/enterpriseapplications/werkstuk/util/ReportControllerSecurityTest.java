package be.ehb.enterpriseapplications.werkstuk.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerSecurityTest {

    private final String BASE_URL = "/api/v1/reports";

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessRevenue_thenForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL + "/revenue")
                        .param("startDate", "01-01-2025")
                        .param("endDate", "31-01-2025"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessRevenue_thenOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/revenue")
                        .param("startDate", "01-01-2025")
                        .param("endDate", "31-01-2025"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessExcelExport_thenForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL + "/revenue/excel")
                        .param("startDate", "01-01-2025")
                        .param("endDate", "31-01-2025"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessExcelExport_thenOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/revenue/excel")
                        .param("startDate", "01-01-2025")
                        .param("endDate", "31-01-2025"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessTopBidders_thenForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL + "/top-bidders")
                        .param("limit", "5"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessTopBidders_thenOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/top-bidders")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "USER")
    void whenUserAccessCategoryPerformance_thenForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL + "/category-performance"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminAccessCategoryPerformance_thenOk() throws Exception {
        mockMvc.perform(get(BASE_URL + "/category-performance"))
                .andExpect(status().isOk());
    }
}

