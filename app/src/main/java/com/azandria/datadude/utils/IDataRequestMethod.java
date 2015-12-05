package com.azandria.datadude.utils;

import java.util.Collection;

public interface IDataRequestMethod<DataType> {

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
    void doRequest(DataRequestBuilder.RequestMethodListener<DataType> listener, Collection<IDataRequestFilter> filters);

    /**
     * Determines if there are any requests following this one in the
     * request queue. This would be trivial to do from the RequestBuilder
     * but we sometimes want this information in places where we can't
     * access it.
     * @return <code>true</code> if the request is the final one in its queue.
     */
    boolean isLastInQueue();

    /**
     * Used in conjunction with <code>isLastInQueue</code>
     * @param isLast
     */
    void setIsLastInQueue(boolean isLast);
}
