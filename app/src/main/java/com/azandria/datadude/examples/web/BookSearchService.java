package com.azandria.datadude.examples.web;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface BookSearchService {
    @GET("/api/v1/search/books")
    Call<List<Book>> getSearchResults(
            @Query("search_term") String searchQuery
    );
}
