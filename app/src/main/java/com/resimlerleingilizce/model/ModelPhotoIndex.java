package com.resimlerleingilizce.model;

/**
 * Created by Mete on 1.06.2016.
 */
public class ModelPhotoIndex {
    int index;
    boolean isLoaded;

    public ModelPhotoIndex(int index, boolean isLoaded) {
        this.index = index;
        this.isLoaded = isLoaded;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}
