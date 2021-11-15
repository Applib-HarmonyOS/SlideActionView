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

import me.jfenn.slideactionview.DimenUtils;
import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.app.Context;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DimenUtilsTest {
    private Context context;

    @Before
    public void setUp() {
        context = AbilityDelegatorRegistry.getAbilityDelegator().getAppContext();
    }

    @Test
    public void testDpToPx() {
       int px = DimenUtils.dpToPx(context,30);
       assertEquals(60,px);
    }

    @Test
    public void testDpToPx1() {
        int px = DimenUtils.dpToPx(context,30);
        assertNotEquals(30,px);
    }

    @Test
    public void testPxToDp() {
        int dp = (int) DimenUtils.pxToDp(context, 30);
        assertEquals(15,dp);
    }

    @Test
    public void testPxToDp1() {
        int dp = (int) DimenUtils.pxToDp(context, 30);
        assertNotEquals(30,dp);
    }


    @Test
    public void testSpToPx() {
        int px = DimenUtils.spToPx(context,30);
        assertEquals(60,px);
    }

    @Test
    public void testSpToPx1() {
        int px = DimenUtils.spToPx(context,30);
        assertNotEquals(30,px);
    }

    @Test
    public void testPxToSp() {
        int sp = (int) DimenUtils.pxToSp(context, 30);
        assertEquals(15,sp);
    }

    @Test
    public void testPxToSp1() {
        int sp = (int) DimenUtils.pxToSp(context, 30);
        assertNotEquals(30,sp);
    }

}