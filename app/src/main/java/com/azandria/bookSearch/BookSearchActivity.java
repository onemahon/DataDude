package com.azandria.bookSearch;

import android.support.v4.app.Fragment;

import com.azandria.datadude.BaseActivity;

public class BookSearchActivity extends BaseActivity {

    ///////////
    // region Abstract Method Implementations

    @Override
    protected Fragment getRootFragment() {
        return BookSearchFragment.newInstance();
    }

    // endregion
    ///////////

    ///////////
    // region Parent Class Method Overrides

    // TODO: custom activity layout with an action bar and maybe some navigation

    // endregion
    ///////////
}
