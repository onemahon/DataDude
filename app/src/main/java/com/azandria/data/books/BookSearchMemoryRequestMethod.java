package com.azandria.data.books;

import android.util.Log;

import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;
import com.azandria.datadude.utils.IDataRequestMethod;

import java.util.Collection;
import java.util.List;

public class BookSearchMemoryRequestMethod implements IDataRequestMethod<List<Book>> {

    private String mSearchString;
    private boolean mIsLastInQueue;

    public BookSearchMemoryRequestMethod(String searchString) {
        mSearchString = searchString;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return true;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
        List<Book> result = BookSearchManager.get().get(mSearchString);
        if (result != null) {
            Log.d(getClass().getName(), "in-memory request completed");

            listener.onCompleted(result);
        } else {
            Log.d(getClass().getName(), "in-memory request failed");

            listener.onFailed(-1);
        }
    }

    @Override
    public boolean isLastInQueue() {
        return mIsLastInQueue;
    }

    @Override
    public void setIsLastInQueue(boolean isLast) {
        mIsLastInQueue = isLast;
    }
}
