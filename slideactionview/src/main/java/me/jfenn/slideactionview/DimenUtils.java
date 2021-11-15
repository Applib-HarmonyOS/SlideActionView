package me.jfenn.slideactionview;

import ohos.app.Context;

/**
 * DimenUtils class.
 */
public class DimenUtils {

    private DimenUtils() {

    }

    /**
     * Converts dp units to pixels.
     *
     * @param context       context value.
     * @param dp            A distance measurement, in dp.
     * @return              The value of the provided dp units, in pixels.
     */
    public static int dpToPx(Context context, float dp) {
        return (int) (context.getResourceManager().getDeviceCapability().screenDensity / 160 * dp);
    }

    /**
     * Converts pixels to dp.
     *
     * @param context       context value.
     * @param pixels        A distance measurement, in pixels.
     * @return              The value of the provided pixel units, in dp.
     */
    public static float pxToDp(Context context, int pixels) {
        return pixels / (context.getResourceManager().getDeviceCapability().screenDensity / 160);
    }

    /**
     * Converts sp to pixels.
     *
     * @param context       context value.
     * @param sp            A distance measurement, in sp.
     * @return The value of the provided sp units, in pixels.
     */
    public static int spToPx(Context context, float sp) {
        return (int) ((ohos.agp.window.service.DisplayManager
                .getInstance().getDefaultDisplay(context).get().getAttributes().scalDensity) * sp);
    }

    /**
     * Converts pixels to sp.
     *
     * @param context       context value.
     * @param pixels        A distance measurement, in pixels.
     * @return              The value of the provided pixel units, in sp.
     */
    public static float pxToSp(Context context, int pixels) {
        return pixels / (ohos.agp.window.service.DisplayManager
                .getInstance().getDefaultDisplay(context).get().getAttributes().scalDensity);
    }

}