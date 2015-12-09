package com.whatswhere.places;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.azandria.boildroid.visual.BaseActivity;

public class PlaceActivity extends BaseActivity {
    @Override
    protected Fragment getRootFragment() {
        return PlaceFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
