package com.demo.facts.getfacts;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.demo.facts.BuildConfig;
import com.demo.facts.CommonMethodForTest;
import com.demo.facts.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class FactsListActivityTest {

    @Mock
    FactsListPresenter mPresenter;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    private ActivityController mActivityController;
    private FactsListActivity mActivity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), FactsListActivity.class);
        mActivityController = Robolectric.buildActivity(FactsListActivity.class, intent).create();
        mActivity = (FactsListActivity) mActivityController.get();
        swipeRefreshLayout = mActivity.findViewById(R.id.SwipeRefreshLayout);
        recyclerView = mActivity.findViewById(R.id.recycler_view);

        CommonMethodForTest.setFieldValue(mActivity, "mPresenter", mPresenter);
    }

    @After
    public void tearDown() {
        mActivityController.pause();
        mActivityController.stop();
        mActivityController.destroy();
    }

    @Test
    public void checkNotNull() {
        assertNotNull(recyclerView);
        assertNotNull(swipeRefreshLayout);
    }

    @Test
    public void onError() {
        mActivity.onError(R.string.title_network_unavailable, R.string.network_un_available);
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
        assertNotNull(dialog.findViewById(R.id.errorHeaderTextView));
        assertNotNull(dialog.findViewById(R.id.errorMessageTextView));
        assertNotNull(dialog.findViewById(R.id.button_okay));

        String dialogTitle = ((TextView) dialog.findViewById(R.id.errorHeaderTextView)).getText().toString().trim();
        String dialogMessage = ((TextView) dialog.findViewById(R.id.errorMessageTextView)).getText().toString().trim();
        assertEquals(mActivity.getString(R.string.title_network_unavailable), dialogTitle);
        assertEquals(mActivity.getString(R.string.network_un_available), dialogMessage);

        TextView textViewOK = dialog.findViewById(R.id.button_okay);
        textViewOK.performClick();
    }

    @Test
    public void checkLoadingIndicator() {
        mActivity.setLoadingIndicator(true);
        assertTrue(swipeRefreshLayout.isRefreshing());

        mActivity.setLoadingIndicator(false);
        assertFalse(swipeRefreshLayout.isRefreshing());
    }

    @Test
    public void checkOnRefreshCall() {
        mActivity.onRefresh();
        verify(mPresenter).onRefresh();
    }
}