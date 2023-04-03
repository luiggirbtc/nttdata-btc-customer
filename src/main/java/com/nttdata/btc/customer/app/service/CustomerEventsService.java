package com.nttdata.btc.customer.app.service;

import com.nttdata.btc.customer.app.model.events.CustomerCreatedEvent;
import com.nttdata.btc.customer.app.model.events.Event;
import com.nttdata.btc.customer.app.model.events.EventType;
import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.UUID;

/**
 * Class CustomerEventsService component that publishes the message.
 *
 * @author lrs
 */
@Component
public class CustomerEventsService {

    @Autowired
    private KafkaTemplate<String, Event<?>> producer;

    @Value("${topic.customer.name}")
    private String topicCustomer;

    public void publish(CustomerResponse customer) {

        CustomerCreatedEvent created = new CustomerCreatedEvent();
        created.setData(customer);
        created.setId(UUID.randomUUID().toString());
        created.setType(EventType.CREATED);
        created.setDate(new Date());

        this.producer.send(topicCustomer, created);
    }


}