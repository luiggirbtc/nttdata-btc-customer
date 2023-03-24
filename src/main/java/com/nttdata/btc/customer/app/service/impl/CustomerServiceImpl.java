package com.nttdata.btc.customer.app.service.impl;

import com.nttdata.btc.customer.app.model.entity.Customer;
import com.nttdata.btc.customer.app.model.request.BalanceRequest;
import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.AccountBalanceResponse;
import com.nttdata.btc.customer.app.model.response.BalanceProductResponse;
import com.nttdata.btc.customer.app.model.response.BalanceResponse;
import com.nttdata.btc.customer.app.model.response.CustomerOperationsResponse;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import com.nttdata.btc.customer.app.model.response.OpResponse;
import com.nttdata.btc.customer.app.proxy.AccountRetrofitClient;
import com.nttdata.btc.customer.app.proxy.OperationRetrofitClient;
import com.nttdata.btc.customer.app.proxy.beans.account.AccountResponse;
import com.nttdata.btc.customer.app.proxy.beans.operation.OperationResponse;
import com.nttdata.btc.customer.app.repository.CustomerRepository;
import com.nttdata.btc.customer.app.service.CustomerService;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nttdata.btc.customer.app.util.mappers.OpResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
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
     * Inject dependency {@link OperationRetrofitClient}
     */
    @Autowired
    OperationRetrofitClient operationClient;

    /**
     * Inject dependency {@link AccountRetrofitClient}
     */
    @Autowired
    AccountRetrofitClient accountClient;

    /**
     * Reference interface OpResponseMapper.
     */
    private OpResponseMapper opMapper = Mappers.getMapper(OpResponseMapper.class);

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
     * Method return all operations by product.
     *
     * @param request {@link BalanceRequest}
     * @return {@link CustomerOperationsResponse}
     */
    @Override
    public Mono<CustomerOperationsResponse> getOperations(BalanceRequest request) {
        List<OpResponse> operations = new ArrayList<>();
        List<OperationResponse> listOperations = new ArrayList<>();
        // CustomerOperationsResponse response = new CustomerOperationsResponse();
        /*return operationClient.findBySourceAcc("640c24cd3b905b25cfa2f25a").map(s-> {
            response.setEjemplos(s);
        return response;
        });*/
        return getCustomer(request)
                .map(entity -> buildCustomerR.apply(entity))
                .map(customer -> CustomerOperationsResponse.builder().customer(customer).build())
                .flatMap(response -> accountClient.findByHolder(response.getCustomer().getId_customer())
                        .map(accounts -> accounts.stream().filter(s -> request.getCodeProduct().equalsIgnoreCase(s.getProduct())).map(s -> s.getId_account()).collect(Collectors.toList())
                        ).map(s -> response));
/*
valores -> toFluxx(valores).flatMap(valor -> {
                            //return operationClient.findBySourceAcc(valor).map(lista -> buildMapa(valor, lista))))
                             //   }
        return new CustomerOperationsResponse();})
            .map(f -> {
        log.info("PRINT-F :: " +f);
        return response;
    })
 */

    }

    private Map<String, List<OperationResponse>> buildMapa(String key, List<OperationResponse> lista) {
        log.info("buildMapa :: KEY -  " + key);
        log.info("buildMapa :: LISTA -  " + lista);
        Map<String, List<OperationResponse>> mapa = new HashMap<String, List<OperationResponse>>();
        return (Map<String, List<OperationResponse>>) mapa.put(key, lista);
    }

    private Flux<String> toFluxx(List<String> lista) {
        return Flux.fromIterable(lista);
    }

    private void printOrep(List<OperationResponse> oResponse) {
        log.info("START-OREP :: " + oResponse);
        oResponse.stream().forEach(System.out::println);
    }

    private Flux<AccountResponse> createFluxAccRep(List<AccountResponse> value) {
        return Mono.just(value).flatMapMany(Flux::fromIterable);
    }

    private void consumeProductx(List<AccountResponse> value, List<OperationResponse> listOperations) {
        value.forEach(account -> {
            log.info("ACCOUNT :: " + account);
            consumeClient(account, listOperations);
        });
    }


    private void consumeClient(AccountResponse x, List<OperationResponse> listOperations) {
        log.info("ACCOUNT-RESPONSE :: " + x);
        try {
            operationClient.findBySourceAcc(x.getId_account()).map(y -> {
                log.info("LISTxxx  :: " + y);
                listOperations.addAll(y);
                return listOperations;
            });
            log.info("LIST-OPERATIONS:: " + listOperations);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

/*
    private Mono<List<OpResponse>> listMono(List<AccountResponse> accounts, CustomerOperationsResponse response) {
        List<OpResponse> operations = new ArrayList<>();
        accounts.stream().map(account -> operationClient.findBySourceAcc(account.getId_account()))
                .flatMap(x -> x.)
.map(oResponse -> {
            log.info("O-RESPONSE :: " + oResponse);

            oResponse.stream().forEach(o -> operations.add(opMapper.toOpResponse(o)));
            return operations;
        });

    }*/
    /*
                                        value.forEach(account -> operationClient.findBySourceAcc(account.getId_account())
                                            .map(oResponse -> {
                                                log.info("O-RESPONSE :: " + oResponse);
                                                List<OpResponse> operations = new ArrayList<>();
                                                oResponse.stream().forEach(o -> operations.add(opMapper.toOpResponse(o)));


                                                response.setOperations(operations);


                                                return response;

                                            }));
                                    response.setCodeProduct(request.getCodeProduct());
                                    return response;
     */

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
/*
    public Mono<CustomerOperationsResponse> getOperations(BalanceRequest request) {
        return getCustomer(request)
                .map(entity -> buildCustomerR.apply(entity))
                .map(customer -> CustomerOperationsResponse.builder().customer(customer).build())
                .flatMap(response -> accountClient.findByHolder(response.getCustomer().getId_customer())
                        .map(accounts -> accounts.stream().filter(s -> request.getCodeProduct().equalsIgnoreCase(s.getProduct()))
                                .collect(Collectors.groupingBy(AccountResponse::getProduct)))
                        .map(map -> {
                            map.forEach((key, value) -> {
                                value.stream().forEach(account -> operationClient.findBySourceAcc(account.getCode_account())
                                        .map(oResponse -> {
                                            List<OpResponse> operations = new ArrayList<>();
                                            oResponse.stream().forEach(o -> operations.add(opMapper.toOpResponse(o)));


                                            response.setOperations(operations);
                                            response.setCodeProduct(key);

                                            return response;

                                        }));

                                log.info("MAPA --- " + key + ":" + value);
                            });
                            return response;
                        })
                );
    }
 */