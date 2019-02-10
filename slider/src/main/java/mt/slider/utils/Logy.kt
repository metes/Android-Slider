package mt.slider.utils

import android.util.Log


/**
 * Created by Mete on 18.04.2016.
 */
object Logy {
    /**
     * @param clazz l class
     * @param log l String
     */
    fun l(clazz: Class<*>, log: String) {
        if (mt.slider.BuildConfig.DEBUG) {
            Log.d(clazz.simpleName, log)
        }
    }

    fun l(log: String) {
        if (mt.slider.BuildConfig.DEBUG) {
            Log.d("mt.slider", log)
        }
    }

    /**
     * @param clazz l class
     * @param log l String
     * @param logLevel 0 debug, 1 info, 2 warning, 3 error
     */
    fun l(clazz: Class<*>, log: String, logLevel: Int) {
        if (mt.slider.BuildConfig.DEBUG) {
            when (logLevel) {
                0 -> Log.d(clazz.simpleName, log)
                1 -> Log.i(clazz.simpleName, log)
                2 -> Log.w(clazz.simpleName, log)
                else -> Log.e(clazz.simpleName, log)
            }
        }
    }

}
