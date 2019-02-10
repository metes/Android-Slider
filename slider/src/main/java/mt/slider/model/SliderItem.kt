package mt.slider.model


/**
 * Created by Mete on 14.10.2016.
 */

class SliderItem(val label: String, val imageResourceID: Int, val colorID: Int, val sizeSmall: Int, val sizeBig: Int) {
    val sizeRatio: Float = sizeBig.toFloat() / sizeSmall.toFloat()

}
