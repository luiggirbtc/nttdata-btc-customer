package com.nttdata.btc.customer.app.repository;

import com.nttdata.btc.customer.app.model.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Response bean CustomerRepository.
 *
 * @author lrs
 */
@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    /**
     * Method findByName.
     *
     * @param name {@link String}
     * @return {@link Customer}
     */
    Flux<Customer> findByName(String name);
}