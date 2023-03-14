package com.nttdata.btc.customer.app.service.impl;

import com.nttdata.btc.customer.app.model.entity.Customer;
import com.nttdata.btc.customer.app.model.request.BalanceRequest;
import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.AccountBalanceResponse;
import com.nttdata.btc.customer.app.model.response.BalanceProductResponse;
import com.nttdata.btc.customer.app.model.response.BalanceResponse;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import com.nttdata.btc.customer.app.proxy.AccountRetrofitClient;
import com.nttdata.btc.customer.app.proxy.ProductRetrofitClient;
import com.nttdata.btc.customer.app.proxy.beans.account.AccountResponse;
import com.nttdata.btc.customer.app.repository.CustomerRepository;
import com.nttdata.btc.customer.app.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nttdata.btc.customer.app.util.constant.Constants.DEFAULT_FALSE;

/**
 * Service Implement CustomerServiceImpl.
 *
 * @author lrs
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    /**
     * Inject dependency {@link CustomerRepository}
     */
    @Autowired
    CustomerRepository repository;

    /**
     * Inject dependency {@link CustomerRepository}
     */
    @Autowired
    ProductRetrofitClient productClient;

    /**
     * Inject dependency {@link AccountRetrofitClient}
     */
    @Autowired
    AccountRetrofitClient accountClient;

    /**
     * This method return all customers.
     *
     * @return {@link List<CustomerResponse>}
     */
    @Override
    public Flux<CustomerResponse> findAll() {
        return repository.findAll().filter(Customer::isStatus)
                .map(c -> buildCustomerR.apply(c))
                .onErrorReturn(new CustomerResponse());
    }

    /**
     * This method find a customer by id.
     *
     * @param id {@link String}
     * @return {@link CustomerResponse}
     */
    @Override
    public Mono<CustomerResponse> findById(String id) {
        return repository.findById(id)
                .filter(Customer::isStatus)
                .map(e -> buildCustomerR.apply(e))
                .onErrorReturn(new CustomerResponse());
    }

    /**
     * This method save a customer.
     *
     * @param request {@link CustomerRequest}
     * @return {@link CustomerResponse}
     */
    @Override
    public Mono<CustomerResponse> save(CustomerRequest request) {
        return repository.save(buildCustomer.apply(request))
                .flatMap(entity -> Mono.just(buildCustomerR.apply(entity)))
                .onErrorReturn(new CustomerResponse());
    }

    /**
     * This method update status from customer.
     *
     * @param id {@link String}
     * @return {@link Void}
     */
    @Override
    public Mono<Void> delete(String id) {
        return repository.findById(id).filter(Customer::isStatus)
                .map(e -> updateStatus.apply(e, DEFAULT_FALSE))
                .flatMap(e -> repository.delete(e));
    }

    /**
     * This method find a customer by name.
     *
     * @param name {@link String}
     * @return {@link List<CustomerResponse>}
     */
    @Override
    public Flux<CustomerResponse> findByName(String name) {
        return repository.findByName(name)
                .filter(Customer::isStatus)
                .map(e -> buildCustomerR.apply(e));
    }

    /**
     * This method update a customer.
     *
     * @param request {@link UpdateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    @Override
    public Mono<CustomerResponse> update(UpdateCustomerRequest request) {
        return repository.findById(request.getId_customer())
                .map(entity -> updateCustomer.apply(request, entity))
                .flatMap(customer -> repository.save(customer))
                .flatMap(cupdated -> Mono.just(buildCustomerR.apply(cupdated)))
                .onErrorReturn(new CustomerResponse());
    }

    /**
     * This method check balance by product from customer.
     *
     * @param request {@link BalanceRequest}
     * @return {@link BalanceResponse}
     */
    @Override
    public Mono<BalanceResponse> checkBalance(BalanceRequest request) {
        BalanceResponse balanceResponse = new BalanceResponse();
        List<AccountBalanceResponse> accountList = new ArrayList<>();
        List<BalanceProductResponse> balanceProducts = new ArrayList<>();
        return getCustomer(request).map(entity -> buildCustomerR.apply(entity))
                .map(customer -> setCustomer.apply(customer, balanceResponse))
                .flatMap(response -> accountClient.findByHolder(response.getCustomer().getId_customer()))
                .map(accounts -> {
                    setBalance(accounts, balanceProducts);
                    accounts.forEach(account -> accountList.add(buildAccount.apply(account)));
                    balanceResponse.setAccounts(accountList);
                    balanceResponse.setBalanceProducts(balanceProducts);
                    return balanceResponse;
                });
    }

    /**
     * Method to group accounts by product.
     *
     * @param accounts        {@link List<AccountResponse>}
     * @param balanceProducts {@link List<BalanceProductResponse>}
     */
    private void setBalance(List<AccountResponse> accounts, List<BalanceProductResponse> balanceProducts) {
        Map<String, List<AccountResponse>> groupedProducts = accounts.stream().collect(Collectors.groupingBy(AccountResponse::getProduct));
        groupedProducts.forEach((key, value) -> balanceProducts.add(buildBalance(key, value)));
    }

    /**
     * Method to calculate and build balance by product.
     *
     * @param productCode {@link String}
     * @param account     {@link List<AccountResponse>}
     * @return {@link BalanceProductResponse}
     */
    private BalanceProductResponse buildBalance(String productCode, List<AccountResponse> account) {
        BalanceProductResponse response = new BalanceProductResponse();
        double balance;
        balance = account.stream().mapToDouble(AccountResponse::getBalance).sum();
        response.setProductCode(productCode);
        response.setProductBalance(balance);
        return response;
    }

    /**
     * Function build new AccountBalanceResponse.
     */
    Function<AccountResponse, AccountBalanceResponse> buildAccount = account -> {
        AccountBalanceResponse response = new AccountBalanceResponse();
        response.setCode_account(account.getCode_account());
        response.setBalance(account.getBalance());
        response.setProduct(account.getProduct());
        return response;
    };

    /**
     * Method find a consumer by document.
     *
     * @param request {@link BalanceRequest}
     * @return {@link Customer}
     */
    private Mono<Customer> getCustomer(BalanceRequest request) {
        return repository.findByDocument(request.getTypeDocument(), request.getNumberDocument()).collectList()
                .map(list -> list.stream().findFirst().orElse(null))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error not found.")));
    }

    /**
     * BiFunction update BalanceResponse.
     */
    BiFunction<CustomerResponse, BalanceResponse, BalanceResponse> setCustomer = (customer, response) -> {
        response.setCustomer(customer);
        return response;
    };

    /**
     * BiFunction update Customer.
     */
    BiFunction<UpdateCustomerRequest, Customer, Customer> updateCustomer = (request, customer) -> {
        customer.setType_customer(request.getType_customer());
        customer.setCategory_customer(request.getCategory_customer());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setType_document(request.getType_document());
        customer.setNumber_document(request.getNumber_document());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        return customer;
    };

    /**
     * BiFunction updateStatus from Customer.
     */
    BiFunction<Customer, Boolean, Customer> updateStatus = (customer, status) -> {
        customer.setStatus(status);
        return customer;
    };

    /**
     * Function build new Customer.
     */
    Function<CustomerRequest, Customer> buildCustomer = c -> new Customer(c.getType_customer(),
            c.getCategory_customer(), c.getName(), c.getSurname(), c.getType_document(),
            c.getNumber_document(), c.getPhone(), c.getEmail());

    /**
     * Function build new CustomerResponse.
     */
    Function<Customer, CustomerResponse> buildCustomerR = c -> {
        CustomerResponse response = new CustomerResponse();
        response.setType_customer(c.getType_customer());
        response.setCategory_customer(c.getCategory_customer());
        response.setName(c.getName());
        response.setSurname(c.getSurname());
        response.setType_document(c.getType_document());
        response.setNumber_document(c.getNumber_document());
        response.setPhone(c.getPhone());
        response.setEmail(c.getEmail());
        response.setId_customer(c.getId_customer());
        response.setRegisterDate(c.getRegisterDate());
        response.setStatus(c.isStatus());
        return response;
    };
}