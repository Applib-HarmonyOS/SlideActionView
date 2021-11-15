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

import me.jfenn.slideactionview.AnimatedFloat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AnimatedFloatTest {
    AnimatedFloat animatedFloat;

    @Before
    public void setUp() {
      //setup
    }

    @Test
    public void testNextVal() {
        animatedFloat = new AnimatedFloat(5);
        float val1=animatedFloat.nextVal(5,10);
        assertEquals(String.valueOf(5.0),String.valueOf(val1));
    }

    @Test
    public void testNextVal1() {
        animatedFloat = new AnimatedFloat(10);
        float val1=animatedFloat.nextVal(10,20);
        assertNotEquals(String.valueOf(5.0),String.valueOf(val1));
    }
}