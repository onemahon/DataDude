package com.azandria.bookSearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.azandria.data.books.Book;
import com.azandria.data.books.BookSearchAPIRequestMethod;
import com.azandria.data.books.BookSearchMemoryRequestMethod;
import com.azandria.data.books.BookSearchStubbedApiRequestMethod;
import com.azandria.datadude.R;
import com.azandria.datadude.utils.BasicDataRequestResponse;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.azandria.datadude.utils.IDataRequestResponse;
import com.raizlabs.coreutils.threading.ThreadingUtils;

import java.util.List;

public class BookSearchFragment extends Fragment {

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

    private IDataRequestResponse<List<Book>> mBookSearchResponseListener = new BasicDataRequestResponse<List<Book>>() {
        @Override
        public void onError(IDataRequestMethod method, int errorCode) {
            if (method instanceof BookSearchAPIRequestMethod) {
                super.onError(method, errorCode);
                showError(method);
            }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewHolder = new ViewHolder(view);

        mBooksAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_book);
        mViewHolder.ResultsList.setAdapter(mBooksAdapter);

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

        showEmpty();
    }

    @Override
    public void onDestroyView() {
        mViewHolder = null;
        super.onDestroyView();
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void showContent() {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mViewHolder != null) {
                    setLoading(false);
                    mViewHolder.ViewFlipper.setDisplayedChild(mViewHolder.mResultsIndex);
                }
            }
        });
    }

    private void showError(IDataRequestMethod method) {
        setLoading(false);

        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Error retrieving something, sorry!", Toast.LENGTH_SHORT).show();
            }
        });

        showEmpty();
    }

    private void showEmpty() {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mViewHolder != null) {
                    setLoading(false);
                    mViewHolder.ViewFlipper.setDisplayedChild(mViewHolder.mEmptyIndex);
                }
            }
        });
    }

    private void setLoading(final boolean active) {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mViewHolder != null) {
                    int visibility = active ? View.VISIBLE : View.INVISIBLE;
                    mViewHolder.LoadingIndicator.setVisibility(visibility);
                }
            }
        });
    }

    private void startSearch() {
        setLoading(true);

        // It's possible mViewHolder will be null, since it's only inflated
        // once the ViewStub holding it is shown. If the Fragment has not been
        // in the "content" state yet, it will not have been inflated.
        if (mViewHolder != null) {
            String searchString = mViewHolder.SearchText.getText().toString();

            new DataRequestBuilder<>(mBookSearchResponseListener)
                    .addRequestMethod(new BookSearchMemoryRequestMethod(searchString))
                    .addRequestMethod(new BookSearchStubbedApiRequestMethod(searchString))
//                    .addRequestMethod(new BookSearchAPIRequestMethod(searchString))
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
    // region Inner Classes

    public static class ViewHolder {
        private EditText SearchText;

        private ViewFlipper ViewFlipper;
        private ListView ResultsList;
        private ProgressBar LoadingIndicator;

        private int mResultsIndex;
        private int mEmptyIndex;

        public ViewHolder(View view) {
            SearchText = (EditText) view.findViewById(R.id.fragment_book_search_SearchText);

            LoadingIndicator = (ProgressBar) view.findViewById(R.id.fragment_book_search_LoadingIndicator);

            ViewFlipper = (ViewFlipper) view.findViewById(R.id.fragment_book_search_ViewFlipper);
            ResultsList = (ListView) view.findViewById(R.id.fragment_book_search_ListView);
            View emptyViewStub = view.findViewById(R.id.fragment_book_search_EmptyViewStub);

            mResultsIndex = ViewFlipper.indexOfChild(ResultsList);
            mEmptyIndex = ViewFlipper.indexOfChild(emptyViewStub);
        }
    }

    // endregion
    ///////////
}
