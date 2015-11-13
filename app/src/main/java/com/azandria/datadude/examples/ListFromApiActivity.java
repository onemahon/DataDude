package com.azandria.datadude.examples;

import com.azandria.datadude.R;

public class ListFromApiActivity extends LoadingStateActivity {

    @Override
    protected void executeRequest(LoadingStateActivity.LoadingStateHelper loadingStateHelper) {
        loadingStateHelper.showContent();
    }

    protected int getContentStateViewId() {
        return R.layout.view_list_from_api;
    }
}
