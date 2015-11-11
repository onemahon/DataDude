package com.azandria.datadude.data;

/**
 * Created by joe on 11/10/15.
 */
public interface DataRequestFilter {

    /*
    Can create custom implementations that select what kind of objects you want returned in your final list

     Advanced: pass these filters into the DataRequestMethods provided in the DataRequestBuilder so the requests themselves can take them into account
     (e.g. only select relevant data from database to prevent having to filter once in-memory, or being able to use filter to decide what API parameters to send on an Async web call)

     */

}
