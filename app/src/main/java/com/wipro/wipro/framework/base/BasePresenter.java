package com.wipro.wipro.framework.base;

public interface BasePresenter {

    void onAttach(MVPView<BasePresenter> view);

    void onDetach();
}