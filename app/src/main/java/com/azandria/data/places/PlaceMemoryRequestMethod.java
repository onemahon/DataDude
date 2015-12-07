package com.azandria.data.places;

import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;
import com.azandria.datadude.utils.IDataRequestMethod;

import java.util.Collection;

/**
 * An implementation of IDataRequestMethod that accesses the
 * in-memory Place cache to attempt to quickly return an
 * existing Place object. Ingested by DataRequestBuilders.
 */
public class PlaceMemoryRequestMethod implements IDataRequestMethod<Place> {

    private int mKey;

    public PlaceMemoryRequestMethod(int key) {
        mKey = key;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return false;
    }

    @Override
    public void doRequest(DataRequestBuilder.RequestMethodListener<Place> listener, Collection<IDataRequestFilter> filters) {
        Place place = PlaceManager.get().get(mKey);

        if (place != null) {
            listener.onCompleted(place);
        } else {
            listener.onFailed(-1);
        }
    }
}
