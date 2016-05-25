package com.resimlerleingilizce.model;

/**
 * Created by Mete on 25.05.2016.
 */
public class ModelCard {

    String turkish, english;
    int id;
    byte category;

    public ModelCard(String turkish, String english, int id, byte category) {
        this.turkish = turkish;
        this.english = english;
        this.id = id;
        this.category = category;
    }

    public String getTurkish() {

        return turkish;
    }

    public void setTurkish(String turkish) {
        this.turkish = turkish;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getCategory() {
        return category;
    }

    public void setCategory(byte category) {
        this.category = category;
    }
}
