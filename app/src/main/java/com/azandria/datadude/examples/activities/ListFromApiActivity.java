package com.azandria.datadude.examples.activities;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.IDataRequestMethod;
import com.azandria.datadude.examples.books.Book;
import com.azandria.datadude.examples.books.BookListAPIRequestMethod;
import com.azandria.datadude.examples.books.BookListMemoryRequestMethod;

import java.util.List;

public class ListFromApiActivity extends LoadingStatesActivity {

    // region member variables

    private ContentViewHolder mContentViewHolder;

    // Note: using a notational standard of "p" instead of "m" prefix to indicate this
    // variable is a property, and should only be accessed through overridden getters or setters
    private ArrayAdapter<Book> pBookAdapter;

    // Note: it's not *great* practice to put actual logic in member variables, but this
    // is composed here just for ease of writing and interpreting.
    private BasicDataRequestResponse<List<Book>> mBookListResponse = new BasicDataRequestResponse<List<Book>>() {
        @Override
        public void onCompleted(IDataRequestMethod method, final List<Book> books) {
            super.onCompleted(method, books);

            showContent();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getBookAdapter().clear();
                    getBookAdapter().addAll(books);
                }
            });
        }
    };

    // endregion

    // region private methods

    private ArrayAdapter<Book> getBookAdapter() {
        if (pBookAdapter == null) {
            pBookAdapter = new ArrayAdapter<>(this, R.layout.list_item_book);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mContentViewHolder.mListView.setAdapter(pBookAdapter);
                }
            });
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

        String searchString = "Brave New World";

        new DataRequestBuilder<>(mBookListResponse)
            .addRequestMethod(new BookListMemoryRequestMethod(searchString))
            .addRequestMethod(new BookListAPIRequestMethod(searchString))
            .execute();
    }

    // endregion

    private static class ContentViewHolder {
        private ListView mListView;

        public ContentViewHolder(View view) {
            mListView = (ListView) view.findViewById(R.id.view_basic_list_ListView);
        }
    }
}
