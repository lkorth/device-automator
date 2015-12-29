package com.lukekorth.deviceautomator;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;import java.lang.Override;import java.lang.RuntimeException;import java.lang.String;

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
            void wrappedPerform(UiObject object) throws UiObjectNotFoundException {
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
            void wrappedPerform(UiObject object) throws UiObjectNotFoundException {
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
            void wrappedPerform(UiObject object) throws UiObjectNotFoundException {
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
            void wrappedPerform(UiObject object) throws UiObjectNotFoundException {
                object.swipeRight(steps);
            }
        };
    }

    void perform(UiObject object) {
        try {
            wrappedPerform(object);
        } catch (UiObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void wrappedPerform(UiObject object) throws UiObjectNotFoundException;
}
