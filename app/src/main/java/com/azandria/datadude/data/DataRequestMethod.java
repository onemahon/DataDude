package com.azandria.datadude.data;

import java.util.Collection;

public interface DataRequestMethod<DataType> {

    /*
    Can apply this class to things like web requests and DB accessors

    needs an API that looks something like: given an ID, return an object (and that's all)
     */


    /**
     * @return true if you want to ensure the request gets made, regardless of
     */
    boolean makeRequestDespitePreviousSuccess();


    /**
     * Implementation of the request itself. This is welcome to be asynchronous - the one condition
     * the DataRequestBuilder API expects is that the implementation correctly calls
     * listener.onCompleted() or listener.onError() when the request method is complete.
     * @param listener
     */
    void doRequest(DataRequestBuilder.RequestMethodListener<DataType> listener, Collection<DataRequestFilter> filters);





}
