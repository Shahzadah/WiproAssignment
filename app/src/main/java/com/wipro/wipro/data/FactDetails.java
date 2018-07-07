package com.wipro.wipro.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FactDetails implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageHref")
    private String imageUrl;

    private FactDetails(Parcel in) {
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FactDetails> CREATOR = new Creator<FactDetails>() {
        @Override
        public FactDetails createFromParcel(Parcel in) {
            return new FactDetails(in);
        }

        @Override
        public FactDetails[] newArray(int size) {
            return new FactDetails[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}