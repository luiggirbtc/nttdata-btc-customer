package com.nttdata.btc.customer.app.proxy;

import com.nttdata.btc.customer.app.proxy.beans.account.AccountResponse;
import com.nttdata.btc.customer.app.proxy.beans.operation.OperationResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Class OperationRetrofitClient.
 *
 * @author lrs
 */
public interface OperationRetrofitClient {
    @GET(value = "code/{code}")
    Mono<List<OperationResponse>> findBySourceAcc(@Path("code") String code);
}