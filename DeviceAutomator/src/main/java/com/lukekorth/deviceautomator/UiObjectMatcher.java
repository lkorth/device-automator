package com.lukekorth.deviceautomator;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;import java.lang.Class;import java.lang.String;

/**
 * A clean interface for specifying {@link UiObject}s to interact with.
 */
public class UiObjectMatcher {

    private UiSelector mUiSelector;
    private BySelector mBySelector;

    private UiObjectMatcher(UiSelector uiSelector, BySelector bySelector) {
        mUiSelector = uiSelector;
        mBySelector = bySelector;
    }

    /**
     * Find a view based on the starting text in the view.
     *
     * @param text Starting text to search for.
     * @return
     */
    public static UiObjectMatcher withTextStartingWith(String text) {
        return withTextStartingWith(text, null);
    }

    /**
     * Find a view based on the starting text in the view.
     *
     * @param text Starting text to search for.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withTextStartingWith(String text, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .textStartsWith(text);
        BySelector bySelector = By.textStartsWith(text);

        if (klass != null) {
            uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the text contained within the view.
     *
     * @param text Text to search for inside a view.
     * @return
     */
    public static UiObjectMatcher withTextContaining(String text) {
        return withTextContaining(text, null);
    }

    /**
     * Find a view based on the text contained within the view.
     *
     * @param text Text to search for inside a view.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withTextContaining(String text, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .textContains(text);
        BySelector bySelector = By.textContains(text);

        if (klass != null) {
            uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the exact text contained within the view.
     *
     * @param text Exact text in the view.
     * @return
     */
    public static UiObjectMatcher withText(String text) {
        return withText(text, null);
    }

    /**
     * Find a view based on the exact text contained within the view.
     *
     * @param text Exact text in the view.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withText(String text, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .text(text);
        BySelector bySelector = By.text(text);

        if (klass != null) {
            uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the content description of the view.
     *
     * On {@link android.os.Build.VERSION_CODES#LOLLIPOP} devices and higher than matcher can be
     * used to match views in {@link android.webkit.WebView}s and browsers.
     *
     * @param text Content description of the view.
     * @return
     */
    public static UiObjectMatcher withContentDescription(String text) {
        return withContentDescription(text, null);
    }

    /**
     * Find a view based on the content description of the view.
     *
     * On {@link android.os.Build.VERSION_CODES#LOLLIPOP} devices and higher than matcher can be
     * used to match views in {@link android.webkit.WebView}s and browsers.
     *
     * @param text Content description of the view.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withContentDescription(String text, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .description(text);
        BySelector bySelector = By.desc(text);

        if (klass != null) {
            uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    UiObject getUiObject(UiDevice device) {
        return device.findObject(mUiSelector);
    }

    BySelector getBySelector() {
        return mBySelector;
    }
}
