package com.wipro.wipro.data.source.local.db;

import android.provider.BaseColumns;

public class DbContracts {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RandomFacts.sqlite";

    public interface FactDetails extends BaseColumns {

        String TABLE_NAME   = "TblFacts";
    }
}