package com.lukekorth.deviceautomator;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AutomatorActionTest {

    @Test
    public void click() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);

        AutomatorAction.click().perform(null, object);

        verify(object).click();
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

    @Test(timeout = 1000)
    public void perform() throws InterruptedException {
        final UiSelector expectedSelector = mock(UiSelector.class);
        final UiObject expectedObject = mock(UiObject.class);
        final CountDownLatch latch = new CountDownLatch(1);
        AutomatorAction action = new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                assertEquals(expectedSelector, selector);
                assertEquals(expectedObject, object);
                latch.countDown();
            }
        };

        action.perform(expectedSelector, expectedObject);

        latch.await();
    }
}
