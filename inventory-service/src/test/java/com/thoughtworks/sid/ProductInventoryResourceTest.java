package com.thoughtworks.sid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.ProductInventoryController;
import com.thoughtworks.sid.domain.Inventory;
import com.thoughtworks.sid.domain.Store;
import com.thoughtworks.sid.repository.InventoryRepository;
import com.thoughtworks.sid.repository.StoreRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInventoryResourceTest {

    final Long STORE_ID = 1L;
    final Long PRODUCT_ID = 1L;
    final Long INVENTORY_ID = 1L;
    final String OWNER = "Sid";
    final Store SAVED_STORE = new Store(STORE_ID, OWNER, "Sid's Store", "This is szz's store");
    final Inventory INVENTORY = new Inventory(STORE_ID, PRODUCT_ID, 10);
    final Inventory SAVED_INVENTORY = new Inventory(INVENTORY_ID, STORE_ID, PRODUCT_ID, 10);
    final String INVENTORY_URL = "/api/stores/" + STORE_ID + "/products/" + PRODUCT_ID + "/inventories/";

    private MockMvc mvc;

    @InjectMocks
    private ProductInventoryController inventoryController;

    @Mock
    StoreRepository storeRepository;

    @Mock
    InventoryRepository inventoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    Principal principal = Mockito.mock(Principal.class);

    @Before
    public void before() {
        when(principal.getName()).thenReturn(OWNER);
        when(storeRepository.getByOwnerAndId(OWNER, STORE_ID)).thenReturn(SAVED_STORE);
        this.mvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    public void should_get_401_to_get_inventories() throws Exception {
        RequestBuilder requestBuilder = get(INVENTORY_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_get_200_to_get_stores_with_principal() throws Exception {
        when(inventoryRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_INVENTORY));
        RequestBuilder requestBuilder = get(INVENTORY_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void should_success_to_create_inventory() throws Exception {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(SAVED_INVENTORY);
        when(inventoryRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_INVENTORY));

        RequestBuilder requestBuilder = post(INVENTORY_URL).principal(principal)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(INVENTORY));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(INVENTORY_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(SAVED_INVENTORY))))
                .andReturn();
    }

    @Test
    public void should_fail_to_find_inexist_inventory() throws Exception {
        RequestBuilder requestBuilder = get(INVENTORY_URL + INVENTORY_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_success_to_find_existing_inventory() throws Exception {
        should_success_to_create_inventory();
        when(inventoryRepository.getByIdAndStoreIdAndProductId(INVENTORY_ID, STORE_ID, PRODUCT_ID)).thenReturn(SAVED_INVENTORY);
        RequestBuilder requestBuilder = get(INVENTORY_URL + INVENTORY_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_INVENTORY)))
                .andReturn();
    }

    @Test
    public void should_success_to_find_latest_inventory() throws Exception {
        when(inventoryRepository.getByStoreIdAndProductIdOrderByIdDesc(STORE_ID, PRODUCT_ID)).thenReturn(SAVED_INVENTORY);
        RequestBuilder requestBuilder = get(INVENTORY_URL + "latest").principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_INVENTORY)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_inventory_list() throws Exception {
        when(inventoryRepository.getAllByStoreIdAndProductId(STORE_ID, PRODUCT_ID)).thenReturn(Arrays.asList(SAVED_INVENTORY));

        RequestBuilder requestBuilder = get(INVENTORY_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(SAVED_INVENTORY))))
                .andReturn();
    }
}
