package com.wipro.wipro.data.source.remote;

import com.wipro.wipro.data.FactList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceInterface {

    @GET("./")
    Observable<FactList> getRandomFacts();
}