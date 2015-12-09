package com.azandria.boildroid.data;

public interface IDataRequestResponse<Data> {

//    // TODO: this might not be appropriate to have in this interface
//    void getStatus(IDataRequestMethod method);

    /**
     * Called when the IDataRequestMethod completes successfully.
     * @param method
     * @param data
     */
    void onCompleted(IDataRequestMethod method, Data data);

    /**
     * Called when any IDataRequestMethod encounters an error. Note that this could be called
     * amidst other, successful, IDataRequestMethod implementations, so should not necessarily be
     * considered a failure (until the DataRequestBuilder has gone through all queued request
     * methods)
     * @param method
     * @param errorCode
     */
    void onError(IDataRequestMethod method, int errorCode);

    /**
     * If the DataRequestBuilder cannot get through all of its responses in time, it will call
     * onTimeout() on the current request method, and onCancelled() on any queued request methods.
     * @param method
     */
    void onTimeout(IDataRequestMethod method);

    /**
     * Called when the IDataRequestMethod is removed from the queue without being started (most likely
     * due to an earlier method's timeout)
     * @param method
     */
    void onCancelled(IDataRequestMethod method);

    /**
     * Called when the IDataRequestMethod (that gets passed back as an argument) begins executing
     * the given DataRequest
     * @param method
     */
    void onBegun(IDataRequestMethod method);

    /**
     * Called immediately when this object is queued into a DataRequestBuilder, while it waits for
     * pending, higher-priority DataRequestMethods to complete.
     * @param method
     */
    void onQueued(IDataRequestMethod method);
}
