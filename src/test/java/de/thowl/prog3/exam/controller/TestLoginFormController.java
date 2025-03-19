package de.thowl.prog3.exam.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestLoginFormController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProcessLoginForm_Success() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "thowl")
                        .param("password", "start"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    public void testProcessLoginForm_Failure() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "testUser")
                        .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }
}
