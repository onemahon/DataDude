package com.azandria.data.places;

import com.azandria.datadude.utils.BasicWebServiceManager;
import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;
import com.azandria.datadude.utils.IDataRequestMethod;
import com.raizlabs.webservicemanager.webservicemanager.ResultInfo;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceManager;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceRequestListener;

import java.util.Collection;

public class PlaceApiRequestMethod implements IDataRequestMethod<Place> {

    protected int mId;

    public PlaceApiRequestMethod(int id) {
        mId = id;
    }

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return false;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<Place> listener, Collection<IDataRequestFilter> filters) {
        BasicWebServiceManager.get().doRequestInBackground(new PlaceApiRequest(mId), new WebServiceRequestListener<Place>() {
            @Override
            public void onRequestComplete(WebServiceManager webServiceManager, ResultInfo<Place> resultInfo) {
                if (resultInfo.isStatusOK()) {
                    listener.onCompleted(resultInfo.getResult());
                    PlaceManager.get().store(mId, resultInfo.getResult());
                } else {
                    listener.onFailed(resultInfo.getResponseCode());
                }
            }
        });
    }
}
