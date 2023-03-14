package com.nttdata.btc.customer.app.repository;

import com.nttdata.btc.customer.app.model.entity.Customer;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
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

    /**
     * Method findByDocument.
     *
     * @param type_document   {@link String}
     * @param number_document {@link String}
     * @return {@link Customer}
     */
    @Query("{'type_document' : :#{#type_document}, 'number_document' : :#{#number_document}}")
    Flux<Customer> findByDocument(@Param("type_document") String type_document, @Param("number_document") String number_document);
}