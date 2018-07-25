package com.demo.facts.data.source;

import com.demo.facts.Utils.ResponseHandler;
import com.demo.facts.data.FactList;

public interface FactDataSource {

    void getRandomFacts(boolean useCache, final ResponseHandler<FactList> responseHandler);
}