package com.azandria.data.books;

import com.azandria.datadude.utils.DataObjectManager;

/**
 * A place for Books to be kept around and fetched at
 * need from memory.
 *
 * The String parameter in the DataObjectManager definition
 * indicates we'll be keying off a string identifier, in
 * the case of our examples, the book's title should suffice
 * (since that's the only information we're actually using
 * about any book).
 */
public class BookManager extends DataObjectManager<String, Book> {

    private static BookManager INSTANCE = new BookManager();
    public static BookManager get() { return INSTANCE; }

}
