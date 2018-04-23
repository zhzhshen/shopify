package com.thoughtworks.sid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.ProductUnloadingController;
import com.thoughtworks.sid.domain.ProductUnloading;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.ProductUnloadingRepository;
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

import java.security.Principal;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductUnloadingResourceTest {

    private final Long ORDER_ITEM_ID = 1L;
    private final Long STORE_ID = 1L;
    private final Long PRODUCT_ID = 1L;
    private final Long PRODUCT_UNLOADING_ID = 1L;
    private final String OWNER = "Sid";
    private final Store SAVED_STORE = new Store(STORE_ID, OWNER, "Sid's Store", "This is szz's store");
    private final ProductUnloading PRODUCT_UNLOADING = new ProductUnloading(ORDER_ITEM_ID, 100);
    private final ProductUnloading SAVED_PRODUCT_UNLOADING = new ProductUnloading(PRODUCT_UNLOADING_ID, ORDER_ITEM_ID, 100);
    private final String PRODUCT_UNLOADING_URL = "/api/stores/" + STORE_ID + "/products/" + PRODUCT_ID + "/unloadings/";

    private MockMvc mvc;

    @InjectMocks
    private ProductUnloadingController productUnloadingController;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductUnloadingRepository productUnloadingRepository;

    @Mock
    private InventoryService inventoryService;

    @Autowired
    ObjectMapper objectMapper;

    private Principal principal = Mockito.mock(Principal.class);

    @Before
    public void before() {
        when(principal.getName()).thenReturn(OWNER);
        when(storeRepository.getByOwnerAndId(OWNER, STORE_ID)).thenReturn(SAVED_STORE);
        this.mvc = MockMvcBuilders.standaloneSetup(productUnloadingController).build();
    }

    @Test
    public void should_get_401_to_get_inventories() throws Exception {
        RequestBuilder requestBuilder = get(PRODUCT_UNLOADING_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_get_200_to_get_stores_with_principal() throws Exception {
        when(productUnloadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Collections.singletonList(SAVED_PRODUCT_UNLOADING));
        RequestBuilder requestBuilder = get(PRODUCT_UNLOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void should_success_to_create_product_loading() throws Exception {
        when(productUnloadingRepository.save(any(ProductUnloading.class))).thenReturn(SAVED_PRODUCT_UNLOADING);
        when(productUnloadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Collections.singletonList(SAVED_PRODUCT_UNLOADING));

        RequestBuilder requestBuilder = post(PRODUCT_UNLOADING_URL).principal(principal)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(PRODUCT_UNLOADING));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(PRODUCT_UNLOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(SAVED_PRODUCT_UNLOADING))))
                .andReturn();
    }

    @Test
    public void should_successful_product_loading_create_new_inventory() throws Exception {
        should_success_to_create_product_loading();

        verify(inventoryService, times(1)).unloading(SAVED_PRODUCT_UNLOADING);
    }

    @Test
    public void should_fail_to_find_inexist_product_loading() throws Exception {
        RequestBuilder requestBuilder = get(PRODUCT_UNLOADING_URL + PRODUCT_UNLOADING_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_success_to_find_existing_product_loading() throws Exception {
        should_success_to_create_product_loading();
        when(productUnloadingRepository.getByIdAndStoreIdAndProductId(PRODUCT_UNLOADING_ID, STORE_ID, PRODUCT_ID)).thenReturn(SAVED_PRODUCT_UNLOADING);
        RequestBuilder requestBuilder = get(PRODUCT_UNLOADING_URL + PRODUCT_UNLOADING_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_PRODUCT_UNLOADING)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_product_loading_list() throws Exception {
        when(productUnloadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Collections.singletonList(SAVED_PRODUCT_UNLOADING));

        RequestBuilder requestBuilder = get(PRODUCT_UNLOADING_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(SAVED_PRODUCT_UNLOADING))))
                .andReturn();
    }

    @Test
    public void should_success_confirm_uploading() throws Exception {
        when(productUnloadingRepository.getByIdAndStoreIdAndProductId(PRODUCT_UNLOADING_ID, STORE_ID, PRODUCT_ID)).thenReturn(SAVED_PRODUCT_UNLOADING);
        when(productUnloadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Collections.singletonList(SAVED_PRODUCT_UNLOADING));
        when(productUnloadingRepository.save(any(ProductUnloading.class))).thenReturn(SAVED_PRODUCT_UNLOADING);

        RequestBuilder requestBuilder = post(PRODUCT_UNLOADING_URL + PRODUCT_UNLOADING_ID + "/confirmation").principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void should_success_cancel_uploading() throws Exception {
        when(productUnloadingRepository.getByIdAndStoreIdAndProductId(PRODUCT_UNLOADING_ID, STORE_ID, PRODUCT_ID)).thenReturn(SAVED_PRODUCT_UNLOADING);
        when(productUnloadingRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Collections.singletonList(SAVED_PRODUCT_UNLOADING));
        when(productUnloadingRepository.save(any(ProductUnloading.class))).thenReturn(SAVED_PRODUCT_UNLOADING);

        RequestBuilder requestBuilder = post(PRODUCT_UNLOADING_URL + PRODUCT_UNLOADING_ID + "/cancellation").principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        verify(inventoryService, times(1)).cancelUnloading(SAVED_PRODUCT_UNLOADING);
    }
}
