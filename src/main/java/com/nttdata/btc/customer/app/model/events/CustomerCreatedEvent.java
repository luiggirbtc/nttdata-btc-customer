package com.nttdata.btc.customer.app.model.events;

import com.nttdata.btc.customer.app.model.response.CustomerResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class extension from CustomerResponse.
 *
 * @author lrs
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCreatedEvent extends Event<CustomerResponse> {
}