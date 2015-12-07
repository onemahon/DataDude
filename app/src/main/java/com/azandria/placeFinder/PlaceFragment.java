package com.azandria.placeFinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.azandria.data.places.Place;
import com.azandria.data.places.PlaceApiRequestMethod;
import com.azandria.data.places.PlaceMemoryRequestMethod;
import com.azandria.data.places.placeInfo.TripAdvisorApiRequestMethod;
import com.azandria.data.places.placeInfo.TripAdvisorInformation;
import com.azandria.data.places.placeInfo.WikipediaApiRequestMethod;
import com.azandria.data.places.placeInfo.WikipediaInformation;
import com.azandria.datadude.R;
import com.azandria.datadude.utils.BasicDataRequestResponse;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.azandria.datadude.utils.IDataRequestResponse;
import com.azandria.placeFinder.cards.MapsCard;
import com.raizlabs.coreutils.threading.ThreadingUtils;

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
    private WikipediaInformation mWikipediaInformation;
    private TripAdvisorInformation mTripAdvisorInformation;

    // TEMP: just for testing.
    private int randomPlaceId;

    private IDataRequestResponse<Place> mPlaceResponseListener = new BasicDataRequestResponse<Place>() {
        @Override
        public void onCompleted(IDataRequestMethod method, Place place) {
            super.onCompleted(method, place);

            mPlace = place;

            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (mPlace != null) {
                        requestAndFillInCardInformation();
                    } else {
                        Toast.makeText(getActivity(), "Sorry, no place found for ID " + randomPlaceId, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private IDataRequestResponse<WikipediaInformation> mWikipediaResponseListener = new BasicDataRequestResponse<WikipediaInformation>() {
        @Override
        public void onCompleted(IDataRequestMethod method, WikipediaInformation wikipediaInformation) {
            super.onCompleted(method, wikipediaInformation);
            mWikipediaInformation = wikipediaInformation;

            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    fillInWikipediaCard();
                }
            });
        }
    };

    private IDataRequestResponse<TripAdvisorInformation> mTripadvisorResponseListener = new BasicDataRequestResponse<TripAdvisorInformation>() {
        @Override
        public void onCompleted(IDataRequestMethod method, TripAdvisorInformation tripAdvisorInformation) {
            super.onCompleted(method, tripAdvisorInformation);
            mTripAdvisorInformation = tripAdvisorInformation;

            ThreadingUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    fillInTripAdvisorCard();
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

        randomPlaceId = (int) (Math.random() * 4) + 1;  // TODO random item index (hard-coded, for testing)

        // TODO delegate this into a button (e.g. "refresh" or "try new place")
        new DataRequestBuilder<>(mPlaceResponseListener)
                .addRequestMethod(new PlaceMemoryRequestMethod(randomPlaceId))
                .addRequestMethod(new PlaceApiRequestMethod(randomPlaceId))
//                .addRequestMethod(new PlaceStubbedApiRequestMethod(randomPlaceId))
                .execute();

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

    private void requestAndFillInCardInformation() {
        fillInLocalCardInfo();

        new DataRequestBuilder<>(mWikipediaResponseListener)
                .addRequestMethod(new WikipediaApiRequestMethod(mPlace))
                .execute();

        new DataRequestBuilder<>(mTripadvisorResponseListener)
                .addRequestMethod(new TripAdvisorApiRequestMethod(mPlace))
                .execute();
    }

    private void fillInLocalCardInfo() {
        // TODO update Maps link
        // TODO update Hopper Intent
        // (or, alternately, have those both just generated on click)
        if (mViewHolder != null) {
            mViewHolder.MapsCard.setTitle(mPlace.mName);
        }
    }

    private void fillInWikipediaCard() {
        if (mViewHolder != null) {
            // mViewHolder.WikipediaCard
        }
    }

    private void fillInTripAdvisorCard() {
        if (mViewHolder != null) {
            // mViewHolder.TripAdvisorCard
        }
    }

    private void addClickListeners() {
        if (mViewHolder != null) {
            mViewHolder.WikipediaCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO go to web view with wikipedia information
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

        private View WikipediaCard;
        private MapsCard MapsCard;
        private View TripAdvisorCard;
        private View HopperCard;

        public ViewHolder(View view) {
            Container = (ViewGroup) view.findViewById(R.id.fragment_place_Container);

            WikipediaCard = view.findViewById(R.id.fragment_place_WikipediaCard);
            MapsCard = (MapsCard) view.findViewById(R.id.fragment_place_MapsCard);
            TripAdvisorCard = view.findViewById(R.id.fragment_place_TripAdvisorCard);
            HopperCard = view.findViewById(R.id.fragment_place_HopperCard);
        }
    }

    // endregion
    ///////////
}
