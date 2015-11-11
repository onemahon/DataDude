package com.azandria.datadude;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class SimpleRequestActivityTest extends ActivityInstrumentationTestCase2<SimpleRequestActivity> {

    private SimpleRequestActivity mActivity;
    private TextView mTextView;

    public SimpleRequestActivityTest() {
        super(SimpleRequestActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        mTextView = (TextView) mActivity.findViewById(R.id.activity_test_TextView);
    }

    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mTextView);
    }

    public void testTextView_initialLabelText() {
        assertEquals("Hello World!", mTextView.getText());
    }

    public void testTextView_initialLayout() {
        ViewAsserts.assertOnScreen(mActivity.getWindow().getDecorView(), mTextView);
        assertTrue(View.VISIBLE == mTextView.getVisibility());

        ViewGroup.LayoutParams layoutParams = mTextView.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }




}
