package com.wipro.wipro.getfacts;

import android.support.annotation.NonNull;

import com.wipro.wipro.R;
import com.wipro.wipro.Utils.NetworkUtil;
import com.wipro.wipro.Utils.ResponseHandler;
import com.wipro.wipro.data.FactDetails;
import com.wipro.wipro.data.FactList;
import com.wipro.wipro.data.source.FactDataSource;
import com.wipro.wipro.data.source.remote.CloudDataSource;
import com.wipro.wipro.framework.base.MVPView;

import java.util.ArrayList;
import java.util.List;

public class FactsListPresenter implements FactsListContract.Presenter {

    @NonNull
    private final List<FactDetails> mListFactDetails;

    @NonNull
    private final FactDataSource mDataSource;

    private FactsListContract.View mFactsView;

    /**
     * Creates a presenter to get show random facts.
     */
    FactsListPresenter() {
        mListFactDetails = new ArrayList<>();
        mDataSource = CloudDataSource.create();
    }

    @Override
    public void onAttach(MVPView view) {
        this.mFactsView = (FactsListContract.View) view;
    }

    @Override
    public void onDetach() {
        mFactsView = null;
    }

    @Override
    public List<FactDetails> getListRandomFacts() {
        return this.mListFactDetails;
    }

    @Override
    public void loadRandomFacts() {
        if (validateNetworkAvailability()) {
            mFactsView.setLoadingIndicator(true);
            callFactsRetrievalAPI(true);
        }
    }

    @Override
    public void onRefresh() {
        if (validateNetworkAvailability()) {
            callFactsRetrievalAPI(false);
        }
    }

    @Override
    public void onListItemClick(int position) {
        mFactsView.onFactItemSelected(mListFactDetails.get(position));
    }

    /**
     * Call API to get list of random facts.
     */
    private void callFactsRetrievalAPI(boolean useCache) {
        mDataSource.getRandomFacts(true, useCache, new ResponseHandler<FactList>() {
            @Override
            public void onRequestFailure(String errorMessage) {
                if (mFactsView != null) {
                    mFactsView.setLoadingIndicator(false);
                    mFactsView.onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
                }
            }

            @Override
            public void onRequestSuccess(FactList model) {
                if (model != null) {
                    mListFactDetails.clear();
                    mListFactDetails.addAll(model.getListFacts());

                }
                if (mFactsView != null) {
                    mFactsView.setLoadingIndicator(false);
                    mFactsView.notifyAdapter();
                }
            }
        });
    }

    /**
     * Send network unavailable error to activity and dismissIndicator progress indicator.
     */
    private boolean validateNetworkAvailability() {
        if (NetworkUtil.isNetworkConnected()) {
            return true;
        }
        mFactsView.setLoadingIndicator(false);
        mFactsView.onError(R.string.title_network_unavailable, R.string.network_un_available);
        return false;
    }
}