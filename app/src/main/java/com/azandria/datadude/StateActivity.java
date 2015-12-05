package com.azandria.datadude;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class StateActivity extends AppCompatActivity {

    ///////////
    // region Constants

    private final static String ROOT_FRAGMENT_TAG = "StateActivity:ROOT_FRAGMENT_TAG";

    // endregion
    ///////////

    ///////////
    // region Abstract Method Definitions

    protected abstract Fragment getRootFragment();

    // endregion
    ///////////

    ///////////
    // region Commonly Overridden Methods

    /**
     * Provide a layout resource ID for a custom XML layout
     * for your activity. If you implement this, you need to
     * either include a FrameLayout in that layout with the
     * resource ID of `R.id.activity_state_switchable_FragmentHolder`,
     * or override `getRootFragmentLayoutId()` to point the
     * Activity to the correct place to insert the root fragment.
     * @return A resource ID
     */
    protected int getLayoutResource() {
        return R.layout.activity_state_switchable;
    }

    /**
     * Provide a custom resource ID indicating where in the
     * Activity's layout XML structure this implementation
     * can find the FrameLayout into which the root fragment
     * will be inserted. To be used in conjunction with
     * overriding `getLayoutResource()` as well.
     * @return a layout resource element ID
     */
    protected int getRootFragmentLayoutId() {
        return R.id.activity_state_switchable_FragmentHolder;
    }

    // endregion
    ///////////

    ///////////
    // region Lifecycle


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("asdf", "onCreate started in parent");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        Fragment savedInstanceFragment = getSupportFragmentManager().findFragmentByTag(ROOT_FRAGMENT_TAG);

        if (savedInstanceFragment == null) {
            Fragment fragment = getRootFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(getRootFragmentLayoutId(), fragment, ROOT_FRAGMENT_TAG)
                    .commit();
        }
    }

    // endregion
    ///////////
}
