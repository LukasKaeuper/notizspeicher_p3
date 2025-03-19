package de.thowl.prog3.exam.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRegisterFormController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "tester")
                        .param("email", "test@example.com")
                        .param("password", "testpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("message", "Benutzer wurde erfolgreich registriert!"));
    }

    @Test
    public void testRegisterUser_Failure() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "test")
                        .param("email", "testexample.com")
                        .param("password", "testPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("errorMessage", "Fehler bei der Eingabe!, mindestens 5 Zeichen"));
    }
}
