/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.jfenn.slideactionviewsample.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.PixelMapElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Rect;
import ohos.media.image.common.Size;
import me.jfenn.slideactionview.SlideActionListener;
import me.jfenn.slideactionview.SlideActionView;
import me.jfenn.slideactionviewsample.ResourceTable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Sample app to test the SlideActionView library functionality.
 */
public class MainAbilitySlice extends AbilitySlice implements SlideActionListener {
    private static final PixelFormat PIXELMAP_CONFIG = PixelFormat.ARGB_8888;
    public static final HiLogLabel HI_LOG_LABEL = new HiLogLabel(0, 0, "SlideActionView MainAbility");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        SlideActionView view = (SlideActionView) findComponentById(ResourceTable.Id_SlideAction);
        view.setListener(this);
        Optional<PixelMapElement> element = getElementByResId(ResourceTable.Media_cancel_2);
        PixelMap pixelMap = getPixelMapFromDrawable(element.get()).get();
        view.setLeftIcon(pixelMap);
        Optional<PixelMapElement> element1 = getElementByResId(ResourceTable.Media_unlock_2);
        PixelMap pixelMap1 = getPixelMapFromDrawable(element1.get()).get();
        view.setRightIcon(pixelMap1);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    @Override
    public void onSlideLeft() {
        new ToastDialog(getContext())
                .setText("slid left")
                .show();

    }

    @Override
    public void onSlideRight() {
        new ToastDialog(getContext())
                .setText("slid right")
                .show();

    }

    /**
     * * preparePixelmap.
     *
     * @param resource resource
     * @return return pixelMap
     * @throws IOException Exception
     */
    public static Optional<PixelMap> preparePixelmap(Resource resource) throws IOException {
        byte[] bytes = null;
        try {
            if (resource != null) {
                bytes = readBytes(resource);
                resource.close();
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "get pixelmap failed, read resource bytes failed");
        }
        if (bytes == null) {
            HiLog.error(HI_LOG_LABEL, "get pixelmap failed, read resource bytes is null");
            return Optional.empty();
        }
        ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
        ImageSource imageSource = ImageSource.create(bytes, srcOpts);
        if (imageSource == null) {
            HiLog.error(HI_LOG_LABEL, "get pixelmap failed, image source is null");
        }
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.desiredSize = new Size(0, 0);
        decodingOpts.desiredRegion = new Rect(0, 0, 0, 0);
        decodingOpts.desiredPixelFormat = PixelFormat.ARGB_8888;
        PixelMap decodePixelMap = null;
        if (imageSource != null) {
            decodePixelMap = imageSource.createPixelmap(decodingOpts);
        }
        return Optional.ofNullable(decodePixelMap);
    }

    /**
     * * prepareElement.
     *
     * @param resource resource
     * @return return pixelMap
     * @throws IOException Exception
     */
    public static Optional<PixelMapElement> prepareElement(Resource resource) throws IOException, NotExistException {
        Optional<PixelMap> pixelMap = preparePixelmap(resource);
        if (pixelMap.isPresent()) {
            PixelMapElement pixelMapElement = new PixelMapElement(pixelMap.get());
            return Optional.of(pixelMapElement);
        }
        return Optional.empty();
    }

    /**
     * * getElementByResId.
     *
     * @param resourceId resourceId
     * @return return pixelMap
     */
    public Optional<PixelMapElement> getElementByResId(int resourceId) {
        ResourceManager resourceManager = getContext().getResourceManager();
        if (resourceManager == null) {
            return Optional.empty();
        }
        Optional<PixelMapElement> element = Optional.empty();
        if (resourceId != 0) {
            try {
                Resource resource = resourceManager.getResource(resourceId);
                element = prepareElement(resource);
            } catch (IOException | NotExistException e) {
                HiLog.error(HI_LOG_LABEL, "set imageview pixelmap failed, pixelmap is empty");
            }
        }
        return Optional.of(element.get());
    }

    private static byte[] readBytes(Resource resource) {
        final int bufferSize = 1024;
        final int ioEnd = -1;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bytes = new byte[bufferSize];
        byte[] bytesArray = new byte[0];
        while (true) {
            try {
                int readLen = resource.read(bytes, 0, bufferSize);
                if (readLen == ioEnd) {
                    bytesArray = output.toByteArray();
                    return bytesArray;
                }
                output.write(bytes, 0, readLen);
            } catch (IOException e) {
                break;
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    HiLog.error(HI_LOG_LABEL, "close output failed");
                }
            }
        }
        return bytesArray;
    }

    private Optional<PixelMap> getPixelMapFromDrawable(Element drawable) {
        if (drawable == null) {
            return Optional.empty();
        }

        if (drawable instanceof PixelMapElement) {
            return Optional.of(((PixelMapElement) drawable).getPixelMap());
        }

        try {
            PixelMap pixelMap;
            PixelMap.InitializationOptions initializationOptions = new PixelMap.InitializationOptions();

            initializationOptions.size = new Size(drawable.getBounds().getHeight(), drawable.getBounds().getWidth());
            initializationOptions.pixelFormat = PIXELMAP_CONFIG;
            pixelMap = PixelMap.create(initializationOptions);
            if (pixelMap == null) {
                HiLog.debug(HI_LOG_LABEL, "set getPixelMapFromDrawable pixelMap is null");
            }
            return Optional.of(pixelMap);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
