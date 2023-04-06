package com.nttdata.btc.customer.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class NttdataBtcCustomerApplication {

	public static void main(String[] args) {
		log.info("Start component nttdata-btc-customer");
		SpringApplication.run(NttdataBtcCustomerApplication.class, args);
	}

}
