package com.azandria.datadude.examples.web;

public class Book {
    private String mTitle;

    public Book(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
