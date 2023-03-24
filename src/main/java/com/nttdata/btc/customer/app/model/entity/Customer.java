package com.nttdata.btc.customer.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Customer.
 *
 * @author lrs
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customer")
public class Customer {
    @Id
    private String id_customer;

    /**
     * 1 = personal, 2 = empresarial
     */
    private String type_customer;

    /**
     * 0 = ninguno, 1 = titular , 2 = firmante
     */
    private String category_customer;

    private String name;

    private String surname;

    private String type_document;

    private String number_document;

    private String phone;

    private String email;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date registerDate = new Date();

    private boolean status = true;

    /**
     * Constructor create a new customer.
     *
     * @param type_customer     {@link String}
     * @param category_customer {@link String}
     * @param name              {@link String}
     * @param surname           {@link String}
     * @param type_document     {@link String}
     * @param number_document   {@link String}
     * @param phone             {@link String}
     * @param email             {@link String}
     */
    public Customer(String type_customer, String category_customer, String name,
                    String surname, String type_document, String number_document,
                    String phone, String email) {
        this.type_customer = type_customer;
        this.category_customer = category_customer;
        this.name = name;
        this.surname = surname;
        this.type_document = type_document;
        this.number_document = number_document;
        this.phone = phone;
        this.email = email;
    }
}