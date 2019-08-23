package by.haidash.shop.auth.controller;

import by.haidash.shop.auth.controller.messaging.UserCheckMessage;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import by.haidash.shop.security.properties.JwtProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import springfox.documentation.builders.PathSelectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MessagingService messagingService;

    @Test
    public void shouldNotGenerateAuthTokenForUnknownUser() throws Exception {

        UserCheckMessage request = new UserCheckMessage();
        request.setEmail("unknown");

        UserCheckMessage response = new UserCheckMessage();
        response.setEmail("unknown");
        response.setId(1L);
        response.setPsw(passwordEncoder.encode("unknown"));

        when(messagingService.getProperties(any())).thenReturn(new MessagingPropertiesEntry());
        when(messagingService.sendWithResponse(eq(request), eq(UserCheckMessage.class), any(), any()))
                .thenReturn(java.util.Optional.of(response));

        mockMvc.perform(post("/auth")
                    .param("username", "unknown")
                    .param("password", "unknown"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {

        UserCheckMessage response = new UserCheckMessage();
        response.setEmail("user");
        response.setId(1L);
        response.setPsw(passwordEncoder.encode("user"));

        when(messagingService.getProperties(any())).thenReturn(new MessagingPropertiesEntry());
        when(messagingService.sendWithResponse(any(), eq(UserCheckMessage.class), any(), any()))
                .thenReturn(java.util.Optional.of(response));

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
