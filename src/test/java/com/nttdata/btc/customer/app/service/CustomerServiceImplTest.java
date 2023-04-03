package com.nttdata.btc.customer.app.service;

import com.nttdata.btc.customer.app.cache.RedisCustomer;
import com.nttdata.btc.customer.app.cache.RedisRepository;
import com.nttdata.btc.customer.app.model.entity.Customer;
import com.nttdata.btc.customer.app.model.request.BalanceRequest;
import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.AccountBalanceResponse;
import com.nttdata.btc.customer.app.model.response.BalanceProductResponse;
import com.nttdata.btc.customer.app.model.response.BalanceResponse;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import com.nttdata.btc.customer.app.proxy.AccountRetrofitClient;
import com.nttdata.btc.customer.app.proxy.beans.account.AccountResponse;
import com.nttdata.btc.customer.app.repository.CustomerRepository;
import com.nttdata.btc.customer.app.service.impl.CustomerServiceImpl;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    CustomerRepository repository;

    @Mock
    RedisRepository redis;

    @Mock
    AccountRetrofitClient accountClient;

    @Mock
    CustomerEventsService eventsService;

    @InjectMocks
    CustomerServiceImpl service;

    List<Customer> listCustomers = new ArrayList<>();

    static final String ID_CUSTOMER = "640bf4a36bf23c1c772da9d6";
    static final String NAME_CUSTOMER = "Luiggi";

    @BeforeEach
    private void setUp() {
        Customer customer = new Customer();
        customer.setId_customer(ID_CUSTOMER);
        customer.setType_customer("1");
        customer.setCategory_customer("2");
        customer.setName(NAME_CUSTOMER);
        customer.setSurname("Rivera");
        customer.setType_document("1");
        customer.setNumber_document("12345678");
        customer.setPhone("123456789");
        customer.setEmail("Luiggi@gmail.com");
        customer.setRegisterDate(new Date());
        customer.setStatus(true);

        listCustomers.add(customer);
    }

    @Test
    @DisplayName("Return all customers")
    void testFindAll() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(listCustomers));
        when(redis.save(any())).thenReturn(new RedisCustomer());

        Flux<CustomerResponse> result = service.findAll();

        assertEquals(result.blockFirst().getId_customer(), listCustomers.get(0).getId_customer());
    }

    @Test
    @DisplayName("Return customer by id")
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(listCustomers.get(0)));
        when(redis.findById(anyString())).thenReturn(new RedisCustomer());

        Mono<CustomerResponse> result = service.findById(ID_CUSTOMER);

        assertEquals(ID_CUSTOMER, result.block().getId_customer());
    }

    @Test
    @DisplayName("Create new customer")
    void testSave() {
        Customer customer = listCustomers.get(0);

        CustomerRequest request = new CustomerRequest();
        request.setType_customer(customer.getType_customer());
        request.setCategory_customer(customer.getCategory_customer());
        request.setName(customer.getName());
        request.setSurname(customer.getSurname());
        request.setType_document(customer.getType_document());
        request.setNumber_document(customer.getNumber_document());
        request.setPhone(customer.getPhone());
        request.setEmail(customer.getEmail());

        when(repository.save(any())).thenReturn(Mono.just(customer));

        Mono<CustomerResponse> result = service.save(request);

        assertEquals(result.block().getNumber_document(), request.getNumber_document());
    }

    @Test
    @DisplayName("Delete customer")
    void testDeleteCustomer() {
        when(repository.findById(anyString())).thenReturn(Mono.just(listCustomers.get(0)));
        when(repository.save(any())).thenReturn(Mono.just(listCustomers.get(0)));

        Mono<Boolean> result = service.delete(ID_CUSTOMER).thenReturn(Boolean.TRUE);

        assertTrue(result.block());
    }

    @Test
    @DisplayName("Return customer by name")
    void testFindByName() {
        when(repository.findByName(anyString())).thenReturn(Flux.fromIterable(listCustomers));

        Flux<CustomerResponse> result = service.findByName(NAME_CUSTOMER);

        assertEquals(NAME_CUSTOMER, result.blockFirst().getName());
    }

    @Test
    @DisplayName("Update customer")
    void testUpdate() {
        Customer customer = listCustomers.get(0);

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setId_customer(ID_CUSTOMER);
        request.setType_customer(customer.getType_customer());
        request.setCategory_customer(customer.getCategory_customer());
        request.setName(customer.getName());
        request.setSurname(customer.getSurname());
        request.setType_document(customer.getType_document());
        request.setNumber_document(customer.getNumber_document());
        request.setPhone(customer.getPhone());
        request.setEmail("luiggi@gmail.com");

        when(repository.findById(anyString())).thenReturn(Mono.just(listCustomers.get(0)));
        when(repository.save(any())).thenReturn(Mono.just(customer));

        Mono<CustomerResponse> result = service.update(request);

        assertEquals("luiggi@gmail.com", result.block().getEmail());
    }

    @Test
    @DisplayName("Return check balance")
    void testCheckBalance() {
        BalanceRequest request = new BalanceRequest();
        request.setTypeDocument("1");
        request.setNumberDocument("12345678");
        request.setCodeProduct("640c24cd3b905b25cfa2f25a");

        when(repository.findByDocument(anyString(),anyString())).thenReturn(Flux.just(listCustomers.get(0)));
        when(accountClient.findByHolder(anyString())).thenReturn(Mono.just(buildAccountResponse()));

        Mono<BalanceResponse> result = service.checkBalance(request);

        assertEquals(ID_CUSTOMER, result.block().getCustomer().getId_customer());
    }

    private List<AccountResponse> buildAccountResponse(){
        AccountResponse beanResponse = new AccountResponse();
        beanResponse.setId_account("640cc29c60650d1637e040a9");
        beanResponse.setCode_account("193-1853946-0-26");
        beanResponse.setHolder_account(Collections.singletonList("640bf4a36bf23c1c772da9d6"));
        beanResponse.setAuthorized_signer(Collections.singletonList("640cab9692b061681e7d4d86"));
        beanResponse.setProduct("640c24cd3b905b25cfa2f25a");
        beanResponse.setBalance(350.0);
        beanResponse.setTransactions(new ArrayList<>());
        beanResponse.setRegister_date(new Date());
        beanResponse.setStatus(true);
        List<AccountResponse> list = new ArrayList<>();
        list.add(beanResponse);
        return list;
    }
}