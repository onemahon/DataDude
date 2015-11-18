package com.azandria.datadude.examples.web;

import com.azandria.datadude.data.methods.RetrofitDataRequestMethod;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;

public class BookListRetrofitRequest extends RetrofitDataRequestMethod<List<Book>> {

    @Override
    public boolean makeRequestDespitePreviousSuccess() {
        return true;
    }

    @Override
    public String getBaseUrl() {
        return "http://azandria.com/api/v1/search";
    }

    @Override
    public Call<List<Book>> createRetrofitRequest(Retrofit retrofit) {
        return retrofit.create(BookSearchService.class).getSearchResults("brave new world");
    }
}

