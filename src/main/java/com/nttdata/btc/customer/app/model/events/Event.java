package com.nttdata.btc.customer.app.model.events;

import lombok.*;

import java.util.Date;

/**
 * Class abstract Event.
 *
 * @param <T> {@link T}
 * @author lrs
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event<T> {
    private String id;
    private Date date;
    private EventType type;
    private T data;
}