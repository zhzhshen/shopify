package com.thoughtworks.sid;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.ProductPricesController;
import com.thoughtworks.sid.domain.ProductPrice;
import com.thoughtworks.sid.repository.ProductPriceRepository;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPriceResourceTest {

    final String PRODUCT_PRICE_URL = "/api/product-prices/";
    final Long PRODUCT_ID = 1L;
    final Long PRICE_ID = 1L;
    final ProductPrice VALID_PRODUCT_PRICE = new ProductPrice(PRODUCT_ID, 100d, new Date(), new Date());
    final ProductPrice SAVED_PRODUCT_PRICE = new ProductPrice(PRICE_ID, PRODUCT_ID, 100d, new Date(), new Date());

    private MockMvc mvc;

    @InjectMocks
    private ProductPricesController productPricesController;

    @Mock
    ProductPriceRepository productPriceRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.standaloneSetup(productPricesController).build();
    }

    @Test
    public void should_success_to_create_product() throws Exception {
        when(productPriceRepository.save(any(ProductPrice.class))).thenReturn(SAVED_PRODUCT_PRICE);
        when(productPriceRepository.findOne(PRICE_ID)).thenReturn(SAVED_PRODUCT_PRICE);

        RequestBuilder requestBuilder = post(PRODUCT_PRICE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(VALID_PRODUCT_PRICE));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(PRODUCT_PRICE_URL + PRICE_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_PRODUCT_PRICE)))
                .andReturn();
    }

    @Test
    public void should_fail_to_find_inexist_product() throws Exception {
        when(productPriceRepository.findOne(PRICE_ID)).thenReturn(null);
        RequestBuilder requestBuilder = get(PRODUCT_PRICE_URL + PRICE_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_success_to_find_existing_product() throws Exception {
        when(productPriceRepository.findOne(PRICE_ID)).thenReturn(VALID_PRODUCT_PRICE);
        RequestBuilder requestBuilder = get(PRODUCT_PRICE_URL + PRICE_ID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(VALID_PRODUCT_PRICE)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_product_prices_list() throws Exception {
        List<ProductPrice> products = Arrays.asList(SAVED_PRODUCT_PRICE);
        when(productPriceRepository.findAll()).thenReturn(products);
        RequestBuilder requestBuilder = get(PRODUCT_PRICE_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }

    @Test
    public void should_success_to_get_price_list_of_a_product() throws Exception {
        List<ProductPrice> products = Arrays.asList(SAVED_PRODUCT_PRICE);
        when(productPriceRepository.findProductPricesByProductId(PRODUCT_ID)).thenReturn(products);
        RequestBuilder requestBuilder = get(PRODUCT_PRICE_URL).param("productId", PRODUCT_ID.toString());
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }
}
