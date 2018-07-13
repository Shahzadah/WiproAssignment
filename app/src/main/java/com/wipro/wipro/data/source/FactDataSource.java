package com.wipro.wipro.data.source;

import com.wipro.wipro.Utils.ResponseHandler;
import com.wipro.wipro.data.FactList;

public interface FactDataSource {

    void getRandomFacts(boolean useCache, final ResponseHandler<FactList> responseHandler);
}