package com.azandria.datadude.examples;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

import com.azandria.datadude.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingStatesActivity extends Activity {

    private LoadingStateHelper mLoadingStatesHelper;

    // region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_states);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set up the LoadingStateHelper so we can change the view's state
        // later, while processing requests.
        View view = findViewById(R.id.activity_loading_states_ViewFlipper);
        LoadingStatesViewHolder viewHolder = new LoadingStatesViewHolder(view, getContentStateViewId(),
                getEmptyStateViewId(), getErrorStateViewId(), getLoadingStateViewId());
        mLoadingStatesHelper = new LoadingStateHelper(viewHolder);

        // Give any implementations a chance to set up a member variable ViewHolder for just the actual content view
        setUpContentViewHolder(viewHolder.mViewFlipper.getChildAt(viewHolder.contentViewIndex));
        setUpEmptyViewHolder(viewHolder.mViewFlipper.getChildAt(viewHolder.emptyViewIndex));
        setUpErrorViewHolder(viewHolder.mViewFlipper.getChildAt(viewHolder.errorViewIndex));
        setUpLoadingViewHolder(viewHolder.mViewFlipper.getChildAt(viewHolder.loadingViewIndex));

        executeRequest();
    }

    // endregion

    // region Protected Methods (available to implementing classes)

    /**
     * A method that you can call to override how the activity retrieves data.
     *
     * This method is called from inside onResume().
     */
    protected void executeRequest() {
        showLoading();
        startFakeLoadingRequest();
    }

    protected int getContentStateViewId() {
        return R.layout.view_loading_state_content;
    }

    protected int getEmptyStateViewId() {
        return R.layout.view_loading_state_empty;
    }

    protected int getLoadingStateViewId() {
        return R.layout.view_loading_state_loading;
    }

    protected int getErrorStateViewId() {
        return R.layout.view_loading_state_error;
    }

    // This class has no need for access into any of these state views, but its children might.
    // Give them a chance to create a ViewHolder that they can access more easily later.
    protected void setUpContentViewHolder(View view) { }
    protected void setUpEmptyViewHolder(View view) { }
    protected void setUpErrorViewHolder(View view) { }
    protected void setUpLoadingViewHolder(View view) { }

    protected void showContent() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingStatesHelper != null) {
                    mLoadingStatesHelper.showContent();
                }
            }
        });
    }

    protected void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingStatesHelper != null) {
                    mLoadingStatesHelper.showLoading();
                }
            }
        });
    }

    protected void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingStatesHelper != null) {
                    mLoadingStatesHelper.showError();
                }
            }
        });
    }

    protected void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingStatesHelper != null) {
                    mLoadingStatesHelper.showEmpty();
                }
            }
        });
    }

    // endregion

    // region Throwaway Code

    /**
     * Simulate requests running by entering a loading state, then leaving it, and repeating.
     */
    private void startFakeLoadingRequest() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoading();
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
                        showContent();
                        break;
                    case 1:
                        fakeResultTurn++;
                        showEmpty();
                        break;
                    case 2:
                        fakeResultTurn = 0;
                        showError();
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

    // endregion

    /**
     * It's good practice to keep logic to a minimum in Activities (and Fragments). This kind of
     * a class can be used to extract some convenience logic out of the Activity and into a separate
     * file. It can also be re-used, then, in other kinds of activities that might use the same
     * patterns.
     */
    public static class LoadingStateHelper {

        private LoadingStatesViewHolder mViewHolder;

        public LoadingStateHelper(LoadingStatesViewHolder viewHolder) {
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
    private static class LoadingStatesViewHolder {
        private ViewFlipper mViewFlipper;

        public int contentViewIndex, errorViewIndex, loadingViewIndex, emptyViewIndex;

        public LoadingStatesViewHolder(View view, int contentViewId, int emptyViewId,
                                       int errorViewId, int loadingViewId) {

            mViewFlipper = (ViewFlipper) view.findViewById(R.id.activity_loading_states_ViewFlipper);

            // TODO: child views should really be ViewStubs and inflated only as needed

            LayoutInflater.from(view.getContext()).inflate(loadingViewId, mViewFlipper, true);
            loadingViewIndex = mViewFlipper.getChildCount() - 1; // take the last view that was added to the group
            LayoutInflater.from(view.getContext()).inflate(emptyViewId, mViewFlipper, true);
            emptyViewIndex = mViewFlipper.getChildCount() - 1; // take the last view that was added to the group
            LayoutInflater.from(view.getContext()).inflate(errorViewId, mViewFlipper, true);
            errorViewIndex = mViewFlipper.getChildCount() - 1; // take the last view that was added to the group
            LayoutInflater.from(view.getContext()).inflate(contentViewId, mViewFlipper, true);
            contentViewIndex = mViewFlipper.getChildCount() - 1; // take the last view that was added to the group
        }

        public void flipView(int index) {
            mViewFlipper.setDisplayedChild(index);
        }
    }
}
