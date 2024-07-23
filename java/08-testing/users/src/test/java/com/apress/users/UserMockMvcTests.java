package com.apress.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mockMvc")
public class UserMockMvcTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUserTests() throws Exception {
        String location = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("""
                        {
                            "email": "dummy@email.com",
                            "name": "Dummy",
                            "password": "aw2s0meR!",
                            "gravatarUrl": "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                            "userRole": ["USER"],
                            "active": true
                        }
                       """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void getAllUsersTests() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Dummy"))
                .andExpect(jsonPath("$..active").value(hasItem(true)))
                .andExpect(jsonPath("$[*]").value(hasSize(1)));
    }



}
