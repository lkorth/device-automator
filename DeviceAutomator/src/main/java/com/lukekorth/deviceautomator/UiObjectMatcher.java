package com.lukekorth.deviceautomator;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import java.util.regex.Pattern;

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
     * Find a view based on the prefixed text in the view. The matching is case-insensitive.
     *
     * @param text Prefix to search for.
     * @return
     */
    public static UiObjectMatcher withTextStartingWith(String text) {
        return withTextStartingWith(text, null);
    }

    /**
     * Find a view based on the prefixed text in the view. The matching is case-insensitive.
     *
     * @param text Prefix to search for.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withTextStartingWith(String text, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .textStartsWith(text);
        BySelector bySelector = By.textStartsWith(text);

        if (klass != null) {
            uiSelector = uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the text contained within the view. The matching is case-sensitive.
     *
     * @param text Text to search for inside a view.
     * @return
     */
    public static UiObjectMatcher withTextContaining(String text) {
        return withTextContaining(text, null);
    }

    /**
     * Find a view based on the text contained within the view. The matching is case-sensitive.
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
            uiSelector = uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the exact text contained within the view. Matching is case-insensitive.
     *
     * @param text Exact text in the view.
     * @return
     */
    public static UiObjectMatcher withText(String text) {
        return withText(text, null);
    }

    /**
     * Find a view based on the exact text contained within the view. Matching is case-insensitive.
     *
     * @param text Exact text in the view.
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withText(String text, Class klass) {
        Pattern pattern = Pattern.compile("(?i)" + Pattern.quote(text));

        UiSelector uiSelector = new UiSelector()
                .textMatches(pattern.pattern());
        BySelector bySelector = By.text(pattern);

        if (klass != null) {
            uiSelector = uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the content description of the view. The content-description is typically used by the
     * Android Accessibility framework to provide an audio prompt for the widget when the widget is selected.
     * The content-description for the widget must match exactly with the string in your input argument.
     * Matching is case-sensitive.
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
     * Find a view based on the content description of the view. The content-description is typically used by the
     * Android Accessibility framework to provide an audio prompt for the widget when the widget is selected.
     * The content-description for the widget must match exactly with the string in your input argument.
     * Matching is case-sensitive.
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
            uiSelector = uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on the resource id. Resource ids should be the fully qualified id,
     * ex: com.android.browser:id/url
     *
     * @param id The fully qualified id of the view, ex: com.android.browser:id/url
     * @return
     */
    public static UiObjectMatcher withResourceId(String id) {
        return withResourceId(id, null);
    }

    /**
     * Find a view based on the resource id. Resource ids should be the fully qualified id,
     * ex: com.android.browser:id/url
     *
     * @param id The fully qualified id of the view, ex: com.android.browser:id/url
     * @param klass Expected class of the view.
     * @return
     */
    public static UiObjectMatcher withResourceId(String id, Class klass) {
        UiSelector uiSelector = new UiSelector()
                .resourceId(id);
        BySelector bySelector = By.res(id);

        if (klass != null) {
            uiSelector = uiSelector.className(klass);
            bySelector.clazz(klass);
        }

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    /**
     * Find a view based on it's class.
     *
     * @param klass The class of the view to find.
     * @return
     */
    public static UiObjectMatcher withClass(Class klass) {
        UiSelector uiSelector = new UiSelector()
                .className(klass);
        BySelector bySelector = By.clazz(klass);

        return new UiObjectMatcher(uiSelector, bySelector);
    }

    UiSelector getUiSelector() {
        return mUiSelector;
    }

    UiObject getUiObject(UiDevice device) {
        return device.findObject(mUiSelector);
    }

    BySelector getBySelector() {
        return mBySelector;
    }
}
