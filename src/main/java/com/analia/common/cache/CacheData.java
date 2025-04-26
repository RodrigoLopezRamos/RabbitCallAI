package com.analia.common.cache;

import java.util.List;

/**
 * @param <T>
 * @author Rodrigo Lopez
 */
public class CacheData<T> {
    private List<T> data;
    private int version;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
