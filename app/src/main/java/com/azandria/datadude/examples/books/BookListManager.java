package com.azandria.datadude.examples.books;

import com.azandria.datadude.data.DataObjectManager;

import java.util.List;

/**
 * A place to hold on to local copies of lists of Books. The
 * String parameter in the DataObjectManager definition
 * indicates that we'll be keying off a String - in the case
 * of our book list example, the most unique ID we have is
 * the search string sent to the API.
 */
public class BookListManager extends DataObjectManager<String, List<Book>> {

    private static final BookListManager INSTANCE = new BookListManager();
    public static BookListManager get() { return INSTANCE; }

}
