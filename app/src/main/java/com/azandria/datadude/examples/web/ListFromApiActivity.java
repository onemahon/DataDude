package com.azandria.datadude.examples.web;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.DataRequestMethod;
import com.azandria.datadude.examples.LoadingStateActivity;

import java.util.List;

public class ListFromApiActivity extends LoadingStateActivity {

    // region member variables

    private ContentViewHolder mContentViewHolder;

    // Note: using a notational standard of "p" instead of "m" prefix to indicate this
    // variable is a property, and should only be accessed through overridden getters or setters
    private ArrayAdapter<Book> pBookAdapter;

    // Note: it's not *great* practice to put actual logic in member variables, but this
    // is composed here just for ease of writing and interpreting.
    private BasicDataRequestResponse<List<Book>> bookListResponse = new BasicDataRequestResponse<List<Book>>() {
        @Override
        public void onCompleted(DataRequestMethod method, List<Book> books) {
            super.onCompleted(method, books);

            showContent();

            getBookAdapter().clear();
            getBookAdapter().addAll(books);
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
        new DataRequestBuilder<>(bookListResponse)
            .addRequestMethod(new BookListRetrofitRequest())
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
