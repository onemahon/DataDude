package com.whatswhere.places.dataRequests;

import com.whatswhere.places.Place;
import com.raizlabs.webservicemanager.HttpMethod;
import com.raizlabs.webservicemanager.requests.BaseWebServiceRequest;
import com.raizlabs.webservicemanager.requests.RequestBuilder;
import com.raizlabs.webservicemanager.responses.Response;

import org.json.JSONArray;

public class PlaceApiRequest extends BaseWebServiceRequest<Place> {

    private static final String HOSTED_PLACE_JSON_URL = "https://raw.githubusercontent.com/onemahon/Hosted/places-api/places.json";

    private int mId;

    public PlaceApiRequest(int id) {
        mId = id;
    }

    @Override
    protected RequestBuilder getRequestBuilder() {
        // TODO correct URL & testing
        return new RequestBuilder(HttpMethod.Get, HOSTED_PLACE_JSON_URL);
    }

    @Override
    protected Place translate(Response response) {
        Place result = null;

        if (response != null) {
            JSONArray array = response.getContentAsJSONArray();
            result = Place.from(array, mId);
        }

        return result;
    }
}
