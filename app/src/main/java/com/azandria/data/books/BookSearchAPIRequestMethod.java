package com.azandria.data.books;

import android.util.Log;

import com.azandria.datadude.utils.BasicWebServiceManager;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.raizlabs.webservicemanager.webservicemanager.ResultInfo;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceManager;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceRequestListener;

import java.util.Collection;
import java.util.List;

public class BookSearchAPIRequestMethod implements IDataRequestMethod<List<Book>> {

    private String mSearchString;
    private boolean mIsLastInQueue;

    public BookSearchAPIRequestMethod(String searchString) {
        mSearchString = searchString;
    }

    @Override
    public boolean isLastInQueue() {
        return mIsLastInQueue;
    }

    @Override
    public void setIsLastInQueue(boolean isLast) {
        mIsLastInQueue = isLast;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        // This could be set to true, if you wanted to ensure
        // the cached data will always be updated from the API -
        // but, you'd have to monitor loading states more tightly.
        // (consider, if you get a cached results hit, show that
        // stuff to the user, then AFTER that, you receive another
        // positive hit from the API request listener, and the data
        // has changed. Don't just interrupt the user's experience
        // by changing the data without any indication of what's
        // happening - be smooth. Either give them a notice saying,
        // "that info's out of date!", or, do some kind of smooth
        // animation to interpolate the data (e.g. slide an item
        // into a list in a non-interruptive way).
        return false;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
        BasicWebServiceManager.get().doRequestInBackground(new BookSearchApiRequest(mSearchString), new WebServiceRequestListener<List<Book>>() {
            @Override
            public void onRequestComplete(WebServiceManager webServiceManager, ResultInfo<List<Book>> resultInfo) {
                if (resultInfo.isStatusOK()) {
                    Log.d(getClass().getName(), "web request completed");

                    listener.onCompleted(resultInfo.getResult());

                    // Important note: you *do* still have to manage the storing of
                    // to-be-cached results yourself. It is up to you discretion when
                    // you want to cache results or not.
                    // In this example, as soon as the API request comes back with results, I want
                    // to store that in the cache - every time the request completes successfully.
                    BookSearchManager.get().store(mSearchString, resultInfo.getResult());
                } else {
                    Log.d(getClass().getName(), "web request failed");

                    listener.onFailed(resultInfo.getResponseCode());
                }
            }
        });
    }
}
