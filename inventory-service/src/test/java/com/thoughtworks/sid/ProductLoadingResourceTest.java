package com.thoughtworks.sid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.ProductInventoryController;
import com.thoughtworks.sid.controller.ProductLoadingController;
import com.thoughtworks.sid.domain.ProductLoading;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.InventoryRepository;
import com.thoughtworks.sid.repository.ProductLoadingRepository;
import com.thoughtworks.sid.repository.StoreRepository;
import com.thoughtworks.sid.service.InventoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductLoadingResourceTest {

    final Long STORE_ID = 1L;
    final Long PRODUCT_ID = 1L;
    final Long PRODUCT_LOADING_ID = 1L;
    final String OWNER = "Sid";
    final Store SAVED_STORE = new Store(STORE_ID, OWNER, "Sid's Store", "This is szz's store");
    final ProductLoading PRODUCT_LOADING = new ProductLoading(STORE_ID, PRODUCT_ID, 100);
    final ProductLoading SAVED_PRODUCT_LOADING = new ProductLoading(PRODUCT_LOADING_ID, STORE_ID, PRODUCT_ID, 100);
    final String PRODUCT_LOADING_URL = "/api/stores/" + STORE_ID + "/products/" + PRODUCT_ID + "/loadings/";

    private MockMvc mvc;

    @InjectMocks
    private ProductLoadingController productLoadingController;

    @InjectMocks
    private ProductInventoryController productInventoryController;

    @Mock
    StoreRepository storeRepository;

    @Mock
    ProductLoadingRepository productLoadingRepository;

    @Mock
    InventoryService inventoryService;

    @Mock
    InventoryRepository inventoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    Principal principal = Mockito.mock(Principal.class);

    @Before
    public void before() {
        when(principal.getName()).thenReturn(OWNER);
        when(storeRepository.getByOwnerAndId(OWNER, STORE_ID)).thenReturn(SAVED_STORE);
        this.mvc = MockMvcBuilders.standaloneSetup(productLoadingController, productInventoryController).build();
    }

    @Test
    public void should_get_401_to_get_inventories() throws Exception {
        RequestBuilder requestBuilder = get(PRODUCT_LOADING_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_get_200_to_get_stores_with_principal() throws Exception {
        when(productLoadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_PRODUCT_LOADING));
        RequestBuilder requestBuilder = get(PRODUCT_LOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void should_success_to_create_product_loading() throws Exception {
        when(productLoadingRepository.save(any(ProductLoading.class))).thenReturn(SAVED_PRODUCT_LOADING);
        when(productLoadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_PRODUCT_LOADING));

        RequestBuilder requestBuilder = post(PRODUCT_LOADING_URL).principal(principal)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(PRODUCT_LOADING));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(PRODUCT_LOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(SAVED_PRODUCT_LOADING))))
                .andReturn();
    }

    @Test
    public void should_successful_product_loading_create_new_inventory() throws Exception {
        should_success_to_create_product_loading();

        verify(inventoryService, times(1)).loading(SAVED_PRODUCT_LOADING);
    }

    @Test
    public void should_fail_to_find_inexist_product_loading() throws Exception {
        RequestBuilder requestBuilder = get(PRODUCT_LOADING_URL + PRODUCT_LOADING_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_success_to_find_existing_product_loading() throws Exception {
        should_success_to_create_product_loading();
        when(productLoadingRepository.getByIdAndStoreIdAndProductId(PRODUCT_LOADING_ID, STORE_ID, PRODUCT_ID)).thenReturn(SAVED_PRODUCT_LOADING);
        RequestBuilder requestBuilder = get(PRODUCT_LOADING_URL + PRODUCT_LOADING_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_PRODUCT_LOADING)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_product_loading_list() throws Exception {
        when(productLoadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_PRODUCT_LOADING));

        RequestBuilder requestBuilder = get(PRODUCT_LOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(SAVED_PRODUCT_LOADING))))
                .andReturn();
    }
}
