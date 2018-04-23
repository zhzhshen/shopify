package com.thoughtworks.sid;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.sid.controller.OrdersController;
import com.thoughtworks.sid.domain.Order;
import com.thoughtworks.sid.domain.OrderItem;
import com.thoughtworks.sid.repository.OrderRepository;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderResourceTest {

    private final Long ORDER_ID = 1L;
    private final Long PRODUCT_PRICE_ID = 1L;
    private final Long PRODUCT_UNLOADING_ID = 1L;
    private final String OWNER = "Sid";
    private final String ORDER_URL = "/api/orders/";
    private final OrderItem ORDER_ITEM = new OrderItem(PRODUCT_PRICE_ID, PRODUCT_UNLOADING_ID, 2);
    private final Order VALID_ORDER = new Order(OWNER, 1000.0, Collections.singletonList(ORDER_ITEM));
    private final Order SAVED_ORDER = new Order(ORDER_ID, OWNER, 1000.0, Collections.singletonList(ORDER_ITEM));

    private MockMvc mvc;

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Principal principal = Mockito.mock(Principal.class);

    @Before
    public void before() {
        when(principal.getName()).thenReturn(OWNER);
        this.mvc = MockMvcBuilders.standaloneSetup(ordersController).build();
    }

    @Test
    public void should_get_401_to_get_orders() throws Exception {
        RequestBuilder requestBuilder = get(ORDER_URL);
        mvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void should_get_200_to_get_orders_with_principal() throws Exception {
        RequestBuilder requestBuilder = get(ORDER_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void should_success_to_create_order() throws Exception {
        when(orderRepository.save(any(Order.class))).thenReturn(SAVED_ORDER);
        when(orderRepository.getByCustomerAndId(OWNER, ORDER_ID)).thenReturn(SAVED_ORDER);

        RequestBuilder requestBuilder = post(ORDER_URL).principal(principal)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(VALID_ORDER));
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        requestBuilder = get(ORDER_URL + ORDER_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_ORDER)))
                .andReturn();
    }

    @Test
    public void should_fail_to_find_inexist_order() throws Exception {
        RequestBuilder requestBuilder = get(ORDER_URL + ORDER_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_fail_to_find_others_order() throws Exception {
        should_success_to_create_order();
        when(principal.getName()).thenReturn("OTHERS");
        when(orderRepository.getByCustomerAndId("OTHERS", ORDER_ID)).thenReturn(null);

        RequestBuilder requestBuilder = get(ORDER_URL + ORDER_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void should_success_to_find_existing_order() throws Exception {
        when(orderRepository.getByCustomerAndId(OWNER, ORDER_ID)).thenReturn(SAVED_ORDER);
        RequestBuilder requestBuilder = get(ORDER_URL + ORDER_ID).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(SAVED_ORDER)))
                .andReturn();
    }

    @Test
    public void should_success_to_get_order_list() throws Exception {
        List<Order> stores = Collections.singletonList(SAVED_ORDER);
        when(orderRepository.getAllByCustomer(OWNER)).thenReturn(stores);
        RequestBuilder requestBuilder = get(ORDER_URL).principal(principal);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stores)));
    }
}
