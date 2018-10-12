package com.lukekorth.deviceautomator;

import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class AutomatorActionTest {

    @Test
    public void click() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.click().perform(null, object);

        verify(object).click();
    }

    @Test
    public void check_doesNotClickObjectIfObjectNotCheckable() throws UiObjectNotFoundException {
        UiObject uncheckedObject = mock(UiObject.class);
        when(uncheckedObject.isCheckable()).thenReturn(false);
        when(uncheckedObject.isChecked()).thenReturn(false);

        AutomatorAction.check(true).perform(null, uncheckedObject);

        verify(uncheckedObject, times(0)).click();

        UiObject checkedObject = mock(UiObject.class);
        when(checkedObject.isCheckable()).thenReturn(false);
        when(checkedObject.isChecked()).thenReturn(true);

        AutomatorAction.check(false).perform(null, checkedObject);

        verify(checkedObject, times(0)).click();
    }

    @Test
    public void check_clicksObjectIfRequestingCheckAndObjectNotChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isCheckable()).thenReturn(true);
        when(object.isChecked()).thenReturn(false);

        AutomatorAction.check(true).perform(null, object);

        verify(object).click();
    }

    @Test
    public void check_doesNotClickObjectIfRequestingCheckAndObjectIsChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isCheckable()).thenReturn(true);
        when(object.isChecked()).thenReturn(true);

        AutomatorAction.check(true).perform(null, object);

        verify(object, times(0)).click();
    }

    @Test
    public void check_clicksObjectIfRequestingUncheckAndObjectChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isCheckable()).thenReturn(true);
        when(object.isChecked()).thenReturn(true);

        AutomatorAction.check(false).perform(null, object);

        verify(object).click();
    }

    @Test
    public void check_doesNotClickObjectIfRequestingUncheckAndObjectIsUnchecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isCheckable()).thenReturn(true);
        when(object.isChecked()).thenReturn(false);

        AutomatorAction.check(false).perform(null, object);

        verify(object, times(0)).click();
    }

    @Test
    public void setText() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.setText("test").perform(null, object);

        verify(object).setText("test");
    }

    @Test
    public void clearTextField() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.clearTextField().perform(null, object);

        verify(object).clearTextField();
    }

    @Test
    public void swipeRight() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.swipeRight(5).perform(null, object);

        verify(object).swipeRight(5);
    }

    @Test
    public void swipeLeft() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.swipeLeft(5).perform(null, object);

        verify(object).swipeLeft(5);
    }

    @Test(timeout = 1000)
    public void perform() throws InterruptedException {
        final UiSelector expectedSelector = mock(UiSelector.class);
        final UiObject expectedObject = mock(UiObject.class);
        final CountDownLatch latch = new CountDownLatch(1);
        AutomatorAction action = new AutomatorAction() {
            @Override
            public void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                assertEquals(expectedSelector, selector);
                assertEquals(expectedObject, object);
                latch.countDown();
            }
        };

        action.perform(expectedSelector, expectedObject);

        latch.await();
    }
}
