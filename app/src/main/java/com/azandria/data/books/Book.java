package com.azandria.data.books;

import com.azandria.datadude.utils.DataObjectManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Book {
    private String mTitle;

    public Book(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public static Book from(JSONObject bookJson) {
        final String title = bookJson.optString("title");

        Book book = BookManager.get().get(title, new DataObjectManager.FindDelegate<Book>() {
            @Override
            public Book need() {
                // This is where the object is created, in case
                // it's not in the cache.
                return new Book(title);
            }
        });

        return book;
    }

    public static List<Book> fromList(JSONArray json) {
        // Feel free to translate the JSON into a list of books using whatever method you'd prefer.
        // Gson is a good example of a library that could help with that.

        LinkedList<Book> books = new LinkedList<>();

        try {
            for (int i = 0; i < json.length() - 1; i++) {
                JSONObject bookJson = json.getJSONObject(i);
                Book book = Book.from(bookJson);
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }
}
