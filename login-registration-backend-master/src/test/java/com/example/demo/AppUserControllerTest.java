package com.example.demo;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRepository;
import com.example.demo.appuser.AppUserRole;
import com.example.demo.util.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        AppUser user = new AppUser();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(jwtTokenProvider.createToken(Mockito.anyString(), Mockito.any(AppUserRole.class))).thenReturn("testToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .param("username", "testuser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginFailure() throws Exception {
        Mockito.when(userRepository.findByUsername("wronguser")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .param("username", "wronguser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        Mockito.when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .param("username", "newuser")
                .param("password", "password")
                .param("role", "USER")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterConflict() throws Exception {
        AppUser user = new AppUser();
        user.setUsername("existinguser");
        Mockito.when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .param("username", "existinguser")
                .param("password", "password")
                .param("role", "USER")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
