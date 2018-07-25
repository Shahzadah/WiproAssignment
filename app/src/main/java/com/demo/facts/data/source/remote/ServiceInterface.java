package com.demo.facts.data.source.remote;

import com.demo.facts.data.FactList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ServiceInterface {

    @GET("./")
    Observable<FactList> getRandomFacts();
}