package com.lukekorth.deviceautomator;

import android.graphics.Rect;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class AutomatorAssertionTest {

    @Test
    public void visible_true_assertionFailsWhenObjectReturnsNullVisibleBounds() {
        try {
            AutomatorAssertion.visible(true).check(mock(UiObject.class));
        } catch (AssertionFailedError e) {
            assertEquals("Matched view did not have any visible bounds", e.getMessage());
        }
    }

    @Test
    public void visible_true_assertionFailsWhenObjectIsNotFound() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        doThrow(new UiObjectNotFoundException("Not Found!")).when(object).getVisibleBounds();

        try {
            AutomatorAssertion.visible(true).check(object);
        } catch (AssertionFailedError e) {
            assertEquals("Not Found!", e.getMessage());
        }
    }

    @Test
    public void visible_true_assertionFailsWhenObjectHasNoHeight() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(0);
        when(rect.width()).thenReturn(1);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        try {
            AutomatorAssertion.visible(true).check(object);
        } catch (AssertionFailedError e) {
            assertEquals("Matched view was not visible", e.getMessage());
        }
    }

    @Test
    public void visible_true_assertionFailsWhenObjectHasNoWidth() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(1);
        when(rect.width()).thenReturn(0);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        try {
            AutomatorAssertion.visible(true).check(object);
        } catch (AssertionFailedError e) {
            assertEquals("Matched view was not visible", e.getMessage());
        }
    }

    @Test
    public void visible_true_isSuccessfulWhenViewHasHeightAndWidth() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(1);
        when(rect.width()).thenReturn(1);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        AutomatorAssertion.visible(true).check(object);
    }

    @Test
    public void visible_false_assertionFailsWhenObjectReturnsNullVisibleBounds() {
        try {
            AutomatorAssertion.visible(false).check(mock(UiObject.class));
        } catch (AssertionFailedError e) {
            assertEquals("Matched view did not have any visible bounds", e.getMessage());
        }
    }

    @Test
    public void visible_false_isSuccessfulWhenObjectIsNotFound() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        doThrow(new UiObjectNotFoundException("Not Found!")).when(object).getVisibleBounds();

        AutomatorAssertion.visible(false).check(object);
    }

    @Test
    public void visible_false_isSuccessfulWhenViewHasNoHeightAndWidth() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(0);
        when(rect.width()).thenReturn(0);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        AutomatorAssertion.visible(false).check(object);
    }

    @Test
    public void visible_false_assertionFailsWhenObjectHasAHeight() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(1);
        when(rect.width()).thenReturn(0);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        try {
            AutomatorAssertion.visible(false).check(object);
        } catch (AssertionFailedError e) {
            assertEquals("Matched view was visible", e.getMessage());
        }
    }

    @Test
    public void visible_false_assertionFailsWhenObjectHasAWidth() throws UiObjectNotFoundException {
        Rect rect = mock(Rect.class);
        when(rect.height()).thenReturn(0);
        when(rect.width()).thenReturn(1);
        UiObject object = mock(UiObject.class);
        when(object.getVisibleBounds()).thenReturn(rect);

        try {
            AutomatorAssertion.visible(false).check(object);
        } catch (AssertionFailedError e) {
            assertEquals("Matched view was visible", e.getMessage());
        }
    }
}
