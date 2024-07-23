package com.apress.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mockBean")
public class UserMockBeanTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    void saveUsers() throws Exception {
        var user = UserBuilder.createUser()
                .withName("Dummy")
                .withEmail("dummy@email.com")
                .active()
                .withRoles(UserRole.USER)
                .withPassword("aw3s0m3R!")
                .build();

        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
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
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.userRole").isArray())
                .andExpect(jsonPath("$.userRole[0]").value("USER"));

        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }
}
