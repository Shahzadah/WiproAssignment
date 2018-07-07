package com.wipro.wipro.framework.base;

public interface MVPView<T> {

    void onError(int errorTitleResId, int errorMsgResId);
}