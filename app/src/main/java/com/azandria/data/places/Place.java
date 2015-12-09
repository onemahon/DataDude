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

    public int mId;

    public String mName;
    public String mImageUrl;

    public Uri mMapUri;

    public String mWikipediaUrl;
    public String mWikipediaContent;

    public String mTripAdvisorUrl;
    public String mTripAdvisorContent;

    public static Place from(final JSONObject json) {
        // Check the cache, & allow it to create the object internally if it doesn't already exist
        return PlaceManager.get().get(json.optInt("id"), new DataObjectManager.FindDelegate<Place>() {
            @Override
            public Place need() {
                Place place = new Place();
                place.mId = json.optInt("id");

                place.mName = json.optString("name");
                place.mImageUrl = json.optString("image_url");

                place.mWikipediaContent = json.optString("wiki_content");
                place.mWikipediaUrl = json.optString("wikipedia_url");

                place.mTripAdvisorContent = json.optString("tripadvisor_content");
                place.mTripAdvisorUrl = json.optString("tripadvisor_url");

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

                // start short-circuit
                // This temporary short-circuit is just to ensure we don't have to know the IDs
                // of the requested Place objects in advance of requesting a single sample.
                int sampleIndex = (int) (Math.random() * array.length() + 1);
                JSONObject object = array.getJSONObject(sampleIndex);
                result = Place.from(object);
                // end short-circuit

                // Below logic actually uses the KEY rather than the numeric index in the array to find a Place object.
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject json = array.getJSONObject(i);
//                    if (json.optInt("id") == placeKey) {
//                        result = Place.from(json);
//                    }
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
