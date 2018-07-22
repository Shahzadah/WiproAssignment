package com.wipro.wipro.getfacts;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wipro.wipro.R;
import com.wipro.wipro.Utils.AlertManager;
import com.wipro.wipro.data.FactDetails;
import com.wipro.wipro.framework.base.MVPView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsListActivity extends AppCompatActivity implements FactsListContract.View, SwipeRefreshLayout.OnRefreshListener {

    private FactsListContract.Presenter mPresenter;
    private FactsListAdapter mAdapter;
    private AlertDialog mDialog;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_list);
        ButterKnife.bind(this);
        setTitle(null);
        mPresenter = new FactsListPresenter();
        mPresenter.onAttach((MVPView) this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = FactsListAdapter.create(this)
                .withData(mPresenter.getListRandomFacts())
                .withListener(this);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        mPresenter.loadRandomFacts();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        // Making sure that setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void onFactItemSelected(FactDetails factDetails) {

    }

    @Override
    public void notifyAdapter() {
        // Making sure that notifyDataSetChanged() is called after the layout is done with everything else.
        recyclerView.post(() -> mAdapter.notifyDataSetChanged());
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onError(int errorTitleResId, int errorMsgResId) {
        mDialog = AlertManager.showError(this, getString(errorTitleResId), getString(errorMsgResId));
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}