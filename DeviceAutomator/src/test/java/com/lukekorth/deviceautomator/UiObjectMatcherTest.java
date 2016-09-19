package com.lukekorth.deviceautomator;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UiObjectMatcherTest {

    @Test
    public void withTextContaining_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withTextContaining("test", TextView.class);

        assertEquals("UiSelector[CONTAINS_TEXT=test, CLASS=android.widget.TextView]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', TEXT='^.*\\Qtest\\E.*$']",
                matcher.getBySelector().toString());
    }
}
