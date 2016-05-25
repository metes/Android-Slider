package com.resimlerleingilizce.singletons;

import com.resimlerleingilizce.model.ModelCard;

/**
 * Created by Mete on 25.05.2016.
 */
public class SingletonJSON {
    private static SingletonJSON mInstance = null;

    private ModelCard[] mModelCards;

    private SingletonJSON(){
//        mModelCards = "Hello";
    }

    public static SingletonJSON getInstance(){
        if(mInstance == null)
        {
            mInstance = new SingletonJSON();
        }
        return mInstance;
    }

    public ModelCard[] getData(){
        return this.mModelCards;
    }

    public void setData(ModelCard[] value){
        mModelCards = value;
    }
}
