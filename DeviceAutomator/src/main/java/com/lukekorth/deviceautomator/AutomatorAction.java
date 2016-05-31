package com.lukekorth.deviceautomator;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

/**
 * A collection of actions for use with {@link DeviceAutomator}.
 */
public abstract class AutomatorAction {

    /**
     * Performs a click on the ui element specified in
     * {@link DeviceAutomator#onDevice(UiObjectMatcher)}.
     *
     * @return
     */
    public static AutomatorAction click() {
        return new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                object.click();
            }
        };
    }

    /**
     * Sets the text on the ui element specified in
     * {@link DeviceAutomator#onDevice(UiObjectMatcher)}.
     *
     * @return
     */
    public static AutomatorAction setText(final String text) {
        return new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                object.setText(text);
            }
        };
    }

    /**
     * Clears the text field on the ui element specified in
     * {@link DeviceAutomator#onDevice(UiObjectMatcher)}.
     *
     * @return
     */
    public static AutomatorAction clearTextField() {
        return new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                object.clearTextField();
            }
        };
    }

    /**
     * Performs the swipe right action on the UiObject. The swipe gesture can be performed over any surface.
     * The targeted UI element does not need to be scrollable.
     *
     * @param steps indicates the number of injected move steps into the system. Steps are injected about 5ms apart.
     *              So a 100 steps may take about 1/2 second to complete.
     * @return
     */
    public static AutomatorAction swipeRight(final int steps) {
        return new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                object.swipeRight(steps);
            }
        };
    }

    /**
     * Scrolls a {@link UiScrollable} until the given text is displayed on the screen.
     *
     * @param text the text to scroll to.
     * @return
     */
    public static AutomatorAction scrollTextIntoView(final String text) {
        return new AutomatorAction() {
            @Override
            void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException {
                new UiScrollable(selector).scrollTextIntoView(text);
            }
        };
    }

    void perform(UiSelector selector, UiObject object) {
        try {
            wrappedPerform(selector, object);
        } catch (UiObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void wrappedPerform(UiSelector selector, UiObject object) throws UiObjectNotFoundException;
}
