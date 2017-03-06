package mt.slider.model;

/**
 * Created by Mete on 14.10.2016.
 */

public class ModelSliderItem {

    private String label;
    private int imageResourceID, colorID;

    public ModelSliderItem(String label, int imageResourceID, int colorID) {
        this.label = label;
        this.imageResourceID = imageResourceID;
        this.colorID = colorID;
    }

    public String getLabel() {
        return label;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public int getColorID() {
        return colorID;
    }
}
