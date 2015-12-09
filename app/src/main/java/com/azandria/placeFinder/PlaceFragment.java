package com.azandria.placeFinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.azandria.data.places.IntentManager;
import com.azandria.data.places.Place;
import com.azandria.data.places.PlaceApiRequestMethod;
import com.azandria.data.places.PlaceMemoryRequestMethod;
import com.azandria.datadude.R;
import com.azandria.datadude.utils.BasicDataRequestResponse;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.azandria.datadude.utils.IDataRequestResponse;
import com.raizlabs.coreutils.threading.ThreadingUtils;
import com.squareup.picasso.Picasso;

public class PlaceFragment extends Fragment {

    ///////////
    // region Factory Initializers

    public static PlaceFragment newInstance() {
        return new PlaceFragment();
    }

    // endregion
    ///////////

    ///////////
    // region Member Variables

    private ViewHolder mViewHolder;
    private Place mPlace;

    private IDataRequestResponse<Place> mPlaceResponseListener = new BasicDataRequestResponse<Place>() {
        @Override
        public void onCompleted(IDataRequestMethod method, Place place) {
            super.onCompleted(method, place);

            mPlace = place;

            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (mPlace != null) {
                        updateCards();
                    }
                }
            });
        }
    };

    // endregion
    ///////////

    ///////////
    // region Lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        mViewHolder = new ViewHolder(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchNewPlace();
        addClickListeners();
    }

    @Override
    public void onDestroyView() {
        mViewHolder = null;
        super.onDestroyView();
    }

    // endregion
    ///////////

    ///////////
    // region Private Methods

    private void fetchNewPlace() {
        // This ID not currently used. There are very few cases for retrieving
        // a random object in production code, but if there was a good reason
        // for it, that should be the API's responsibility.
        // On the other hand, this request would work quite well if you're trying
        // to fetch a specific object based on an ID that you already have (pretty
        // common use case - e.g. a "details" page for a listed item).
        // Note: since -1 is not a valid ID, and will never have an object cached
        // in memory, the PlaceMemoryRequestMethod will be effectively unused while
        // this debugging setup exists.
        int randomPlaceId = -1;

        new DataRequestBuilder<>(mPlaceResponseListener)
                .addRequestMethod(new PlaceMemoryRequestMethod(randomPlaceId))
                .addRequestMethod(new PlaceApiRequestMethod(randomPlaceId))
//                .addRequestMethod(new PlaceStubbedApiRequestMethod(randomPlaceId))
                .execute();
    }

    private void updateCards() {
        if (mPlace != null) {
            mViewHolder.Toolbar.setTitle(mPlace.mName);

            if (!TextUtils.isEmpty(mPlace.mImageUrl)) {
                Picasso.with(getContext()).load(mPlace.mImageUrl).into(mViewHolder.ToolbarImage);
            }

            mViewHolder.WikipediaCard.setContent(mPlace.mWikipediaContent);
            mViewHolder.TripAdvisorCard.setContent(mPlace.mTripAdvisorContent);
        }
    }

    private void addClickListeners() {
        if (mViewHolder != null) {
            mViewHolder.MapsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentManager.startMapIntent(mPlace, v.getContext(), v);
                }
            });

            mViewHolder.HopperCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentManager.startHopperIntent(v.getContext(), v);
                }
            });

            mViewHolder.WikipediaCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentManager.startUrlIntent(mPlace.mWikipediaUrl, v.getContext());
                }
            });

            mViewHolder.TripAdvisorCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentManager.startUrlIntent(mPlace.mTripAdvisorUrl, v.getContext());
                }
            });

            mViewHolder.RefreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchNewPlace();
                }
            });
        }
    }

    // endregion
    ///////////


    ///////////
    // region Inner Classes

    private static class ViewHolder {
        private ViewGroup Container;

        // TODO add to toolbar instead
        private CollapsingToolbarLayout Toolbar;
        private ImageView ToolbarImage;

        private ImageCard WikipediaCard;
        private ImageCard MapsCard;
        private ImageCard TripAdvisorCard;
        private ImageCard HopperCard;

        private View RefreshButton;

        public ViewHolder(View view) {
            Container = (ViewGroup) view.findViewById(R.id.fragment_place_Container);

            Toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.fragment_place_Toolbar);
            ToolbarImage = (ImageView) view.findViewById(R.id.fragment_place_ToolbarImage);

            WikipediaCard = (ImageCard) view.findViewById(R.id.fragment_place_WikipediaCard);
            MapsCard = (ImageCard) view.findViewById(R.id.fragment_place_MapsCard);
            TripAdvisorCard = (ImageCard) view.findViewById(R.id.fragment_place_TripAdvisorCard);
            HopperCard = (ImageCard) view.findViewById(R.id.fragment_place_HopperCard);

            RefreshButton = view.findViewById(R.id.fragment_place_RefreshButton);
        }
    }

    // endregion
    ///////////
}
