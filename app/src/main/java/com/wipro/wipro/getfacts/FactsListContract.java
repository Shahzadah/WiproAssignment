package com.wipro.wipro.getfacts;

import com.wipro.wipro.data.FactDetails;
import com.wipro.wipro.framework.base.BasePresenter;
import com.wipro.wipro.framework.base.MVPView;

import java.util.List;

class FactsListContract {

    interface View extends MVPView<Presenter> {

        void setLoadingIndicator(boolean active);

        void onFactItemSelected(FactDetails factDetails);

        void notifyAdapter();
    }

    interface Presenter extends BasePresenter {

        List<FactDetails> getListRandomFacts();

        void loadRandomFacts();

        void onRefresh();

        void onListItemClick(int position);

    }
}