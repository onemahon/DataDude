package com.azandria.data.books;

import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookSearchStubbedApiRequestMethod extends BookSearchAPIRequestMethod {

    public BookSearchStubbedApiRequestMethod(String searchString) {
        super(searchString);
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return false;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                JSONArray stubbedBooksJson = null;

                try {
                    stubbedBooksJson = new JSONArray("[ { 'title': 'Brave New World' }, { 'title': '1984' } ]");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (Math.random() > 0.5) {
                    listener.onCompleted(Book.fromList(stubbedBooksJson));
                } else {
                    listener.onFailed(-1);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}
