package com.azandria.datadude.examples.web;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("title")
    private String mTitle;

    @Override
    public String toString() {
        return mTitle;
    }
}
