package com.azandria.datadude.data.methods;


import com.azandria.datadude.data.DataRequestBuilder;
import com.azandria.datadude.data.DataRequestFilter;
import com.azandria.datadude.data.DataRequestMethod;

import java.util.Collection;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class WebDataRequestMethod<DataType> implements DataRequestMethod<DataType> {

    public abstract String getBaseUrl();

    public abstract Call<DataType> createRetrofitRequest(Retrofit retrofit);

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<DataType> listener, Collection<DataRequestFilter> filters) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        createRetrofitRequest(retrofit).enqueue(new Callback<DataType>() {
            @Override
            public void onResponse(Response<DataType> response, Retrofit retrofit) {
                DataType result = response.body();
                listener.onCompleted(result);
            }

            @Override
            public void onFailure(Throwable t) {
                // TODO: correct error handling. Need to check what retrofit returns.
                listener.onFailed(-1);
            }
        });


        // TODO: send out actual web request and deal with progress, empty, and error states as well
//        DataType result = getObject("[\"Brave new world\", \"Brave new world 2\", \"Brave new world 3\", \"Brave new world 4\"]");
    }

//    public abstract DataType getObject(Response<DataType> webResponse);

}
