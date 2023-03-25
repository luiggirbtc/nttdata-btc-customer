package com.nttdata.btc.customer.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NttdataBtcCustomerApplicationTests {

	@Test
	void contextLoads() {
		String expected = "btc-customer";
		String actual = "btc-customer";

		assertEquals(expected, actual);
	}
}