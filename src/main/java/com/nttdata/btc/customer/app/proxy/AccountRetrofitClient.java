package com.nttdata.btc.customer.app.proxy;

import com.nttdata.btc.customer.app.proxy.beans.account.AccountResponse;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Class AccountRetrofitClient: get accounts by holder.
 *
 * @author lrs
 */
public interface AccountRetrofitClient {
    @GET(value = "holder/{id}")
    Mono<List<AccountResponse>> findByHolder(@Path("id") String id);
}