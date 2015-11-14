package com.azandria.datadude.examples;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

import com.azandria.datadude.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingStateActivity extends Activity {

    private LoadingStateHelper mLoadingStateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_state);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set up the LoadingStateHelper so we can change the view's state
        // later, while processing requests.
        View view = findViewById(R.id.activity_loading_state_ViewFlipper);
        LoadingStateViewHolder viewHolder = new LoadingStateViewHolder(view, getContentStateViewId());
        mLoadingStateHelper = new LoadingStateHelper(viewHolder);

        // Give any implementations a chance to set up a member variable ViewHolder for just the actual content view
        setUpContentViewHolder(viewHolder.mViewFlipper.getChildAt(viewHolder.contentViewIndex));

        executeRequest(mLoadingStateHelper);
    }

    /**
     * A method that you can call to override how the activity retrieves data. The
     * LoadingStateHelper is passed in only as a utility to change the view's state - it
     * should not be retained by a child implementation.
     *
     * This method is called from inside onResume().
     * @param loadingStateHelper
     */
    protected void executeRequest(LoadingStateHelper loadingStateHelper) {
        startFakeLoadingRequest();
    }

    protected int getContentStateViewId() {
        return R.layout.view_loading_state_content;
    }

    protected void setUpContentViewHolder(View view) {
        // Don't need to use anything from content view later, in this implementation.
        // Other implementation may want to use this area to create a ViewHolder that they
        // can access more easily.
    }



    /**
     * Simulate requests running by entering a loading state, then leaving it, and repeating.
     */
    private void startFakeLoadingRequest() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingStateHelper.showLoading();
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startFakeResultsPause();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }

    /**
     * Throw-away counter used exclusively for loading state "result" example logic. See below.
     */
    private int fakeResultTurn = 0;
    /**
     * Simulate requests running by entering a loading state, then leaving it, and repeating.
     */
    private void startFakeResultsPause() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (fakeResultTurn) {
                    case 0:
                        fakeResultTurn++;
                        mLoadingStateHelper.showContent();
                        break;
                    case 1:
                        fakeResultTurn++;
                        mLoadingStateHelper.showEmpty();
                        break;
                    case 2:
                        fakeResultTurn = 0;
                        mLoadingStateHelper.showError();
                        break;
                }
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startFakeLoadingRequest();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    /**
     * It's good practice to keep logic to a minimum in Activities (and Fragments). This kind of
     * a class can be used to extract some convenience logic out of the Activity and into a separate
     * file. It can also be re-used, then, in other kinds of activities that might use the same
     * patterns.
     */
    public static class LoadingStateHelper {

        private LoadingStateViewHolder mViewHolder;

        public LoadingStateHelper(LoadingStateViewHolder viewHolder) {
            mViewHolder = viewHolder;
        }

        /**
         * Show a progress indicator.
         */
        public void showLoading() {
            mViewHolder.flipView(mViewHolder.loadingViewIndex);
        }

        /**
         * Show an error page.
         */
        public void showError() {
            mViewHolder.flipView(mViewHolder.errorViewIndex);
        }

        /**
         * Show the main content of the page.
         */
        public void showContent() {
            mViewHolder.flipView(mViewHolder.contentViewIndex);
        }

        /**
         * Mostly used for list states, but could also be helpful to present a 404-like
         * page to indicate any request has come back without information (but without
         * an error).
         */
        public void showEmpty() {
            mViewHolder.flipView(mViewHolder.emptyViewIndex);
        }
    }


    /**
     * The ViewHolder pattern is designed to help with memory management and processing time.
     * By having quick, pre-set access to the views you will be touching in the future, it
     * prevents the relatively intensive "findViewById" search through the view hierarchy from
     * running at inconvenient times during user experience flows. And, it ensures all view
     * references are cleaned up at the same time - no need to worry about forgetting to attach
     * and destroy view references in various different lifecycle methods.
     */
    private static class LoadingStateViewHolder {
        private ViewFlipper mViewFlipper;

        public int contentViewIndex, errorViewIndex, loadingViewIndex, emptyViewIndex;

        public LoadingStateViewHolder(View view, int contentViewId) {
            mViewFlipper = (ViewFlipper) view.findViewById(R.id.activity_loading_state_ViewFlipper);

            // For now, only the content view changes from activity to activity, but in the future,
            // this pattern will probably be used to customize the other state views as well.
            View contentView = LayoutInflater.from(view.getContext()).inflate(contentViewId, mViewFlipper, true);
            contentViewIndex = mViewFlipper.getChildCount() - 1; // take the last view that was added to the group

            errorViewIndex = mViewFlipper.indexOfChild(view.findViewById(R.id.activity_loading_state_ErrorView));
            loadingViewIndex = mViewFlipper.indexOfChild(view.findViewById(R.id.activity_loading_state_LoadingView));
            emptyViewIndex = mViewFlipper.indexOfChild(view.findViewById(R.id.activity_loading_state_EmptyView));
        }

        public void flipView(int index) {
            mViewFlipper.setDisplayedChild(index);
        }
    }


}
