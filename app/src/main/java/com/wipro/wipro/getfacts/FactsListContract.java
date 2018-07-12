package com.wipro.wipro.getfacts;

import com.wipro.wipro.data.FactDetails;
import com.wipro.wipro.framework.base.BasePresenter;
import com.wipro.wipro.framework.base.MVPView;

import java.util.List;

class FactsListContract {

    interface View extends MVPView<Presenter> {

        /**
         * Set refresh loading indicator status based on flag.
         *
         * @param active - flag to show/hide loading indicator
         */
        void setLoadingIndicator(boolean active);

        /**
         * on fact list item selected.
         *
         * @param factDetails - Clicked fact list item
         */
        void onFactItemSelected(FactDetails factDetails);

        /**
         * Notify recycler view adapter when underlying data changes.
         */
        void notifyAdapter();

        /**
         * Set toolbar title
         *
         * @param title :   Title text to be shown
         */
        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {

        /**
         * Return facts list to view.
         *
         * @return facts list
         */
        List<FactDetails> getListRandomFacts();

        /**
         * To load facts from data source.
         */
        void loadRandomFacts();

        /**
         * To load facts on swipe to refresh.
         */
        void onRefresh();

        /**
         * On list item click
         *
         * @param position - list item position
         */
        void onListItemClick(int position);

    }
}