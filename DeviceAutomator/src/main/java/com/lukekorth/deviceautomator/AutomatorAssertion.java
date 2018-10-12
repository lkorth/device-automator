package com.lukekorth.deviceautomator;

import android.graphics.Rect;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * A collection of assertions for use with {@link DeviceAutomator}.
 */
public abstract class AutomatorAssertion {

    /**
     * Asserts that the ui element specified in {@link DeviceAutomator#onDevice(UiObjectMatcher)}
     * is visible.
     *
     * @return
     */
    public static AutomatorAssertion visible(final boolean visible) {
        return new AutomatorAssertion() {
            @Override
            public void wrappedCheck(UiObject object) throws UiObjectNotFoundException {
                try {
                    Rect bounds = object.getVisibleBounds();

                    if (bounds == null) {
                        fail("Matched view did not have any visible bounds");
                    }

                    if (visible) {
                        assertTrue("Matched view was not visible", bounds.width() > 0);
                        assertTrue("Matched view was not visible", bounds.height() > 0);
                    } else {
                        assertTrue("Matched view was visible", bounds.width() == 0);
                        assertTrue("Matched view was visible", bounds.height() == 0);
                    }
                } catch (UiObjectNotFoundException e) {
                    if (visible) {
                        fail(e.getMessage());
                    }
                }
            }
        };
    }

    /**
     * Asserts that the ui element specified in {@link DeviceAutomator#onDevice(UiObjectMatcher)}
     * has text that matches the given matcher.
     *
     * @param matcher The <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a> to match against.
     * @return
     */
    public static AutomatorAssertion text(final Matcher matcher) {
        return new AutomatorAssertion() {
            @Override
            public void wrappedCheck(UiObject object) throws UiObjectNotFoundException {
                visible(true).check(object);
                if (!matcher.matches(object.getText())) {
                    StringDescription description = new StringDescription();
                    description.appendText("Expected ");
                    matcher.describeTo(description);
                    description.appendText(" ");
                    matcher.describeMismatch(object.getText(), description);
                    assertTrue(description.toString(), false);
                }
            }
        };
    }

    /**
     * Asserts that the ui element specified in {@link DeviceAutomator#onDevice(UiObjectMatcher)}
     * has a content description that matches the given matcher.
     *
     * @param matcher The <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a> to match against.
     * @return
     */
    public static AutomatorAssertion contentDescription(final Matcher matcher) {
        return new AutomatorAssertion() {
            @Override
            public void wrappedCheck(UiObject object) throws UiObjectNotFoundException {
                visible(true).check(object);
                if (!matcher.matches(object.getContentDescription())) {
                    StringDescription description = new StringDescription();
                    description.appendText("Expected ");
                    matcher.describeTo(description);
                    description.appendText(" ");
                    matcher.describeMismatch(object.getText(), description);
                    assertTrue(description.toString(), false);
                }
            }
        };
    }

    /**
     * Asserts that the foreground app is the package specified.
     *
     * @param packageName The package to check for.
     * @return
     */
    public static AutomatorAssertion foregroundAppIs(final String packageName) {
        return new AutomatorAssertion() {
            @Override
            public void wrappedCheck(UiObject object) throws UiObjectNotFoundException {
                assertTrue(UiDevice.getInstance(getInstrumentation()).hasObject(By.pkg(packageName)));
            }
        };
    }

    void check(UiObject object) {
        try {
            wrappedCheck(object);
        } catch (UiObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void wrappedCheck(UiObject object) throws UiObjectNotFoundException;
}
