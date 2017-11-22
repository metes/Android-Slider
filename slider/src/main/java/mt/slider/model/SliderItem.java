package mt.slider.model;

import mt.slider.utils.AnimateUtils;

/**
 * Created by Mete on 14.10.2016.
 */

public class SliderItem {

    private String label;
    private int imageResourceID, colorID, sizeSmall, sizeBig;
    private float sizeRaito;

    public SliderItem(String label, int imageResourceID, int colorID, int sizeSmall, int sizeBig) {
        this.label = label;
        this.imageResourceID = imageResourceID;
        this.colorID = colorID;
        this.sizeSmall = sizeSmall;
        this.sizeBig = sizeBig;
        this.sizeRaito = (float)  sizeBig / (float) sizeSmall;
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

    public int getSizeSmall() {
        return sizeSmall;
    }

    public int getSizeBig() {
        return sizeBig;
    }

    public float getSizeRaito() {
        return sizeRaito;
    }
}
