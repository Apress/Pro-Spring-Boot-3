package com.apress.users;


import com.apress.users.config.UserConfig;
import com.apress.users.model.User;
import com.apress.users.model.UserBuilder;
import com.apress.users.repository.UserRepository;
import com.apress.users.security.UserSecurityConfig;
import com.apress.users.web.UsersController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({UserSecurityConfig.class, UserConfig.class})
@WebMvcTest(controllers = { UsersController.class })
public class UserControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserRepository userRepository;


    @BeforeEach
    void SetUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }


    @WithMockUser
    @Test
    void getAllUsersTest() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                UserBuilder.createUser().build(),
                UserBuilder.createUser().build()
        ));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @WithMockUser
    @Test
    void newUserTest() throws Exception {
        User user = UserBuilder.createUser().build();
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(toJson(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("dummy@email.com"));
    }

    @WithMockUser
    @Test
    void findUserByEmailTest() throws Exception {
        User user = UserBuilder.createUser().build();
        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{email}",user.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("dummy@email.com"));
    }

    @WithMockUser
    @Test
    void deleteUserByEmailTest() throws Exception{
        User user = UserBuilder.createUser().build();
        doNothing().when(userRepository).deleteById(user.getEmail());

        mockMvc.perform(delete("/users/{email}",user.getEmail()))
                .andExpect(status().isNoContent());

    }

    private static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
