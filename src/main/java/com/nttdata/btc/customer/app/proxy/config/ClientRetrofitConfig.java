package com.nttdata.btc.customer.app.proxy.config;

import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import com.nttdata.btc.customer.app.proxy.AccountRetrofitClient;
import com.nttdata.btc.customer.app.proxy.ProductRetrofitClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * config ClientRetrofitConfig.
 */
@Configuration
public class ClientRetrofitConfig {
    @Bean
    ProductRetrofitClient productRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8084/api/v1/product/")
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ProductRetrofitClient.class);
    }

    @Bean
    AccountRetrofitClient accountRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8085/api/v1/account/")
                .addCallAdapterFactory(ReactorCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AccountRetrofitClient.class);
    }
}