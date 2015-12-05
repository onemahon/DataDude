package com.azandria.datadude;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.azandria.bookSearch.BookSearchActivity;

public class BookSearchActivityTest extends ActivityInstrumentationTestCase2<BookSearchActivity> {

    private BookSearchActivity mActivity;
    private EditText mEditText;

    public BookSearchActivityTest() {
        super(BookSearchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mActivity = getActivity();
        mEditText = (EditText) mActivity.findViewById(R.id.fragment_book_search_SearchText);
    }

    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mEditText);
    }

//    public void testTextView_initialLabelText() {
//        assertEquals("Hello World!", mEditText.getText().toString());
//    }

    public void testTextView_initialLayout() {
        ViewAsserts.assertOnScreen(mActivity.getWindow().getDecorView(), mEditText);
        assertTrue(View.VISIBLE == mEditText.getVisibility());

        ViewGroup.LayoutParams layoutParams = mEditText.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }




}
