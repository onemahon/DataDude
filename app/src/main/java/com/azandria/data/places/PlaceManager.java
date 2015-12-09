package com.azandria.data.places;

import com.azandria.datadude.utils.DataObjectManager;

/**
 * A class that maintains a cache of Place objects that have been
 * used recently in memory. Implementation can vary if you wish, but
 * the default will cache up to the previous 100 Place objects created.
 */
public class PlaceManager extends DataObjectManager<Integer, Place> {

    private static PlaceManager INSTANCE = new PlaceManager();

    /**
     * Retrieve a static singleton to access the in-memory
     * data manager.
     * @return The PlaceManager that caches Place objects.
     */
    public static PlaceManager get() { return INSTANCE; }

    /**
     * A convenient method to return any random cached Place.
     * @return A pseudo-random Place object.
     */
    public Place sample() {
        int totalItems = mCache.size();
        int sampleIndex = (int) (Math.random() * totalItems);

        int counter = 0;
        for (Place place : mCache.snapshot().values()) {
            if (counter == sampleIndex) {
                return place;
            }
            counter++;
        }

        return null;
    }

}
