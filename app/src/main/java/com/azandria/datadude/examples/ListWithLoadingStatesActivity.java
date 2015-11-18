package com.azandria.datadude.examples;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.IDataRequestFilter;
import com.azandria.datadude.data.IDataRequestMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListWithLoadingStatesActivity extends LoadingStateActivity {

    // region member variables

    private ContentViewHolder mContentViewHolder;

    // Note: using a notational standard of "p" instead of "m" prefix to indicate this
    // variable is a property, and should only be accessed through overridden getters or setters
    private ArrayAdapter<Book> pBookAdapter;

    // Note: it's not *great* practice to put actual logic in member variables, but this
    // is composed here just for ease of writing and interpreting.
    private BasicDataRequestResponse<List<Book>> bookListResponse = new BasicDataRequestResponse<List<Book>>() {
        @Override
        public void onCompleted(IDataRequestMethod method, final List<Book> books) {
            super.onCompleted(method, books);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getBookAdapter().clear();
                    getBookAdapter().addAll(books);

                    if (books.size() > 0) {
                        showContent();
                    } else {
                        showEmpty();
                    }
                }
            });

            scheduleAnotherStubRequest();
        }

        @Override
        public void onError(IDataRequestMethod method, int errorCode) {
            super.onError(method, errorCode);
            showError();
            scheduleAnotherStubRequest();
        }
    };

    // endregion

    // region private methods

    private ArrayAdapter<Book> getBookAdapter() {
        if (pBookAdapter == null) {
            pBookAdapter = new ArrayAdapter<>(this, R.layout.list_item_book);
            mContentViewHolder.mListView.setAdapter(pBookAdapter);
        }
        return pBookAdapter;
    }

    // endregion

    // region superclass implementations

    @Override
    protected void setUpContentViewHolder(View view) {
        super.setUpContentViewHolder(view);
        mContentViewHolder = new ContentViewHolder(view);
    }

    @Override
    protected int getContentStateViewId() {
        return R.layout.view_basic_list;
    }

    @Override
    protected void executeRequest() {
        showLoading();

        new DataRequestBuilder<>(bookListResponse)
                .addRequestMethod(new StubbedBookListRequest(mDesiredListResult))
                .execute();

        mDesiredListResult++;
        if (mDesiredListResult > 3) {
            mDesiredListResult = 1;
        }
    }

    // endregion

    // region Inner Class Definitions

    private static class ContentViewHolder {
        private ListView mListView;

        public ContentViewHolder(View view) {
            mListView = (ListView) view.findViewById(R.id.view_basic_list_ListView);
        }
    }

    // endregion

    // region Throwaway Code

    private static final int WAIT_PERIOD_BETWEEN_REQUESTS = 3000;

    private int mDesiredListResult = 1;

    private void scheduleAnotherStubRequest() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                executeRequest();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, WAIT_PERIOD_BETWEEN_REQUESTS);
    }

    private static class StubbedBookListRequest implements IDataRequestMethod<List<Book>> {

        private static final int REQUEST_LENGTH = 1000;

        // 1 = complete, 2 = fail, 3 = empty
        private int mDesiredResult;

        public StubbedBookListRequest(int desiredResult) {
            mDesiredResult = desiredResult;
        }

        @Override
        public boolean makeRequestDespitePreviousSuccess() {
            return false;
        }

        @Override
        public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
            // Take a couple seconds, as though actually calling out to API
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    notifyRequestListener(listener);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, REQUEST_LENGTH);
        }

        /**
         * Just some fancy state-switching request result stuff. Not real code.
         * @param listener
         */
        private void notifyRequestListener(DataRequestBuilder.RequestMethodListener<List<Book>> listener) {
            if (mDesiredResult == 2) {
                listener.onFailed(404);
            } else {
                Book[] books = null;
                if (mDesiredResult == 1) {
                    books = new Book[] {
                            new Book(), new Book(), new Book(), new Book(), new Book(),
                            new Book(), new Book(), new Book(), new Book(), new Book(),
                            new Book(), new Book(), new Book(), new Book(), new Book(),
                            new Book(), new Book(), new Book(), new Book(), new Book()
                    };
                } else if (mDesiredResult == 3) {
                    books = new Book[0];
                }
                List<Book> result = Arrays.asList(books);
                listener.onCompleted(result);
            }
        }
    }

    public static class Book {
        @Override
        public String toString() {
            // toString is used by the default implementation of ArrayAdapter
            return "Book #"+hashCode();
        }
    }

    // endregion
}
