package com.azandria.bookSearch;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.azandria.data.books.Book;
import com.azandria.data.books.BookSearchAPIRequestMethod;
import com.azandria.data.books.BookSearchMemoryRequestMethod;
import com.azandria.datadude.R;
import com.azandria.datadude.StateFragment;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.azandria.datadude.utils.IDataRequestResponse;
import com.azandria.datadude.utils.StateChanger;
import com.raizlabs.coreutils.threading.ThreadingUtils;

import java.util.List;

public class BookSearchFragment extends StateFragment {

    ///////////
    // region Public Factory Initialization

    public static BookSearchFragment newInstance() {
        return new BookSearchFragment();
    }

    // endregion
    ///////////

    ///////////
    // region Member Variables

    private ArrayAdapter<Book> mBooksAdapter;

    private ViewHolder mViewHolder;

    private IDataRequestResponse<List<Book>> mBookSearchResponseListener = new StateRequestResponse<List<Book>>() {

        @Override
        public StateChanger getStateChanger() {
            return BookSearchFragment.this;
        }

        @Override
        public void onCompleted(IDataRequestMethod method, List<Book> books) {
            super.onCompleted(method, books);

            if (books != null && books.size() > 0) {
                setList(books);
            } else if (books != null) {
                showEmpty();
            } else {
                showError(method);
            }
        }
    };

    // endregion
    ///////////

    ///////////
    // region lifecycle

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showContent();

        mViewHolder.SearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    startSearch();
                    return true;
                }

                return false;
            }
        });
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void startSearch() {
        showLoading();

        // It's possible mViewHolder will be null, since it's only inflated
        // once the ViewStub holding it is shown. If the Fragment has not been
        // in the "content" state yet, it will not have been inflated.
        if (mViewHolder != null) {
            String searchString = mViewHolder.SearchText.getText().toString();

            new DataRequestBuilder<>(mBookSearchResponseListener)
                    .addRequestMethod(new BookSearchMemoryRequestMethod(searchString))
                    .addRequestMethod(new BookSearchAPIRequestMethod(searchString))
                    .execute();
        }
    }

    private void setList(final List<Book> books) {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mBooksAdapter.clear();
                mBooksAdapter.addAll(books);

                showContent();
            }
        });
    }

    // endregion
    ///////////

    ///////////
    // region abstract class overrides

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_book_search;
    }

    @Override
    protected void onContentViewInitialized(View view) {
        super.onContentViewInitialized(view);

        mViewHolder = new ViewHolder(view);

        mBooksAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_book);
        mViewHolder.ResultsList.setAdapter(mBooksAdapter);
    }

    @Override
    protected void onEmptyViewInitialized(View view) {
        super.onEmptyViewInitialized(view);

        /*
         * TODO: when no results come back (or when error)
         * there's no way to try different text. Need to
         * come up with something that allows you to enter text
         * even with the state fragment changes.
         */

        /*
         * TODO: also, when loading, the implementation should have
         * an easy option to just show a progress bar or something
         * like that, rather than completely changing to a different
         * view.
         *
         * That should be true everywhere, actually. The implementation
         * should have the ability to decide if it's going to just do
         * something (e.g. pop up a toast message for an error, or show
         * a progress bar for loading), rather than completely change to
         * a different view.
         */

        
    }

    // endregion
    ///////////

    ///////////
    // region Inner Classes

    public static class ViewHolder {
        private EditText SearchText;
        private ListView ResultsList;

        public ViewHolder(View view) {
            SearchText = (EditText) view.findViewById(R.id.fragment_book_search_SearchText);
            ResultsList = (ListView) view.findViewById(R.id.fragment_book_search_ListView);
        }
    }

    // endregion
    ///////////
}
