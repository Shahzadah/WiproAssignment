package com.demo.facts.framework.base;

public interface BasePresenter {

    void onAttach(MVPView<BasePresenter> view);

    void onDetach();
}