package com.azandria.datadude.data.methods;


import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.DataRequestFilter;
import com.azandria.datadude.data.DataRequestMethod;
import com.azandria.datadude.data.datatypes.WebData;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class WebDataRequestMethod<DataType> implements DataRequestMethod<DataType> {

    @Override
    public void doRequest(DataRequestBuilder.RequestMethodListener<DataType> listener, Collection<DataRequestFilter> filters) {


        // TODO: send out actual web request and deal with progress, empty, and error states as well
        DataType result = getObject("[\"Brave new world\", \"Brave new world 2\", \"Brave new world 3\", \"Brave new world 4\"]");
        listener.onCompleted(result);


    }

    public abstract DataType getObject(String webResponse);
}
