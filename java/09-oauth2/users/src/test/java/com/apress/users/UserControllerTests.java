package com.apress.users;


import com.apress.users.config.UserConfig;
import com.apress.users.model.User;
import com.apress.users.model.UserRole;
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
@WithMockUser(authorities = "SCOPE_users.read")
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


    @Test
    void getAllUsersTest() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                User.builder()
                        .email("dummy1@email.com")
                        .name("Dummy1")
                        .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                        .password("aw2s0meR!")
                        .role(UserRole.USER)
                        .active(true)
                        .build(),
                User.builder()
                        .email("dummy2@email.com")
                        .name("Dummy2")
                        .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                        .password("aw2s0meR!")
                        .role(UserRole.USER)
                        .active(true)
                        .build()
        ));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    void newUserTest() throws Exception {
        User user = User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(toJson(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("dummy@email.com"));
    }


    @Test
    void findUserByEmailTest() throws Exception {
        User user = User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();

        when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{email}",user.getEmail())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("dummy@email.com"));
    }

    @Test
    void deleteUserByEmailTest() throws Exception{
        User user = User.builder()
                .email("dummy@email.com")
                .name("Dummy")
                .gravatarUrl("https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar")
                .password("aw2s0meR!")
                .role(UserRole.USER)
                .active(true)
                .build();

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
