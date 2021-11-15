package me.jfenn.slideactionview;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.element.Element;
import ohos.agp.render.BlendMode;
import ohos.agp.render.Canvas;
import ohos.agp.render.ColorFilter;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.media.image.PixelMap;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Slide action view class.
 */
public class SlideActionView extends Component implements Component.DrawTask, Component.TouchEventListener {
    private float mx = -1;
    private AnimatedFloat selected;
    private Map<Float, AnimatedFloat> ripples;
    private int handleRadius;
    private int expandedHandleRadius;
    private int selectionRadius;
    private int rippleRadius;
    private Paint normalPaint;
    private Paint outlinePaint;
    private Paint bitmapPaint;
    private PixelMap leftImage;
    private PixelMap rightImage;
    private SlideActionListener listener;

    /**
     * SlideActionView Constructor.
     *
     *  @param context - context for SlideActionView constructor
     *
     */
    public SlideActionView(Context context) {
        this(context, null);
        init();
        addDrawTask(this::onDraw);
    }

    /**
     * SlideActionView Constructor.
     *
     *  @param context - context for SlideActionView constructor
     *  @param attrSet - attributes
     *
     */
    public SlideActionView(Context context, AttrSet attrSet) {
        super(context, attrSet, 0);
        init();
        addDrawTask(this::onDraw);
    }

    /**
     * SlideActionView Constructor.
     *
     *  @param context - context for SlideActionView constructor
     *  @param attrSet - attributes
     *  @param defStyleAttr - defStyle attribute
     *
     */
    public SlideActionView(Context context, AttrSet attrSet, int defStyleAttr) {
        super(context, attrSet, defStyleAttr);
        init();
        addDrawTask(this::onDraw);
    }

