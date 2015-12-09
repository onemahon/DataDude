package com.azandria.boildroid.visual;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ViewFlipper;

import com.azandria.boildroid.data.IDataRequestMethod;
import com.azandria.boildroid.data.IDataRequestResponse;
import com.raizlabs.coreutils.threading.ThreadingUtils;
import com.whatswhere.R;

public class StateFragment extends Fragment implements StateChanger {

    ///////////
    // region Member Variables and Getters

    private ViewHolder mStatesViewHolder;

    // endregion
    ///////////

    ////////////
    // region commonly overridden method definitions

    // This class has no need for access into any of these state views, but its children might.
    // Give them a chance to create a ViewHolder that they can access more easily later.
    protected void onContentViewInitialized(View view) { }
    protected void onErrorViewInitialized(View view) { }
    protected void onLoadingViewInitialized(View view) { }
    protected void onEmptyViewInitialized(View view) { }

    protected int getContentViewResource() {
        return R.layout.view_state_default_content;
    }

    protected int getErrorViewResource() {
        return R.layout.view_state_default_error;
    }

    protected int getLoadingViewResource() {
        return R.layout.view_state_default_loading;
    }

    protected int getEmptyViewResource() {
        return R.layout.view_state_default_empty;
    }

    // endregion
    ////////////

    ///////////
    // region Lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_switchable, container, false);

        mStatesViewHolder = new ViewHolder(view,
                getErrorViewResource(),
                getLoadingViewResource(),
                getEmptyViewResource(),
                new ViewStub.OnInflateListener() {
                    @Override
                    public void onInflate(ViewStub stub, View inflated) {
                        int inflatedIndex = ((ViewFlipper)inflated.getParent()).indexOfChild(inflated);

                        if (inflatedIndex == mStatesViewHolder.mErrorIndex) {
                            onErrorViewInitialized(inflated);
                        } else if (inflatedIndex == mStatesViewHolder.mLoadingIndex) {
                            onLoadingViewInitialized(inflated);
                        } else if (inflatedIndex == mStatesViewHolder.mEmptyIndex) {
                            onEmptyViewInitialized(inflated);
                        }
                    }
                }
        );

        // add custom content view (it can't be added through ViewStub since
        // the first View in the group is inflated immediately)
        View customContentView = inflater.inflate(
                getContentViewResource(),
                mStatesViewHolder.ContentContainer,
                true
        );
        onContentViewInitialized(customContentView);

        showLoading();

        return view;
    }

    @Override
    public void onDestroyView() {
        mStatesViewHolder.cleanup();
        mStatesViewHolder = null;

        super.onDestroyView();
    }

    // endregion
    ///////////

    ///////////
    // region Interface Implementations

    @Override
    public void showContent() {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mStatesViewHolder.ViewFlipper.setDisplayedChild(mStatesViewHolder.mContentIndex);
            }
        });
    }

    @Override
    public void showLoading() {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mStatesViewHolder.ViewFlipper.setDisplayedChild(mStatesViewHolder.mLoadingIndex);
            }
        });
    }

    @Override
    public void showEmpty() {
        ThreadingUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                mStatesViewHolder.ViewFlipper.setDisplayedChild(mStatesViewHolder.mEmptyIndex);
            }
        });
    }

    @Override
    public void showError(IDataRequestMethod method) {
        // Up to implementation to decide how to disply errors.
        // Cannot use the "error" screen as a default because you
        // likely don't want it flashing by when each and every
        // request fails (like in-memory request that are quickly
        // followed up by web requests, anyway).
    }

    // endregion
    ///////////

    ///////////
    // region Inner Classes

    private static class ViewHolder {
        ViewFlipper ViewFlipper;
        ViewGroup ContentContainer;

        private int mContentIndex;
        private int mErrorIndex;
        private int mLoadingIndex;
        private int mEmptyIndex;

        public ViewHolder(View view,
                          int errorViewResource,
                          int loadingViewResource,
                          int emptyViewResource,
                          ViewStub.OnInflateListener viewStateInflateListener
        ) {

            ViewFlipper = (android.widget.ViewFlipper) view.findViewById(R.id.fragment_state_switchable_Flipper);

            // (The first one is already inflated: we need to inflate it separately, outside of the ViewHolder.)
            ContentContainer = (ViewGroup) ViewFlipper.findViewById(R.id.fragment_state_switchable_ContentView);

            ViewStub errorStub = (ViewStub) ViewFlipper.findViewById(R.id.fragment_state_switchable_ErrorStub);
            ViewStub loadingStub = (ViewStub) ViewFlipper.findViewById(R.id.fragment_state_switchable_LoadingStub);
            ViewStub emptyStub = (ViewStub) ViewFlipper.findViewById(R.id.fragment_state_switchable_EmptyStub);

            errorStub.setLayoutResource(errorViewResource);
            loadingStub.setLayoutResource(loadingViewResource);
            emptyStub.setLayoutResource(emptyViewResource);

            mContentIndex = ViewFlipper.indexOfChild(ContentContainer);
            mErrorIndex = ViewFlipper.indexOfChild(errorStub);
            mLoadingIndex = ViewFlipper.indexOfChild(loadingStub);
            mEmptyIndex = ViewFlipper.indexOfChild(emptyStub);

            /*
             * Below logic checks if the view stubs have been inflated. If they
             * have, it calls back to the inflate listener the caller provided.
             * If they have not, it adds the inflate listener to the viewstub
             * so the caller can set up the newly inflated view *whenever it
             * becomes visible*.
             */

            if (errorStub.getParent() == null) {
                viewStateInflateListener.onInflate(errorStub, ViewFlipper.getChildAt(mErrorIndex));
            } else {
                errorStub.setOnInflateListener(viewStateInflateListener);
            }

            if (loadingStub.getParent() == null) {
                viewStateInflateListener.onInflate(loadingStub, ViewFlipper.getChildAt(mLoadingIndex));
            } else {
                loadingStub.setOnInflateListener(viewStateInflateListener);
            }

            if (emptyStub.getParent() == null) {
                viewStateInflateListener.onInflate(emptyStub, ViewFlipper.getChildAt(mEmptyIndex));
            } else {
                emptyStub.setOnInflateListener(viewStateInflateListener);
            }
        }

        void cleanup() {
            /*
             * No cleanup required, AS LONG AS the OnInflateListeners
             * that got attached to the ViewStubs *do not get added to
             * any singletons outside the context of this Fragment* (which
             * they shouldn't, anyway. So don't do that.
             */
        }
    }

    public static abstract class StateRequestResponse<Type> implements IDataRequestResponse<Type> {

        public abstract StateChanger getStateChanger();

        @Override
        public void onCompleted(IDataRequestMethod method, Type type) {
            getStateChanger().showContent();
        }

        @Override
        public void onError(IDataRequestMethod method, int errorCode) {
            getStateChanger().showError(method);
        }

        @Override
        public void onTimeout(IDataRequestMethod method) {
            getStateChanger().showError(method);
        }

        @Override
        public void onCancelled(IDataRequestMethod method) {
            getStateChanger().showError(method);
        }

        @Override
        public void onBegun(IDataRequestMethod method) {
            // Optional: you can choose to showLoading() here if
            // the loading state is non-intrusive.
            // Note that this will be called every time a new method
            // begins, even while in the same sequence of requests.
        }

        @Override
        public void onQueued(IDataRequestMethod method) {
            // No state change required.
        }
    }

    // endregion
    ///////////
}
