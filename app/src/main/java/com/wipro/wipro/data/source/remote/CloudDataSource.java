package com.wipro.wipro.data.source.remote;

import android.util.LruCache;

import com.wipro.wipro.Utils.NetworkUtil;
import com.wipro.wipro.Utils.ResponseHandler;
import com.wipro.wipro.application.App;
import com.wipro.wipro.data.FactList;
import com.wipro.wipro.data.source.FactDataSource;

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

        //If Network not available and cache doesn't exist, Show Error. Else, use cache as default if exist.
        if (!NetworkUtil.isNetworkConnected()) {
            if (!isCacheExist(FactList.class)) {
                responseHandler.onInternetNotAvailable();
                return;
            }
            useCache = true;
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