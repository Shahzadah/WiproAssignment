package com.demo.facts.data.source.remote;

import android.util.LruCache;

import com.demo.facts.Utils.NetworkUtil;
import com.demo.facts.Utils.ResponseHandler;
import com.demo.facts.application.App;
import com.demo.facts.data.FactList;
import com.demo.facts.data.source.FactDataSource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CloudDataSource implements FactDataSource {

    private final ServiceInterface mServiceEndpoint;
    private final LruCache<Class<?>, Observable<?>> apiObservables = new LruCache<>(10);
    private static CloudDataSource mCloudDataSource;

    private CloudDataSource(ServiceInterface service) {
        this.mServiceEndpoint = service;
    }

    public static CloudDataSource create() {
        if (mCloudDataSource == null) {
            mCloudDataSource = new CloudDataSource(App.getInstance().getApiEndpointInterface());
        }
        return mCloudDataSource;
    }

    @SuppressWarnings({"unchecked", "unused"})
    @Override
    public void getRandomFacts(boolean useCache, final ResponseHandler<FactList> responseHandler) {

        //If Network not available and useCache is false or cache doesn't exist. Call internet not available callback.
        if (!NetworkUtil.isNetworkConnected()) {
            if (!useCache || !isCacheExist(FactList.class)) {
                responseHandler.onInternetNotAvailable();
                return;
            }
        }
        Observable<FactList> factListObservable = ((Observable<FactList>) getPreparedObservable(mServiceEndpoint.getRandomFacts(), FactList.class, true, useCache));
        DisposableObserver observer = factListObservable.subscribeWith(new DisposableObserver<FactList>() {
            @Override
            public void onNext(FactList factList) {
                responseHandler.onRequestSuccess(factList);
            }

            @Override
            public void onError(Throwable e) {
                responseHandler.onRequestFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache) {
        Observable<?> preparedObservable = null;

        if (useCache) {
            preparedObservable = apiObservables.get(clazz);
        }

        //If cache doesn't exist. Then, create observable to call API
        if (preparedObservable == null) {
            preparedObservable = unPreparedObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
            if (cacheObservable) {
                preparedObservable = preparedObservable.cache();
                apiObservables.put(clazz, preparedObservable);
            }
        }
        return preparedObservable;
    }

    private boolean isCacheExist(Class<?> clazz) {
        return apiObservables.get(clazz) != null;
    }
}