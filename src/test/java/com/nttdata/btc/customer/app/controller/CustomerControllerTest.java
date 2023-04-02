package com.nttdata.btc.customer.app.controller;

import com.nttdata.btc.customer.app.model.request.BalanceRequest;
import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.*;
import com.nttdata.btc.customer.app.service.CustomerService;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @InjectMocks
    CustomerController controller;

    @Mock
    CustomerService service;


    private List<CustomerResponse> listCustomers = new ArrayList<>();

    static final String ID_CUSTOMER = "640bf4a36bf23c1c772da9d6";
    static final String NAME_CUSTOMER = "Luiggi";

    @BeforeEach
    private void setUp() {
        CustomerResponse customer = new CustomerResponse();
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
    @DisplayName("Return check balance")
    void testCheckBalance() {
        BalanceRequest request = new BalanceRequest();
        request.setTypeDocument("1");
        request.setNumberDocument("12345678");
        request.setCodeProduct("640c24cd3b905b25cfa2f25a");

        List<AccountBalanceResponse> accounts = new ArrayList<>();
        AccountBalanceResponse account01 = new AccountBalanceResponse();
        account01.setCode_account("193-1853946-0-26");
        account01.setProduct("640c24cd3b905b25cfa2f25a");
        account01.setBalance(350.0);
        AccountBalanceResponse account02 = new AccountBalanceResponse();
        account02.setCode_account("193-1853946-0-33");
        account02.setProduct("640c24cd3b905b25cfa2f25a");
        account02.setBalance(650.0);
        AccountBalanceResponse account03 = new AccountBalanceResponse();
        account03.setCode_account("193-1853946-0-11");
        account03.setProduct("640ff1744975f4147b986d45");
        account03.setBalance(950.0);
        accounts.add(account01);
        accounts.add(account02);
        accounts.add(account03);

        List<BalanceProductResponse> balanceProducts = new ArrayList<>();
        BalanceProductResponse productBalance01 = new BalanceProductResponse();
        productBalance01.setProductCode("640c24cd3b905b25cfa2f25a");
        productBalance01.setProductBalance(1000.0);
        BalanceProductResponse productBalance02 = new BalanceProductResponse();
        productBalance02.setProductCode("640c24cd3b905b25cfa2f25a");
        productBalance02.setProductBalance(1000.0);
        balanceProducts.add(productBalance01);
        balanceProducts.add(productBalance02);

        BalanceResponse response = new BalanceResponse();
        response.setCustomer(listCustomers.get(0));
        response.setAccounts(accounts);
        response.setBalanceProducts(balanceProducts);


        when(service.checkBalance(any())).thenReturn(Mono.just(response));

        Mono<BalanceResponse> result = controller.checkBalance(request);

        assertEquals(result.block().getCustomer().getId_customer(), response.getCustomer().getId_customer());
    }

    @Test
    @DisplayName("Return operations by product")
    void testGetOperationsByProduct() {
        CustomerOperationsResponse response = new CustomerOperationsResponse();
        response.setCustomer(listCustomers.get(0));
        response.setCodeProduct("640c24cd3b905b25cfa2f25a");


        when(service.getOperations(any())).thenReturn(Mono.just(response));

        BalanceRequest request = new BalanceRequest();
        request.setCodeProduct("640c24cd3b905b25cfa2f25a");
        request.setTypeDocument("1");
        request.setNumberDocument("12345678");
        Mono<CustomerOperationsResponse> result = controller.getOperationsByProduct(request);

        assertEquals(result.block().getCodeProduct(), response.getCodeProduct());
    }

    @Test
    @DisplayName("Return customer by id")
    void testFindById() {
        when(service.findById(anyString())).thenReturn(Mono.just(listCustomers.get(0)));

        Mono<CustomerResponse> result = controller.findById(ID_CUSTOMER);

        assertEquals(result.block().getName(), listCustomers.get(0).getName());
    }

    @Test
    @DisplayName("Return customer by name")
    void testFindByName() {
        when(service.findByName(anyString())).thenReturn(Flux.fromIterable(listCustomers));

        Flux<CustomerResponse> result = controller.findByName(NAME_CUSTOMER);

        assertEquals(result.blockFirst().getName(), listCustomers.get(0).getName());
    }

    @Test
    @DisplayName("Create new customer")
    void testCreateCustomer() {
        CustomerResponse response = listCustomers.get(0);

        CustomerRequest request = new CustomerRequest();
        request.setType_customer(response.getType_customer());
        request.setCategory_customer(response.getCategory_customer());
        request.setName(response.getName());
        request.setSurname(response.getSurname());
        request.setType_document(response.getType_document());
        request.setNumber_document(response.getNumber_document());
        request.setPhone(response.getPhone());
        request.setEmail(response.getEmail());

        when(service.save(request)).thenReturn(Mono.just(response));

        Mono<CustomerResponse> result = controller.createCustomer(request);

        assertEquals(result.block().getId_customer(), response.getId_customer());
    }

    @Test
    @DisplayName("Update customer")
    void testUpdateCustomer() {
        CustomerResponse response = listCustomers.get(0);

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setId_customer(ID_CUSTOMER);
        request.setType_customer(response.getType_customer());
        request.setCategory_customer(response.getCategory_customer());
        request.setName(response.getName());
        request.setSurname(response.getSurname());
        request.setType_document(response.getType_document());
        request.setNumber_document(response.getNumber_document());
        request.setPhone(response.getPhone());
        request.setEmail(response.getEmail());

        when(service.update(any())).thenReturn(Mono.just(response));

        Mono<CustomerResponse> result = controller.updateCustomer(request);

        assertEquals(result.block().getNumber_document(), response.getNumber_document());
    }

    @Test
    @DisplayName("Return all customers")
    void testFindAll() {
        when(service.findAll()).thenReturn(Flux.fromIterable(listCustomers));

        Flux<CustomerResponse> result = controller.findAll();

        assertEquals(result.blockFirst().getEmail(), listCustomers.get(0).getEmail());
    }

    @Test
    @DisplayName("Delete customer")
    void testDeleteCustomer() {
        when(service.delete(anyString())).thenReturn(Mono.empty());

        Mono<Boolean> result = controller.deleteCustomer(ID_CUSTOMER).thenReturn(Boolean.TRUE);

        assertTrue(result.block());
    }
}