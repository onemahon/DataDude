package com.azandria.datadude.examples;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.azandria.datadude.R;
import com.azandria.datadude.data.BasicDataRequestResponse;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.DataRequestFilter;
import com.azandria.datadude.data.DataRequestMethod;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ListFromApiActivity extends LoadingStateActivity {

    // region member variables

    private ListLoaderHelper mListLoaderHelper;
    private ContentViewHolder mContentViewHolder;

    // Note: using a notational standard of "p" instead of "m" prefix to indicate this
    // variable is a property, and should only be accessed through overridden getters or setters
    private ArrayAdapter<Book> pBookAdapter;

    // endregion

    // region lifecycle

    @Override
    protected void onDestroy() {
        mListLoaderHelper.destroy();
        super.onDestroy();
    }

    // endregion

    // region private methods

    public ArrayAdapter<Book> getBookAdapter() {
        if (pBookAdapter == null) {
            pBookAdapter = new ArrayAdapter<>(this, R.layout.list_item_book);
            mContentViewHolder.mListView.setAdapter(pBookAdapter);
        }
        return pBookAdapter;
    }

    //

    // region superclass implementations

    @Override
    protected void setUpContentViewHolder(View view) {
        super.setUpContentViewHolder(view);
        mContentViewHolder = new ContentViewHolder(view);
    }

    @Override
    protected int getContentStateViewId() {
        return R.layout.view_list_from_api;
    }

    private BasicDataRequestResponse<List<Book>> bookListResponse = new BasicDataRequestResponse<List<Book>>() {
        @Override
        public void onCompleted(DataRequestMethod method, List<Book> books) {
            super.onCompleted(method, books);

            if (mListLoaderHelper != null) {
                mListLoaderHelper.showList();
            }

            getBookAdapter().clear();
            getBookAdapter().addAll(books);
        }
    };

    @Override
    protected void executeRequest(LoadingStateActivity.LoadingStateHelper loadingStateHelper) {
        mListLoaderHelper = new ListLoaderHelper(loadingStateHelper);

        new DataRequestBuilder<>(bookListResponse)
            .addRequestMethod(new BookListAPIRequest())
            .execute();
    }

    // endregion

    private static class ContentViewHolder {
        private ListView mListView;

        public ContentViewHolder(View view) {
            mListView = (ListView) view.findViewById(R.id.view_list_from_api_ListView);
        }
    }

    private static class ListLoaderHelper {

        private LoadingStateHelper mLoadingStateHelper;

        public ListLoaderHelper(LoadingStateHelper loadingStateHelper) {
            mLoadingStateHelper = loadingStateHelper;
        }

        private void showList() {
            if (mLoadingStateHelper != null) {
                mLoadingStateHelper.showContent();
            }

            // Could now perform any other list initialization that you might need
        }

        private void destroy() {
            mLoadingStateHelper = null;
        }
    }

    /////////////////////
    // vvvvvv Here below are classes that could be extracted into separate files vvvvvvv

    private static class BookListAPIRequest implements DataRequestMethod<List<Book>> {

        @Override
        public boolean makeRequestDespiteExistingData() {
            return false;
        }

        @Override
        public void doRequest(DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<DataRequestFilter> filters) {
            // TODO: send out actual web request and deal with progress, empty, and error states as well

            LinkedList<Book> books = new LinkedList<>();
            for(int i = 0; i < 10; i++) {
                books.add(new Book());
            }

            listener.onCompleted(books);
        }
    }

    private static class Book {
        @Override
        public String toString() {
            return "A book";
        }
    }



}
