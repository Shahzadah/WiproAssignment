package com.wipro.wipro.Utils;

public interface ResponseHandler<M> {
    void onRequestFailure(String errorMessage);

    void onRequestSuccess(M model);
}