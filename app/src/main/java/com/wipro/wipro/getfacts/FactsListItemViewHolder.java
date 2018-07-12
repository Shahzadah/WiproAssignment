package com.wipro.wipro.getfacts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wipro.wipro.R;
import com.wipro.wipro.data.FactDetails;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FactsListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.image_fact)
    ImageView ivFactRep;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.textview_desc)
    TextView tvDescription;

    @BindView(R.id.progress_image_load)
    ProgressBar pbImageLoad;

    @BindDrawable(R.drawable.ic_no_image)
    Drawable mNoImageDrawable;

    private final Context mContext;
    private IRecyclerViewListClickListener clickListener;

    FactsListItemViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        itemView.setOnClickListener(this);
    }

    public void setValues(FactDetails factDetails) {
        tvTitle.setText(factDetails.getTitle());
        tvDescription.setText(factDetails.getDescription());
        ivFactRep.setImageDrawable(mNoImageDrawable);
        pbImageLoad.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(factDetails.getImageUrl())) {
            Picasso.with(mContext)
                    .load(factDetails.getImageUrl())
                    .fit()
                    .centerCrop()
                    .noFade()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(ivFactRep, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbImageLoad.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            pbImageLoad.setVisibility(View.GONE);
                        }
                    });
        } else {
            pbImageLoad.setVisibility(View.GONE);
        }
    }

    /**
     * Setter for listener.
     */
    public void setClickListener(IRecyclerViewListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getAdapterPosition());
    }

    interface IRecyclerViewListClickListener {

        /**
         * Called when the view is clicked.
         *
         * @param v        view that is clicked
         * @param position of the clicked item
         */
        void onClick(View v, int position);
    }
}
