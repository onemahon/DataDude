package com.azandria.datadude.examples.books;

import android.util.Log;

import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.IDataRequestFilter;
import com.azandria.datadude.data.IDataRequestMethod;

import java.util.Collection;
import java.util.List;

public class BookListMemoryRequestMethod implements IDataRequestMethod<List<Book>> {

    private String mSearchString;

    public BookListMemoryRequestMethod(String searchString) {
        mSearchString = searchString;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return true;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
        List<Book> result = BookListManager.get().get(mSearchString);
        if (result != null) {
            Log.d(getClass().getName(), "in-memory request completed");

            listener.onCompleted(result);
        } else {
            Log.d(getClass().getName(), "in-memory request failed");

            listener.onFailed(-1);
        }
    }
}
