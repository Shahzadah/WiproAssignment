package com.demo.facts.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FactList implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<FactDetails> listFacts;

    public FactList() {}

    private FactList(Parcel in) {
        title = in.readString();
        listFacts = in.createTypedArrayList(FactDetails.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(listFacts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FactList> CREATOR = new Creator<FactList>() {
        @Override
        public FactList createFromParcel(Parcel in) {
            return new FactList(in);
        }

        @Override
        public FactList[] newArray(int size) {
            return new FactList[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public List<FactDetails> getListFacts() {
        return listFacts;
    }
}