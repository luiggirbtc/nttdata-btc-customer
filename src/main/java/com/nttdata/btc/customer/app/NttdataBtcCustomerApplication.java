package com.nttdata.btc.customer.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class NttdataBtcCustomerApplication {

	public static void main(String[] args) {
		log.info("Start component nttdata-btc-customer");
		SpringApplication.run(NttdataBtcCustomerApplication.class, args);
	}

}
