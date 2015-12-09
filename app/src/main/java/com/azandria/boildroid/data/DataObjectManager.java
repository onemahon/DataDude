package com.azandria.boildroid.data;

import android.util.LruCache;

public abstract class DataObjectManager<UniqueIdType, DataType> {

    protected LruCache<UniqueIdType, DataType> mCache;

    protected DataObjectManager() {
        // Default: up to 100 items. To restrict by size, instead, use:
        // new LruCache<>(100) { @Override protected int sizeOf(Object key, Object value) { ... } };
        mCache = new LruCache<>(100);
    }

    /**
     * Returns the object, if it exists in the cache, or null.
     * If you'd like to ensure the object is added to the cache
     * if it doesn't already exist, use get(key, listener).
     * @param key
     * @return
     */
    public DataType get(UniqueIdType key) {
        return mCache.get(key);
    }

    public DataType get(UniqueIdType key, FindDelegate<DataType> delegate) {
        DataType data = mCache.get(key);
        if (data == null) {
            data = delegate.need();
            store(key, data);
        }
        return data;
    }

    public void store(UniqueIdType id, DataType data) {
        mCache.put(id, data);
    }

    public interface FindDelegate<DataType> {
        DataType need();
    }

}
