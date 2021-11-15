package me.jfenn.slideactionview;

import ohos.agp.components.element.Element;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.render.Canvas;
import ohos.agp.render.Texture;
import ohos.media.image.PixelMap;
import ohos.media.image.PixelMap.InitializationOptions;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;

/**
 *  ImageUtils Class.
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * Converts Element to pixelmap.
     *
     * @param drawable          A element.
     * @return                  A pixelmap.
     */
    public static PixelMap drawableToBitmap(Element drawable) {

        if (drawable == null) {
            InitializationOptions options = new InitializationOptions();
            options.pixelFormat = PixelFormat.ARGB_8888;
            options.size = new Size(1, 1);
            return PixelMap.create(options);
        }

        PixelMap bitmap;

        if (drawable instanceof PixelMapElement) {
            PixelMapElement bitmapDrawable = (PixelMapElement) drawable;
            if (bitmapDrawable.getPixelMap() != null) {
                return bitmapDrawable.getPixelMap();
            }
        }

        if (drawable.getBounds().getWidth() <= 0 && drawable.getBounds().getHeight() <= 0) {
            InitializationOptions options2 = new InitializationOptions();
            options2.size = new Size(1, 1);
            options2.pixelFormat = PixelFormat.ARGB_8888;
            bitmap = PixelMap.create(options2);
        } else {
            InitializationOptions options3 = new InitializationOptions();
            options3.size = new Size(drawable.getBounds().getWidth(), drawable.getBounds().getHeight());
            options3.pixelFormat = PixelFormat.ARGB_8888;
            bitmap = PixelMap.create(options3);
        }

        Texture texture = new Texture(bitmap);
        Canvas canvas = new Canvas(texture);
        drawable.setBounds(0, 0, canvas.getLocalClipBounds().getWidth(), canvas.getLocalClipBounds().getHeight());
        drawable.drawToCanvas(canvas);
        return bitmap;
    }

}