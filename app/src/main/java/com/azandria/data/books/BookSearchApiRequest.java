package com.azandria.data.books;

import com.raizlabs.webservicemanager.HttpMethod;
import com.raizlabs.webservicemanager.requests.BaseWebServiceRequest;
import com.raizlabs.webservicemanager.requests.RequestBuilder;
import com.raizlabs.webservicemanager.responses.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BookSearchApiRequest extends BaseWebServiceRequest<List<Book>> {

    private String mSearchString;

    public BookSearchApiRequest(String searchString) {
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
        return Book.fromList(response.getContentAsJSONArray());
    }
}
