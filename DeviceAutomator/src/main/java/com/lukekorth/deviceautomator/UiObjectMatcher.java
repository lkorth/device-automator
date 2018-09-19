package com.lukekorth.deviceautomator;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import java.util.regex.Pattern;

/**
 * A clean interface for specifying {@link UiObject}s to interact with.
 */
public class UiObjectMatcher {

    private UiSelector mUiSelector;
    private BySelector mBySelector;

    public UiObjectMatcher(UiSelector uiSelector, BySelector bySelector) {
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

    /**
     * Specify the child matcher for use with {@link UiSelector#childSelector(UiSelector)}.
     *
     * Use this selector to narrow the search scope to child widgets under a specific parent widget.
     *
     * @param childMatcher The {@link UiObjectMatcher} to use as the child matcher.
     * @return
     */
    public UiObjectMatcher childMatcher(UiObjectMatcher childMatcher) {
        mUiSelector = mUiSelector.childSelector(childMatcher.getUiSelector());
        return this;
    }

    /**
     * Set the search criteria to match the widget by its instance number. The instance value must
     * be 0 or greater, where the first instance is 0. For example, to simulate a user click on the
     * third image that is enabled in a UI screen, you could specify a a search criteria where the
     * instance is 2.
     *
     * @param instance The instance to find.
     * @return
     */
    public UiObjectMatcher instance(int instance) {
        mUiSelector = mUiSelector.instance(instance);
        return this;
    }

    public UiSelector getUiSelector() {
        return mUiSelector;
    }

    public UiObject getUiObject(UiDevice device) {
        return device.findObject(getUiSelector());
    }

    public BySelector getBySelector() {
        return mBySelector;
    }
}
