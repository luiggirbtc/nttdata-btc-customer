package com.nttdata.btc.customer.app.service;

import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service CustomerService.
 *
 * @author lrs.
 */
public interface CustomerService {
    /**
     * Method findAll.
     */
    Flux<CustomerResponse> findAll();

    /**
     * Method findById.
     */
    Mono<CustomerResponse> findById(String id);

    /**
     * Method save.
     */
    Mono<CustomerResponse> save(CustomerRequest request);

    /**
     * Method Delete.
     */
    Mono<Void> delete(String id);

    /**
     * Method findByName.
     */
    Flux<CustomerResponse> findByName(String name);

    /**
     * Method update customer.
     *
     * @param request {@link UpdateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    Mono<CustomerResponse> update(UpdateCustomerRequest request);
}