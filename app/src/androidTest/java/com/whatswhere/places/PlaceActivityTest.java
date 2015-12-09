package com.whatswhere.places;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.whatswhere.R;

public class PlaceActivityTest extends ActivityInstrumentationTestCase2<PlaceActivity> {

    private PlaceActivity mActivity;
    private View mRefreshButton;

    public PlaceActivityTest() {
        super(PlaceActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        mRefreshButton = mActivity.findViewById(R.id.fragment_place_RefreshButton);
    }

    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mRefreshButton);
    }

    public void testTextView_initialLayout() {
        ViewAsserts.assertOnScreen(mActivity.getWindow().getDecorView(), mRefreshButton);
        assertTrue(View.VISIBLE == mRefreshButton.getVisibility());

        ViewGroup.LayoutParams layoutParams = mRefreshButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
