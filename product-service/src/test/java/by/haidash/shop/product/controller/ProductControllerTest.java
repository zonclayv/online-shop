package by.haidash.shop.product.controller;

import by.haidash.shop.product.ProductServiceApp;
import by.haidash.shop.product.entity.Product;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private JwtTokenService jwtTokenService;

    @Before
    public void setUp(){

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .username("mock-user")
                .authorities("USER")
                .id(1L)
                .build();

        when(jwtTokenService.resolveToken(any())).thenReturn(userPrincipal);
    }

    @Test
    public void getAllProducts() throws Exception {
        this.mockMvc.perform(get("/products/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(emptyArray())));
    }

    @Test
    public void getProduct() throws Exception {
        this.mockMvc.perform(get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @WithMockUser
    public void getProductWithWrongId() throws Exception {
        this.mockMvc.perform(get("/products/1515"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void addProduct() throws Exception {

        Product product = new Product();
        product.setName("new product");

        String json = mapper.writeValueAsString(product);

        this.mockMvc.perform(post("/products/")
                            .content(json)
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("name", is(product.getName())));
    }

    @Test
    public void addKeyword() throws Exception {

        this.mockMvc.perform(put("/products/1/keywords/3")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("keywords", hasSize(2)))
                .andExpect(jsonPath("keywords[1].id", is(3)));
    }

    @Test
    public void addExistKeyword() throws Exception {

        this.mockMvc.perform(put("/products/1/keywords/2")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("keywords", hasSize(2)))
                .andExpect(jsonPath("keywords[0].id", is(2)));
    }

    @Test
    public void addKeywordToEmptyList() throws Exception {

        this.mockMvc.perform(put("/products/2/keywords/2")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("keywords", hasSize(1)))
                .andExpect(jsonPath("keywords[0].id", is(2)));
    }

    @Test
    public void addUnknownKeyword() throws Exception {

        this.mockMvc.perform(put("/products/2/keywords/512")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void addCategory() throws Exception {

        this.mockMvc.perform(put("/products/1/categories/1")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("categories", hasSize(2)))
                .andExpect(jsonPath("categories[1].id", is(1)));
    }

    @Test
    public void addExistCategory() throws Exception {

        this.mockMvc.perform(put("/products/1/categories/1")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("categories", hasSize(2)))
                .andExpect(jsonPath("categories[1].id", is(1)));
    }

    @Test
    public void addCategoryToEmptyList() throws Exception {

        this.mockMvc.perform(put("/products/2/categories/1")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("categories", hasSize(1)))
                .andExpect(jsonPath("categories[0].id", is(1)));
    }

    @Test
    public void addUnknownCategory() throws Exception {

        this.mockMvc.perform(put("/products/2/categories/2313")
                            .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void findByKeyword() throws Exception {

        this.mockMvc.perform(get("/products/keywords/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].keywords", hasSize(1)))
                .andExpect(jsonPath("$[0].keywords[0].id", is(2)));
    }

    @Test
    public void findByCategory() throws Exception {

        this.mockMvc.perform(get("/products/categories/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].categories", hasSize(2)))
                .andExpect(jsonPath("$[0].categories[1].id", is(1)));
    }
}
