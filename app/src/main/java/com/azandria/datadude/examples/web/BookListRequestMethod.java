package com.azandria.datadude.examples.web;

import com.azandria.datadude.data.BasicWebServiceManager;
import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.IDataRequestFilter;
import com.azandria.datadude.data.IDataRequestMethod;
import com.raizlabs.webservicemanager.webservicemanager.ResultInfo;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceManager;
import com.raizlabs.webservicemanager.webservicemanager.WebServiceRequestListener;

import java.util.Collection;
import java.util.List;

public class BookListRequestMethod implements IDataRequestMethod<List<Book>> {

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return true;
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<List<Book>> listener, Collection<IDataRequestFilter> filters) {
        BasicWebServiceManager.get().doRequestInBackground(new BookListApiRequest("Brave New World"), new WebServiceRequestListener<List<Book>>() {
            @Override
            public void onRequestComplete(WebServiceManager webServiceManager, ResultInfo<List<Book>> resultInfo) {
                if (resultInfo.isStatusOK()) {
                    listener.onCompleted(resultInfo.getResult());
                } else {
                    listener.onFailed(resultInfo.getResponseCode());
                }
            }
        });
    }
}
