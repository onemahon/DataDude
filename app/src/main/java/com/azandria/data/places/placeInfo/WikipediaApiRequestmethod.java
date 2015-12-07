package com.azandria.data.places.placeInfo;

import com.azandria.data.places.Place;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;
import com.azandria.datadude.utils.IDataRequestMethod;

import java.util.Collection;

public class WikipediaApiRequestMethod implements IDataRequestMethod<WikipediaInformation> {

    private Place mPlace;

    public WikipediaApiRequestMethod(Place place) {
        mPlace = place;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return false;
    }

    @Override
    public void doRequest(DataRequestBuilder.RequestMethodListener<WikipediaInformation> listener, Collection<IDataRequestFilter> filters) {
        // TODO stub out
    }
}
