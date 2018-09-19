package com.lukekorth.deviceautomator;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class UiObjectMatcherTest {

    @Test
    public void withTextStartingWith_includesText() {
        UiObjectMatcher matcher = UiObjectMatcher.withTextStartingWith("test");

        assertEquals("UiSelector[START_TEXT=test]", matcher.getUiSelector().toString());
        assertEquals("BySelector [TEXT='^\\Qtest\\E.*$']", matcher.getBySelector().toString());
    }

    @Test
    public void withTextStartingWith_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withTextStartingWith("test", TextView.class);

        assertEquals("UiSelector[START_TEXT=test, CLASS=android.widget.TextView]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', TEXT='^\\Qtest\\E.*$']",
                matcher.getBySelector().toString());
    }

    @Test
    public void withTextContaining_includesText() {
        UiObjectMatcher matcher = UiObjectMatcher.withTextContaining("test");

        assertEquals("UiSelector[CONTAINS_TEXT=test]", matcher.getUiSelector().toString());
        assertEquals("BySelector [TEXT='^.*\\Qtest\\E.*$']", matcher.getBySelector().toString());
    }

    @Test
    public void withTextContaining_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withTextContaining("test", TextView.class);

        assertEquals("UiSelector[CONTAINS_TEXT=test, CLASS=android.widget.TextView]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', TEXT='^.*\\Qtest\\E.*$']",
                matcher.getBySelector().toString());
    }

    @Test
    public void withText_includesText() {
        UiObjectMatcher matcher = UiObjectMatcher.withText("test");

        assertEquals("UiSelector[TEXT_REGEX=(?i)\\Qtest\\E]", matcher.getUiSelector().toString());
        assertEquals("BySelector [TEXT='(?i)\\Qtest\\E']", matcher.getBySelector().toString());
    }

    @Test
    public void withText_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withText("test", TextView.class);

        assertEquals("UiSelector[CLASS=android.widget.TextView, TEXT_REGEX=(?i)\\Qtest\\E]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', TEXT='(?i)\\Qtest\\E']",
                matcher.getBySelector().toString());
    }

    @Test
    public void withContentDescription_includesText() {
        UiObjectMatcher matcher = UiObjectMatcher.withContentDescription("test");

        assertEquals("UiSelector[DESCRIPTION=test]", matcher.getUiSelector().toString());
        assertEquals("BySelector [DESC='\\Qtest\\E']", matcher.getBySelector().toString());
    }

    @Test
    public void withContentDescription_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withContentDescription("test", TextView.class);

        assertEquals("UiSelector[CLASS=android.widget.TextView, DESCRIPTION=test]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', DESC='\\Qtest\\E']",
                matcher.getBySelector().toString());
    }

    @Test
    public void withResourceId_includesId() {
        UiObjectMatcher matcher = UiObjectMatcher.withResourceId("test");

        assertEquals("UiSelector[RESOURCE_ID=test]", matcher.getUiSelector().toString());
        assertEquals("BySelector [RES='\\Qtest\\E']", matcher.getBySelector().toString());
    }

    @Test
    public void withResourceId_includesClassTarget() {
        UiObjectMatcher matcher = UiObjectMatcher.withResourceId("test", TextView.class);

        assertEquals("UiSelector[CLASS=android.widget.TextView, RESOURCE_ID=test]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E', RES='\\Qtest\\E']",
                matcher.getBySelector().toString());
    }

    @Test
    public void withClass_includesClass() {
        UiObjectMatcher matcher = UiObjectMatcher.withClass(TextView.class);

        assertEquals("UiSelector[CLASS=android.widget.TextView]",
                matcher.getUiSelector().toString());
        assertEquals("BySelector [CLASS='\\Qandroid.widget.TextView\\E']",
                matcher.getBySelector().toString());
    }

    @Test
    public void childMatcher_setsChildSelector() {
        UiObjectMatcher matcher = UiObjectMatcher.withClass(LinearLayout.class)
                .childMatcher(UiObjectMatcher.withClass(ImageView.class));

        assertEquals("UiSelector[CLASS=android.widget.LinearLayout, CHILD=UiSelector[CLASS=android.widget.ImageView]]",
                matcher.getUiSelector().toString());
    }

    @Test
    public void instance_setsInstance() {
        UiObjectMatcher matcher = UiObjectMatcher.withClass(LinearLayout.class)
                .instance(2);

        assertEquals("UiSelector[CLASS=android.widget.LinearLayout, INSTANCE=2]",
                matcher.getUiSelector().toString());
    }

    @Test
    public void getUiSelector() {
        UiObjectMatcher matcher = UiObjectMatcher.withClass(LinearLayout.class);

        assertEquals("UiSelector[CLASS=android.widget.LinearLayout]",
                matcher.getUiSelector().toString());
    }

    @Test
    public void getUiObject() {
        UiObject uiObject = mock(UiObject.class);
        UiDevice device = mock(UiDevice.class);
        when(device.findObject(any(UiSelector.class))).thenReturn(uiObject);

        UiObjectMatcher matcher = UiObjectMatcher.withClass(TextView.class);
        UiObject object = matcher.getUiObject(device);

        verify(device).findObject(matcher.getUiSelector());
        assertEquals(uiObject, object);
    }
}