    private void init() {
        handleRadius = DimenUtils.dpToPx(getContext(), 12);
        expandedHandleRadius = DimenUtils.dpToPx(getContext(), 32);
        selectionRadius = DimenUtils.dpToPx(getContext(), 42);
        rippleRadius = DimenUtils.dpToPx(getContext(), 140);
        selected = new AnimatedFloat(0);
        ripples = new HashMap<>();
        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.FILL_STYLE);
        Color hmosColor = SlideActionView.changeParamToColor(Color.RED.getValue());
        normalPaint.setColor(hmosColor);
        normalPaint.setAntiAlias(true);
        normalPaint.setDither(true);
        outlinePaint = new Paint();
        outlinePaint.setStyle(Paint.Style.STROKE_STYLE);
        Color hmosColor1 = SlideActionView.changeParamToColor(Color.YELLOW.getValue());
        outlinePaint.setColor(hmosColor1);
        outlinePaint.setAntiAlias(true);
        outlinePaint.setDither(true);
        bitmapPaint = new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL_STYLE);
        Color hmosColor2 = SlideActionView.changeParamToColor(Color.GREEN.getValue());
        bitmapPaint.setColor(hmosColor2);
        bitmapPaint.setAntiAlias(true);
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);
        setTouchEventListener(this);
        int param = SlideActionView.changeParamBooleanToInt(true);
        setFocusable(param);
        setClickable(true);
    }

    /**
     * Specify an interface to pass events to when an action
     * is selected.
     *
     * @param listener          An interface to pass events to.
     */
    public void setListener(SlideActionListener listener) {
        this.listener = listener;
    }

    /**
     * Specifies the icon to display on the left side of the view,
     * as a Element. If it is just as easier to pass a PixelMap, you
     * should avoid using this method; all it does is convert the
     * Element to a pixelmap, then call the same method again.
     *
     * @param drawable          The Element to use as an icon.
     */
    public void setLeftIcon(Element drawable) {
        setLeftIcon(ImageUtils.drawableToBitmap(drawable));
    }

    /**
     * Specifies the icon to display on the left side of the view.
     *
     * @param bitmap            The PixelMap to use as an icon.
     */
    public void setLeftIcon(PixelMap bitmap) {
        leftImage = bitmap;
        getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
    }

    /**
     * Specifies the icon to display on the right side of the view,
     * as a Element. If it is just as easier to pass a PixelMap, you
     * should avoid using this method; all it does is convert the
     * element to a pixelmap, then call the same method again.
     *
     * @param drawable          The Element to use as an icon.
     */
    public void setRightIcon(Element drawable) {
        setRightIcon(ImageUtils.drawableToBitmap(drawable));
    }

    /**
     * Specifies the icon to display on the right side of the view.
     *
     * @param bitmap            The PixelMap to use as an icon.
     */
    public void setRightIcon(PixelMap bitmap) {
        rightImage = bitmap;
        getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
    }

    /**
     * Specify the color of the touch handle in the center of
     * the view. The alpha of this color is modified to be somewhere
     * between 0 and 150.
     *
     * @param handleColor       The color of the touch handle.
     */
    public void setTouchHandleColor(int handleColor) {
        Color hmosColor = SlideActionView.changeParamToColor(handleColor);
        normalPaint.setColor(hmosColor);
    }

    /**
     * The color of the touch handle in the center of the view.
     */
    public int getTouchHandleColor() {
        return normalPaint.getColor().getValue();
    }

    /**
     * Specify the color of the random outlines drawn all over the place.
     *
     * @param outlineColor      The color of the random outlines.
     */
    public void setOutlineColor(int outlineColor) {
        Color hmosColor = SlideActionView.changeParamToColor(outlineColor);
        outlinePaint.setColor(hmosColor);
    }

    /**
     * The color of the random outlines drawn all over the place.
     */
    public int getOutlineColor() {
        return outlinePaint.getColor().getValue();
    }

    /**
     * Specify the color applied to the left/right icons as a filter.
     *
     * @param iconColor         The color that the left/right icons are filtered by.
     */
    public void setIconColor(int iconColor) {
        Color hmosColor = SlideActionView.changeParamToColor(iconColor);
        bitmapPaint.setColor(hmosColor);
        bitmapPaint.setColorFilter(new ColorFilter(iconColor, BlendMode.SRC_IN));
    }

    /**
     * The color applied to the left/right icons as a filter.
     */
    public int getIconColor() {
        return bitmapPaint.getColor().getValue();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        selected.next(true);
        if (mx < 0) {
            mx = (float) getWidth() / 2;
        }

        normalPaint.setAlpha((float) (150 - (int) (selected.val() * 100)) / 255);
        int radius = (int) ((handleRadius * (1 - selected.val())) + (expandedHandleRadius * selected.val()));
        float drawnX = (mx * selected.val()) + (((float) getWidth() / 2) * (1 - selected.val()));
        canvas.drawCircle(drawnX, (float) getHeight() / 2, radius, normalPaint);

        if (leftImage != null && rightImage != null) {
            bitmapPaint.setAlpha((255 * Math.min(1f,
                    Math.max(0f, (getWidth() - drawnX - selectionRadius) / getWidth()))) / 255);
            PixelMapHolder pixelMapHolder = SlideActionView.changeParamToPixelMapHolder(leftImage);
            canvas.drawPixelMapHolder(pixelMapHolder, selectionRadius - (leftImage.getImageInfo().size.width / 2),
                    (getHeight() - leftImage.getImageInfo().size.height) / 2, bitmapPaint);
            bitmapPaint.setAlpha((255 * Math.min(1f, Math.max(0f, (drawnX - selectionRadius) / getWidth()))) / 255);
            PixelMapHolder pixelMapHolder1 = SlideActionView.changeParamToPixelMapHolder(rightImage);
            canvas.drawPixelMapHolder(pixelMapHolder1,
                    getWidth() - selectionRadius - (leftImage.getImageInfo().size.width / 2),
                    (getHeight() - leftImage.getImageInfo().size.height) / 2, bitmapPaint);
        }

        if (Math.abs((getWidth() / 2) - drawnX) > selectionRadius / 2) {
            if (drawnX * 2 < getWidth()) {
                float progress = Math.min(1f, Math.max(0f,
                        ((getWidth() - ((drawnX + selectionRadius) * 2)) / getWidth())));
                progress = (float) Math.pow(progress, 0.2f);
                outlinePaint.setAlpha((255 * progress) / 255);
                canvas.drawCircle(selectionRadius, getHeight() / 2,
                        (selectionRadius / 2.0f) + (rippleRadius * (1 - progress)), outlinePaint);
            } else {
                float progress = Math.min(1f,
                        Math.max(0f, (((drawnX - selectionRadius) * 2) - getWidth()) / getWidth()));
                progress = (float) Math.pow(progress, 0.2f);
                outlinePaint.setAlpha((255 * progress) / 255);
                canvas.drawCircle(getWidth() - selectionRadius,
                        getHeight() / 2, (selectionRadius / 2.0f) + (rippleRadius * (1 - progress)), outlinePaint);
            }
        }

        for (float x : ripples.keySet()) {
            AnimatedFloat scale = ripples.get(x);
            scale.next(true, 1600);
            normalPaint.setAlpha((float) (150 * (scale.getTarget() - scale.val()) / scale.getTarget()) / 255);
            canvas.drawCircle(x, getHeight() / 2, scale.val(), normalPaint);
            if (scale.isTarget()) {
                ripples.remove(x);
            }

        }

        if (!selected.isTarget() || ripples.size() > 0) {
            getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
        }
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        MmiPoint point = touchEvent.getPointerScreenPosition(touchEvent.getIndex());
        if (touchEvent.getAction() == TouchEvent.PRIMARY_POINT_DOWN
                && Math.abs(point.getX() - (getWidth() / 2)) < selectionRadius) {
            selected.to(1f);
        } else if (touchEvent.getAction() == TouchEvent.PRIMARY_POINT_UP && selected.getTarget() > 0) {
            selected.to(0f);
            if (point.getX() > getWidth() - (selectionRadius * 2)) {
                AnimatedFloat ripple = new AnimatedFloat(selectionRadius);
                ripple.to((float) rippleRadius);
                ripples.put((float) getWidth() - selectionRadius, ripple);
                if (listener != null) {
                    listener.onSlideRight();
                }
                getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
            } else if (point.getX() < selectionRadius * 2) {
                AnimatedFloat ripple = new AnimatedFloat(selectionRadius);
                ripple.to((float) rippleRadius);
                ripples.put((float) selectionRadius, ripple);
                if (listener != null) {
                    listener.onSlideLeft();
                }
                getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
            }
        }

        if (selected.getTarget() > 0) {
            mx = point.getX();
            getContext().getUITaskDispatcher().asyncDispatch(this::invalidate);
        }
        return true;
    }

    public static Color changeParamToColor(int color) {
        return new Color(color);
    }

    public static int changeParamBooleanToInt(boolean value) {
        return Boolean.compare(value, false);
    }

    public static PixelMapHolder changeParamToPixelMapHolder(PixelMap pixelMap) {
        return new PixelMapHolder(pixelMap);
    }
}
