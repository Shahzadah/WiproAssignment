package com.demo.facts.getfacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.facts.R;
import com.demo.facts.data.FactDetails;

import java.util.List;

public class FactsListAdapter extends RecyclerView.Adapter<FactsListItemViewHolder> {


    private final Context mContext;
    private List<FactDetails> mListFactDetails;
    private FactsListContract.View mListener;

    private FactsListAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * Create adapter & returns it.
     *
     * @return : Return the adapter reference
     */
    public static FactsListAdapter create(Context context) {
        return new FactsListAdapter(context);
    }

    /**
     * Set Data of adapter.
     *
     * @param listFactDetails : List of random facts
     * @return : Return the adapter reference
     */
    public FactsListAdapter withData(List<FactDetails> listFactDetails) {
        this.mListFactDetails = listFactDetails;
        return this;
    }

    /**
     * Set Listener in adapter for view action.
     *
     * @param listener : Callback listener to communicate with presenter
     * @return : Return the adapter reference
     */
    public FactsListAdapter withListener(FactsListContract.View listener) {
        this.mListener = listener;
        return this;
    }

    @NonNull
    @Override
    public FactsListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_facts_item, parent, false);
        return new FactsListItemViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FactsListItemViewHolder holder, int position) {
        if (mListFactDetails == null || mListFactDetails.size() <= position || mListFactDetails.get(position) == null) {
            return;
        }
        FactDetails factDetails = mListFactDetails.get(position);
        holder.setValues(factDetails);
        holder.setClickListener((v, itemPosition) -> mListener.onFactItemSelected(factDetails));
    }

    @Override
    public int getItemCount() {
        return mListFactDetails.size();
    }
}