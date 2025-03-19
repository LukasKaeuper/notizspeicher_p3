package de.thowl.prog3.exam.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class TestDashboardFormController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowDashboard() throws Exception {
        HttpSession session = mockMvc.perform(post("/login")
                        .param("username", "thowl")
                        .param("password", "start"))
                .andReturn()
                .getRequest()
                .getSession();

        // Teste den Zugriff auf das Dashboard mit angemeldetem Benutzer
        mockMvc.perform(get("/dashboard").session((MockHttpSession) session))
                .andExpect(status().isOk()) // Erwarte HTTP 200 (OK)
                .andExpect(view().name("/dashboard"));
    }
}
