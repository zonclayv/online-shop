package by.haidash.shop.auth.controller;

import by.haidash.shop.security.properties.JwtProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    public void shouldNotGenerateAuthTokenForUnknownUser() throws Exception {
        mockMvc.perform(post("/auth")
                    .param("username", "unknown")
                    .param("password", "unknown"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        ResultActions result = mockMvc.perform(post("/auth")
                    .param("username", "user")
                    .param("password", "user"))
                .andExpect(status().isOk())
                .andExpect(header().exists(jwtProperties.getHeader()));

        String header = result.andReturn()
                .getResponse()
                .getHeader(jwtProperties.getHeader());

        Assert.assertNotNull(header);
        Assert.assertTrue(header.startsWith(jwtProperties.getPrefix()));
    }
}
