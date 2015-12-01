package com.azandria.datadude.examples.web;

import com.raizlabs.webservicemanager.HttpMethod;
import com.raizlabs.webservicemanager.requests.BaseWebServiceRequest;
import com.raizlabs.webservicemanager.requests.RequestBuilder;
import com.raizlabs.webservicemanager.responses.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

public class BookListApiRequest extends BaseWebServiceRequest<List<Book>> {

    private String mSearchString;

    public BookListApiRequest(String searchString) {
        mSearchString = searchString;
    }

    @Override
    protected RequestBuilder getRequestBuilder() {
        String searchStringEncoded = null;

        try {
            searchStringEncoded = URLEncoder.encode(mSearchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new RequestBuilder(HttpMethod.Get, "http://azandria.com/api/v1/search/books?search_term="+searchStringEncoded);
    }

    @Override
    protected List<Book> translate(Response response) {
        JSONArray json = response.getContentAsJSONArray();

        // Feel free to translate the JSON into a list of books using whatever method you'd prefer.
        // Gson is a good example of a library that could help with that.

        LinkedList<Book> books = new LinkedList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject bookJson = json.getJSONObject(i);
                Book book = new Book(bookJson.getString("title"));
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }

}
