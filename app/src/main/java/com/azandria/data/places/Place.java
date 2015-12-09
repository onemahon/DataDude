package com.azandria.data.places;

import android.net.Uri;

import com.azandria.datadude.utils.DataObjectManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An object representing a particular place in the world.
 */
public class Place {

    public String mName;
    public Uri mMapUri;


    public static Place from(final JSONObject json) {
        // Check the cache, & allow it to create the object internally if it doesn't already exist
        return PlaceManager.get().get(json.optInt("id"), new DataObjectManager.FindDelegate<Place>() {
            @Override
            public Place need() {
                Place place = new Place();

                place.mName = json.optString("name");

                String mapUri = json.optString("map_uri");
                if (mapUri != null) {
                    place.mMapUri = Uri.parse(mapUri);
                }

                return place;
            }
        });
    }

    public static Place from(JSONArray array, int placeKey) {
        Place result = null;

        if (array != null) {
            try {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    if (json.optInt("id") == placeKey) {
                        result = Place.from(json);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
