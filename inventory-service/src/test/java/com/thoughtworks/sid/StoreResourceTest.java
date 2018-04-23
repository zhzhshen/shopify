package com.thoughtworks.sid;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.StoresController;
import com.thoughtworks.sid.domain.Store;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreResourceTest {

    final Long STORE_ID = 1L;
    final String OWNER = "Sid";
    final String STORE_URL = "/api/stores/";
    final Store VALID_STORE = new Store(OWNER, "Sid's Store", "This is szz's store");
    final Store SAVED_STORE = new Store(STORE_ID, OWNER, "Sid's Store", "This is szz's store");

    private MockMvc mvc;

    @InjectMocks
    private StoresController storesController;

    @Mock
    StoreRepository storeRepository;

    @Autowired
    ObjectMapper objectMapper;

    Principal principal = Mockito.mock(Principal.class);

    @Before
    public void before() {
        when(principal.getName()).thenReturn(OWNER);
        this.mvc = MockMvcBuilders.standaloneSetup(storesController).build();
    }

    @Test
    public void should_get_401_to_get_stores() throws Exception {
        RequestBuilder requestBuilder = get(STORE_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_get_200_to_get_stores_with_principal() throws Exception {
        RequestBuilder requestBuilder = get(STORE_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void should_success_to_create_product() throws Exception {
        when(storeRepository.save(any(Store.class))).thenReturn(SAVED_STORE);
        when(storeRepository.getByOwnerAndId(OWNER, STORE_ID)).thenReturn(SAVED_STORE);

        RequestBuilder requestBuilder = post(STORE_URL).principal(principal)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(VALID_STORE));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(STORE_URL + STORE_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_STORE)))
                .andReturn();
    }

    @Test
    public void should_fail_to_find_inexist_store() throws Exception {
        RequestBuilder requestBuilder = get(STORE_URL + STORE_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_fail_to_find_others_store() throws Exception {
        should_success_to_create_product();
        when(principal.getName()).thenReturn("OTHERS");
        when(storeRepository.getByOwnerAndId("OTHERS", STORE_ID)).thenReturn(null);

        RequestBuilder requestBuilder = get(STORE_URL + STORE_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_success_to_find_existing_store() throws Exception {
        when(storeRepository.getByOwnerAndId(OWNER, STORE_ID)).thenReturn(SAVED_STORE);
        RequestBuilder requestBuilder = get(STORE_URL + STORE_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_STORE)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_store_list() throws Exception {
        List<Store> stores = Arrays.asList(SAVED_STORE);
        when(storeRepository.getAllByOwner(OWNER)).thenReturn(stores);
        RequestBuilder requestBuilder = get(STORE_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stores)));
    }
}
