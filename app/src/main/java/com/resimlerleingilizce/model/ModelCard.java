package com.resimlerleingilizce.model;

/**
 * Created by Mete on 25.05.2016.
 */
public class ModelCard {

    String turkish, english, imagePath;
    int id, category;

    public ModelCard(int id, String turkish, String english, String imagePath, int category) {
        this.id = id;
        this.turkish = turkish;
        this.english = english;
        this.category = category;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(byte category) {
        this.category = category;
    }
}
