package com.wipro.wipro.getfacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wipro.wipro.BuildConfig;
import com.wipro.wipro.R;
import com.wipro.wipro.data.FactDetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class FactsListAdapterTest {

    @Mock
    private Context mContext;

    private FactsListAdapter adapter;
    private FactsListItemViewHolder holder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        adapter = FactsListAdapter.create(mContext);
        when(mContext.getString(anyInt())).thenReturn("Random facts");
    }

    @Test
    public void itemCount() {
        FactDetails factDetails = mock(FactDetails.class);
        adapter.withData(asList(factDetails, factDetails, factDetails));
        assertEquals(adapter.getItemCount(), 3);
    }

    @Test
    public void onCreateViewHolder_returnsViewHolder() {
        FactsListItemViewHolder viewHolder = adapter.onCreateViewHolder(new FrameLayout(RuntimeEnvironment.application), 0);
        assertNotNull(viewHolder);
    }

    @Test
    public void onBindViewHolder_setsTextInView() {
        FactDetails factDetails = mock(FactDetails.class);
        when(factDetails.getTitle()).thenReturn("Beavers");
        when(factDetails.getDescription()).thenReturn("Beavers are second only to humans in their...");

        adapter.withData(asList(factDetails));
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //We have a layout especially for the items in our recycler view. We will see it in a moment.
        View listItemView = inflater.inflate(R.layout.row_facts_item, null, false);
        holder = new FactsListItemViewHolder(mContext, listItemView);
        adapter.onBindViewHolder(holder, 0);
        assertEquals(holder.tvTitle.getText().toString(), "Beavers");
        assertEquals(holder.tvDescription.getText().toString(), "Beavers are second only to humans in their...");
    }
}