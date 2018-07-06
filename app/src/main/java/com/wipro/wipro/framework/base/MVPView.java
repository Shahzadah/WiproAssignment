package com.wipro.wipro.framework.base;

public interface MVPView<T> {

    void setPresenter(T presenter);

    void onError(int errorTitleResId, int errorMsgResId);
}