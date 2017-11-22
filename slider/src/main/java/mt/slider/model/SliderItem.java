package mt.slider.model;

import mt.slider.utils.AnimateUtils;

/**
 * Created by Mete on 14.10.2016.
 */

public class SliderItem {

//    AnimateUtils.mAnimationTranslateXPosition = width / 3.8f;
//    AnimateUtils.mAnimationScaleSizeMultipler = 1.29230769f;

    private String label;
    private int imageResourceID, colorID, sizeSmall, sizeBig;
    private float sizeRaito;

    public SliderItem(String label, int imageResourceID, int colorID, int sizeSmall, int sizeBig) {
        this.label = label;
        this.imageResourceID = imageResourceID;
        this.colorID = colorID;
        this.sizeSmall = sizeSmall;
        this.sizeBig = sizeBig;
        this.sizeRaito = (float) sizeBig / (float) sizeSmall;
    }

    public SliderItem(String label, int imageResourceID, int colorID, int sizeSmall, int sizeBig, float raito) {
        this.label = label;
        this.imageResourceID = imageResourceID;
        this.colorID = colorID;
        this.sizeSmall = sizeSmall;
        this.sizeBig = sizeBig;
        this.sizeRaito = raito;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public int getSizeSmall() {
        return sizeSmall;
    }

    public void setSizeSmall(int sizeSmall) {
        this.sizeSmall = sizeSmall;
    }

    public int getSizeBig() {
        return sizeBig;
    }

    public void setSizeBig(int sizeBig) {
        this.sizeBig = sizeBig;
    }

    public float getSizeRaito() {
        return sizeRaito;
    }

    public void setSizeRaito(float sizeRaito) {
        this.sizeRaito = sizeRaito;
    }
}
