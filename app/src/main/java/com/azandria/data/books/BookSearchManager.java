package com.azandria.data.books;

import com.azandria.datadude.utils.DataObjectManager;

import java.util.List;

/**
 * A place to hold on to local copies of lists of Books. The
 * String parameter in the DataObjectManager definition
 * indicates that we'll be keying off a String - in the case
 * of our book list example, the most unique ID we have is
 * the search string sent to the API.
 */
public class BookSearchManager extends DataObjectManager<String, List<Book>> {

    private static final BookSearchManager INSTANCE = new BookSearchManager();
    public static BookSearchManager get() { return INSTANCE; }

}
