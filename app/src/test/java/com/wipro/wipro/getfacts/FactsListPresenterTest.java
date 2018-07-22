package com.wipro.wipro.getfacts;

import com.wipro.wipro.CommonMethodForTest;
import com.wipro.wipro.R;
import com.wipro.wipro.Utils.NetworkUtil;
import com.wipro.wipro.Utils.ResponseHandler;
import com.wipro.wipro.data.FactDetails;
import com.wipro.wipro.data.FactList;
import com.wipro.wipro.data.source.remote.CloudDataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkUtil.class, CloudDataSource.class})
public class FactsListPresenterTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(CommonMethodForTest.TEST_TIMEOUT);

    private FactsListPresenter mPresenter;

    @Mock
    private FactsListContract.View view;

    @Mock
    private CloudDataSource mCloudDataSource;

    @Captor
    private ArgumentCaptor<ResponseHandler<FactList>> mFactsDataSourceCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockStatic(NetworkUtil.class);
        mockStatic(CloudDataSource.class);
        when(CloudDataSource.create()).thenReturn(mCloudDataSource);
        mPresenter = new FactsListPresenter();
        mPresenter.onAttach(view);
    }

    @Test
    public void loadRandomFactsWhenNetworkAvailable() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(true);
        mPresenter.loadRandomFacts();
        verify(view).setLoadingIndicator(true);
        verify(mCloudDataSource).getRandomFacts(eq(true), mFactsDataSourceCaptor.capture());
    }

    @Test
    public void loadRandomFactsServerError() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(true);
        mPresenter.loadRandomFacts();
        verify(view).setLoadingIndicator(true);
        verify(mCloudDataSource).getRandomFacts(eq(true), mFactsDataSourceCaptor.capture());

        mFactsDataSourceCaptor.getValue().onRequestFailure("Server unavailable");
        verify(view).setLoadingIndicator(false);
        verify(view).onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
    }

    @Test
    public void loadRandomFactsSuccessWithAllNullData() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(true);
        mPresenter.loadRandomFacts();
        verify(view).setLoadingIndicator(true);
        verify(mCloudDataSource).getRandomFacts(eq(true), mFactsDataSourceCaptor.capture());

        List<FactDetails> factDetailsList = new ArrayList<>();
        FactDetails factDetails = mock(FactDetails.class);
        factDetailsList.add(factDetails);
        FactList factList = mock(FactList.class);
        when(factList.getListFacts()).thenReturn(factDetailsList);
        mFactsDataSourceCaptor.getValue().onRequestSuccess(factList);

        assertEquals(mPresenter.getListRandomFacts().size(), 0);
        verify(view).setLoadingIndicator(false);
        verify(view).notifyAdapter();
    }

    @Test
    public void loadRandomFactsSuccessWithValidData() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(true);
        mPresenter.loadRandomFacts();
        verify(view).setLoadingIndicator(true);
        verify(mCloudDataSource).getRandomFacts(eq(true), mFactsDataSourceCaptor.capture());

        List<FactDetails> factDetailsList = new ArrayList<>();
        FactDetails factDetails = mock(FactDetails.class);
        when(factDetails.getTitle()).thenReturn("title");
        when(factDetails.getDescription()).thenReturn("description");

        factDetailsList.add(factDetails);
        FactList factList = mock(FactList.class);
        when(factList.getListFacts()).thenReturn(factDetailsList);
        mFactsDataSourceCaptor.getValue().onRequestSuccess(factList);

        assertEquals(mPresenter.getListRandomFacts().size(), factDetailsList.size());
        verify(view).setLoadingIndicator(false);
        verify(view).notifyAdapter();
    }
}