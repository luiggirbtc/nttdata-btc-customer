package com.nttdata.btc.customer.app.controller;

import com.nttdata.btc.customer.app.model.request.BalanceRequest;
import com.nttdata.btc.customer.app.model.request.CustomerRequest;
import com.nttdata.btc.customer.app.model.request.UpdateCustomerRequest;
import com.nttdata.btc.customer.app.model.response.BalanceResponse;
import com.nttdata.btc.customer.app.model.response.CustomerOperationsResponse;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import com.nttdata.btc.customer.app.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Class CustomerController.
 *
 * @author lrs
 */
@Slf4j
@Tag(name = "Customer", description = "Service customer")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    /**
     * Inject dependency CustomerService.
     */
    @Autowired
    private CustomerService service;

    /**
     * Service check balance
     *
     * @return {@link BalanceResponse}
     */
    @Operation(summary = "Check balance by customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BalanceResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping(value = "/check/balance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BalanceResponse> checkBalance(@Valid @RequestBody BalanceRequest request) {
        log.info("Start Check Balance");
        log.info("Request :: " + request);
        return service.checkBalance(request);
    }

    /**
     * Service return all operations by product.
     *
     * @return {@link Mono<CustomerOperationsResponse>}
     */
    @Operation(summary = "Find operations by product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerOperationsResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping(value = "/operations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerOperationsResponse> getOperationsByProduct(@Valid @RequestBody BalanceRequest request) {
        log.info("Start GetOperationsByProduct");
        log.info("Request :: " + request);
        return service.getOperations(request);
    }

    /**
     * Service find by id.
     *
     * @param id {@link String}
     * @return {@link CustomerResponse}
     */
    @Operation(summary = "Get a customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping(value = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerResponse> findById(@PathVariable String id) {
        log.info("Start FindById");
        return service.findById(id);
    }

    /**
     * Service find by name.
     *
     * @param name {@link String}
     * @return {@link CustomerResponse}
     */
    @Operation(summary = "Get a customer by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping(value = "name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerResponse> createCustomer(@Valid @RequestBody final CustomerRequest request) {
        log.info("Start CreateCustomer.");
        return service.save(request);
    }

    /**
     * Service update customer.
     *
     * @param request {@link UpdateCustomerRequest}
     * @return {@link CustomerResponse}
     */
    @Operation(summary = "Update customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerResponse> updateCustomer(@Valid @RequestBody final UpdateCustomerRequest request) {
        log.info("Start UpdateCustomer.");
        return service.update(request);
    }

    /**
     * Service list all customers.
     *
     * @return {@link CustomerResponse}
     */
    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Operation(summary = "Delete customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @DeleteMapping(value = "/{id}")
    public Mono<Void> deleteCustomer(@PathVariable(value = "id") final String id) {
        return service.delete(id);
    }
}