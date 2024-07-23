package com.apress.users;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("spyBean")
public class UserSpyBeanTests {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() throws Exception {

        List<User> mockUsers = Arrays.asList(
                UserBuilder.createUser()
                        .withName("Ximena")
                        .withEmail("ximena@email.com")
                        .build(),
                UserBuilder.createUser()
                        .withName("Norma")
                        .withEmail("norma@email.com")
                        .build()
        );
        doReturn(mockUsers).when(userRepository).findAll();


        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ximena"))
                .andExpect(jsonPath("$[1].name").value("Norma"));


        verify(userRepository).findAll();
    }
}
