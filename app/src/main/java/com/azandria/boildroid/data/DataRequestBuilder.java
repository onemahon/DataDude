package com.azandria.boildroid.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataRequestBuilder<DataType> {

    /**
     * Milliseconds until the request builder times out. Note: this is inclusive of *all*
     * queued requests - so, if you queue a request that will take 4000 millis in front of 3
     * requests that will take under 500 millis, those quick requests will never have a chance to
     * run.
     */
    private static final long DEFAULT_REQUEST_TIMEOUT = 10000; // milliseconds

    private List<IDataRequestFilter> mFilters;
    private List<IDataRequestMethod> mMethods;

    /**
     * A private flag that determines if the requestBuilder has received a successful response
     * from one of its IDataRequestMethod calls. However, remaining IDataRequestMethod calls in which
     * makeRequestDespitePreviousSuccess() resolves to true shall still run.
     */
    private boolean mRequestHasSucceeded;

    /**
     * A local handle to the caller that will receive updates based on what happens with each
     * IDataRequestMethod.
     */
    private IDataRequestResponse<DataType> mDataRequestResponse;

    private long mTimeout;
    private boolean mExpired;

    public DataRequestBuilder(IDataRequestResponse<DataType> responseListener) {
        mFilters = new LinkedList<>();
        mMethods = new LinkedList<>();

        mTimeout = DEFAULT_REQUEST_TIMEOUT;
        mExpired = false;

        mDataRequestResponse = responseListener;
    }

    public DataRequestBuilder<DataType> addRequestMethod(IDataRequestMethod method) {
        if (!mExpired) {
            mMethods.add(method);

            if (mDataRequestResponse != null) {
                mDataRequestResponse.onQueued(method);
            }
        }
        return this;
    }

    public DataRequestBuilder<DataType> addFilter(IDataRequestFilter filter) {
        if (!mExpired) {
            mFilters.add(filter);
        }
        return this;
    }

    public void execute() {
        if (mExpired) {
            throw new IllegalStateException("You cannot execute a request that has expired");
        }

        startTimeoutTimer();
        executeTopRequestMethod();
    }

    private void executeTopRequestMethod() {
        if (mExpired) {
            return;
        }

        if (mMethods != null && mMethods.size() > 0) {
            final IDataRequestMethod method = mMethods.remove(0);

            if (method.makeRequestDespitePreviousSuccess() || !mRequestHasSucceeded) {

                if (mDataRequestResponse != null) {
                    mDataRequestResponse.onBegun(method);
                }

                RequestMethodListener<DataType> listener = createRequestMethodListener(method);
                method.doRequest(listener, mFilters);
            }
        }
    }

    private void expireTimer() {
        mExpired = true;

        if (mMethods != null && mMethods.size() > 0 && mDataRequestResponse != null) {
            for (IDataRequestMethod requestMethod : mMethods) {
                mDataRequestResponse.onCancelled(requestMethod);
            }
        }
    }

    private void startTimeoutTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                expireTimer();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, mTimeout);
    }

    private RequestMethodListener<DataType> createRequestMethodListener(final IDataRequestMethod method) {
        return new RequestMethodListener<DataType>() {
            @Override
            public void onCompleted(DataType result) {
                if (mDataRequestResponse != null) {
                    if (mExpired) {
                        mDataRequestResponse.onTimeout(method);
                    } else {
                        mDataRequestResponse.onCompleted(method, result);
                    }
                }

                mRequestHasSucceeded = true;

                executeTopRequestMethod();
            }

            @Override
            public void onFailed(int errorCode) {
                if (mDataRequestResponse != null) {
                    if (mExpired) {
                        mDataRequestResponse.onTimeout(method);
                    } else {
                        mDataRequestResponse.onError(method, errorCode);
                    }
                }

                executeTopRequestMethod();
            }
        };
    }

    public interface RequestMethodListener<RequestedDataType> {
        void onCompleted(RequestedDataType result);
        void onFailed(int errorCode);
    }
}
