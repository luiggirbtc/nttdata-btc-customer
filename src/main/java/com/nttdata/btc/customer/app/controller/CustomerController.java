package com.nttdata.btc.customer.app.controller;

import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import com.nttdata.btc.customer.app.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class CustomerController.
 *
 * @author lrs
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    /**
     * Inject dependency CustomerService.
     */
    @Autowired
    private CustomerService service;

    /**
     * Service find by id.
     *
     * @param id {@link String}
     * @return {@link CustomerResponse}
     */
    @GetMapping("id/{id}")
    public Mono<ResponseEntity<CustomerResponse>> findById(@PathVariable final String id) {
        return service.findById(id)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Service find by name.
     *
     * @param name {@link String}
     * @return {@link CustomerResponse}
     */
    @GetMapping("name/{name}")
    public Flux<CustomerResponse> findByName(@PathVariable final String name) {
        return service.findByName(name)
                .doOnNext(customer -> log.info(customer.toString()));
    }

    /**
     * Service create customer.
     *
     * @param request {@link CustomerRequest}
     * @return {@link CustomerResponse}
     */
    @PostMapping("/")
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(@RequestBody final CustomerRequest request) {
        log.info("Start CreateCustomer.");
        return service.save(request)
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    /**
     * Service update customer.
     *
     * @param request {@link UpdateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    @PutMapping("/")
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(@RequestBody final UpdateCustomerRequest request) {
        log.info("Start UpdateCustomer.");
        return service.update(request)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Service list all customers.
     *
     * @return {@link CustomerResponse}
     */
    @GetMapping("/")
    public Flux<CustomerResponse> findAll() {
        log.info("Start findAll Customers.");
        return service.findAll()
                .doOnNext(customer -> log.info(customer.toString()));
    }

    /**
     * Service delete customer.
     *
     * @param id {@link String}
     * @return {@link Void}
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable(value = "id") final String id) {
        return service.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}