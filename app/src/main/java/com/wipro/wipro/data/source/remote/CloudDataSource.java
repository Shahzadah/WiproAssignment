package com.wipro.wipro.data.source.remote;

import android.util.LruCache;

import com.wipro.wipro.application.App;
import com.wipro.wipro.data.source.FactDataSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CloudDataSource implements FactDataSource {

    private ServiceInterface mServiceEndpoint;
    private LruCache<Class<?>, Observable<?>> apiObservables = new LruCache<>(10);
    private static CloudDataSource mCloudDataSource;

    private CloudDataSource(ServiceInterface service) {
        this.mServiceEndpoint = service;
    }

    public static CloudDataSource create() {
        if(mCloudDataSource == null) {
            mCloudDataSource =  new CloudDataSource(App.getInstance().getApiEndpointInterface());
        }
        return mCloudDataSource;
    }

    private Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz) {
        Observable<?> preparedObservable = apiObservables.get(clazz);
        if (preparedObservable == null) {
            //we are here because we have never created this observable before or we didn't want to use the cache...
            preparedObservable = unPreparedObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }
        return preparedObservable;
    }
}