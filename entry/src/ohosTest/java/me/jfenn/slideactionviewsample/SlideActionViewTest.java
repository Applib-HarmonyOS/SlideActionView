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

package me.jfenn.slideactionviewsample;

import me.jfenn.slideactionview.SlideActionView;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.utils.Color;
import org.junit.Test;
import ohos.agp.components.Attr;
import ohos.agp.components.AttrSet;
import ohos.app.Context;
import org.junit.Before;
import java.util.Optional;
import static org.junit.Assert.*;


public class SlideActionViewTest {
    private Context context;
    private AttrSet attrSet;
    private SlideActionView slideActionView;
    private static final String COLOR = "#4CAF50";
    private static final String COLOR1 = "#B7AC8D";

    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("me.jfenn.slideactionviewsample", actualBundleName);
    }

    @Before
    public void setUp() {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
        attrSet = new AttrSet() {
            @Override
            public Optional<String> getStyle() {
                return Optional.empty();
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Optional<Attr> getAttr(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<Attr> getAttr(String s) {
                return Optional.empty();
            }
        };
    }

    @Test
    public void testTouchHandleColor() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setTouchHandleColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), slideActionView.getTouchHandleColor());
    }

    @Test
    public void testTouchHandleColor1() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setTouchHandleColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), slideActionView.getTouchHandleColor());
    }

    @Test
    public void testOutlineColor() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setOutlineColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), slideActionView.getOutlineColor());
    }

    @Test
    public void testOutlineColor1() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setOutlineColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), slideActionView.getOutlineColor());
    }

    @Test
    public void testIconColor() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setIconColor(Color.getIntColor(COLOR));
        assertEquals(Color.getIntColor(COLOR), slideActionView.getIconColor());
    }

    @Test
    public void testIconColor1() {
        slideActionView = new SlideActionView(context, attrSet);
        slideActionView.setIconColor(Color.getIntColor(COLOR));
        assertNotEquals(Color.getIntColor(COLOR1), slideActionView.getIconColor());
    }

    @Test
    public void testChangeParamToColor() {
        Color hmosColor = SlideActionView.changeParamToColor(Color.RED.getValue());
        assertEquals(Color.RED,hmosColor);
    }

    @Test
    public void testChangeParamToColor1() {
        Color hmosColor = SlideActionView.changeParamToColor(Color.RED.getValue());
        assertNotEquals(Color.BLUE,hmosColor);
    }

    @Test
    public void testChangeParamBooleanToInt() {
        int param = SlideActionView.changeParamBooleanToInt(true);
        assertEquals(1,param);
    }

    @Test
    public void testChangeParamBooleanToInt1() {
        int param = SlideActionView.changeParamBooleanToInt(false);
        assertNotEquals(1,param);
    }

}