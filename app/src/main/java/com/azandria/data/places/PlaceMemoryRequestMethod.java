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
        // begin short-circuit
        // This temporary change allows us to not find out in advance of the Place
        // request what ID we're looking for. It has the side effect of there being
        // some potential inconsistency - while the MemoryRequest could scramble
        // the cached places and retrieve ID=3, the API request could scramble and
        // retriev ID=5 (or any other). Beware.
        Place place = PlaceManager.get().sample();
        // end short-circuit

        // Below line actually requests a specific Place object
        // Place place = PlaceManager.get().get(mKey);

        if (place != null) {
            listener.onCompleted(place);
        } else {
            listener.onFailed(-1);
        }
    }
}
