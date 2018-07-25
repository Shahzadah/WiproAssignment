package com.demo.facts.framework.base;

public interface MVPView<T> {

    void onError(int errorTitleResId, int errorMsgResId);
}