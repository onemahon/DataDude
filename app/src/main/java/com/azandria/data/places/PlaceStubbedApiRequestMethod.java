package com.azandria.data.places;

import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class PlaceStubbedApiRequestMethod extends PlaceApiRequestMethod {

    public PlaceStubbedApiRequestMethod(int id) {
        super(id);
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<Place> listener, Collection<IDataRequestFilter> filters) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String response =
                        "[{'id':1,  'map_uri':'geo:42.3583855,-71.0621501?z=14.32', 'name':'Boston, MA'}," +
                        "{'id':2,   'map_uri':'geo:42.3583855,-71.0621501?z=14.32', 'name':'Jamaica'}," +
                        "{'id':3,   'map_uri':'geo:42.3583855,-71.0621501?z=14.32', 'name':'London, UK'}," +
                        "{'id':4,   'map_uri':'geo:42.3583855,-71.0621501?z=14.32', 'name':'San Francisco, CA'}]";
                JSONArray placeArray = null;

                try {
                    placeArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onCompleted(Place.from(placeArray, mId));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }
}
