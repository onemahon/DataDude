package com.azandria.placeFinder;

import android.support.v4.app.Fragment;

import com.azandria.datadude.BaseActivity;

public class PlaceActivity extends BaseActivity {
    @Override
    protected Fragment getRootFragment() {
        return PlaceFragment.newInstance();
    }
}
