package com.nttdata.btc.customer.app.proxy;

import com.nttdata.btc.customer.app.proxy.beans.product.ProductResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Class ProductRetrofitClient: Consume services from product.
 *
 * @author lrs
 */
public interface ProductRetrofitClient {

    @GET(value = "id/{id}")
    Mono<ProductResponse> findById(@Path("id") String id);
}