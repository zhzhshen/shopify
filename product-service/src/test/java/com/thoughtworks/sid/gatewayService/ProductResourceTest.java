package com.thoughtworks.sid.gatewayService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.gatewayService.domain.Product;
import com.thoughtworks.sid.gatewayService.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.thoughtworks.sid.gatewayService.controller.ProductsController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductResourceTest {

    final String PRODUCT_URL = "/api/products/";
    final Long PRODUCT_ID = 1L;
    final Product VALID_PRODUCT = new Product("product 1", "product 1 description");
    final Product SAVED_PRODUCT = new Product(PRODUCT_ID, "product 1", "product 1 description");

    private MockMvc mvc;

    @InjectMocks
    private ProductsController productsController;

    @Mock
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.standaloneSetup(productsController).build();
    }

    @Test
    public void should_success_to_create_product() throws Exception {
        Product savedProduct = SAVED_PRODUCT;
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(savedProduct);

        RequestBuilder requestBuilder = post(PRODUCT_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(VALID_PRODUCT));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(PRODUCT_URL + PRODUCT_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(savedProduct)))
                .andReturn();
    }

    @Test
    public void should_not_find_inexist_product() throws Exception {
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(null);
        RequestBuilder requestBuilder = get(PRODUCT_URL + PRODUCT_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_find_existing_product() throws Exception {
        when(productRepository.findOne(PRODUCT_ID)).thenReturn(VALID_PRODUCT);
        RequestBuilder requestBuilder = get(PRODUCT_URL + PRODUCT_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_PRODUCT)))
                .andReturn();
    }
}
