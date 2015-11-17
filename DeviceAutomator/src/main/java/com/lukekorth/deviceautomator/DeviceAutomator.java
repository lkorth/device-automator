package com.lukekorth.deviceautomator;

import android.content.Intent;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.Until;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.uiautomator.Until.hasObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * The entry point for using the DeviceAutomator library.
 */
public class DeviceAutomator {

    private UiDevice mDevice;
    private UiObjectMatcher mMatcher;

    public DeviceAutomator(UiDevice device, UiObjectMatcher matcher) {
        mDevice = device;
        mMatcher = matcher;
    }

    /**
     * @return {@link DeviceAutomator} without a {@link UiObjectMatcher}. Suitable for performing
     *         global actions and launching apps.
     */
    public static DeviceAutomator onDevice() {
        return onDevice(null);
    }

    /**
     * @param matcher {@link UiObjectMatcher} used to specify the ui element to interact with.
     * @return {@link DeviceAutomator} for the supplied {@link UiObjectMatcher}. All further methods
     *         called on this instance of {@link DeviceAutomator} will interact with the supplied
     *         {@link UiObjectMatcher} only.
     */
    public static DeviceAutomator onDevice(UiObjectMatcher matcher) {
        return new DeviceAutomator(UiDevice.getInstance(getInstrumentation()), matcher);
    }

    /**
     * Presses the home button and waits for the launcher with a timeout of 5 seconds.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator onHomeScreen() {
        return onHomeScreen(5000);
    }

    /**
     * Presses the home button and waits for the launcher with the given timeout.
     *
     * @param timeout length of time in milliseconds to wait for the launcher to appear before
     *        timing out.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator onHomeScreen(long timeout) {
        mDevice.pressHome();

        String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(hasObject(By.pkg(launcherPackage).depth(0)), timeout);

        return this;
    }

    /**
     * Simulates a short press on the ENTER key.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressEnter() {
        mDevice.pressEnter();
        return this;
    }

    /**
     * Launches the app with the given package name and waits for it to start with a timeout of 5 seconds.
     *
     * @param packageName package name of the app to launch.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator launchApp(String packageName) {
        return launchApp(packageName, 5000);
    }

    /**
     * Launches the app with the given package name and waits for it to start with the given timeout.
     *
     * @param packageName package name of the app to launch.
     * @param timeout length of time in milliseconds to wait for the app to appear before timing out.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator launchApp(String packageName, long timeout) {
        return launchApp(getContext().getPackageManager().getLaunchIntentForPackage(packageName), timeout);
    }

    /**
     * Launches the intent and waits for it to start with a timeout of 5 seconds.
     *
     * @param intent {@link Intent} to launch
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator launchApp(Intent intent) {
        return launchApp(intent, 5000);
    }

    /**
     * Launches the intent and waits for it to start with the given timeout.
     *
     * @param intent {@link Intent} to launch
     * @param timeout length of time in milliseconds to wait for the app to appear before timing out.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator launchApp(Intent intent, long timeout) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);

        mDevice.wait(hasObject(By.pkg(intent.getPackage()).depth(0)), timeout);

        return this;
    }

    /**
     * Waits for the ui element specified in {@link #onDevice(UiObjectMatcher)} to be enabled with
     * a timeout of 5 seconds.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator waitForEnabled() {
        return waitForEnabled(5000);
    }

    /**
     * Waits for the ui element specified in {@link #onDevice(UiObjectMatcher)} to be enabled with
     * the given timeout.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator waitForEnabled(final long timeout) {
        mDevice.findObject(mMatcher.getBySelector()).wait(Until.enabled(true), timeout);
        return this;
    }

    /**
     * @param action the {@link AutomatorAction} to perform on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void perform(AutomatorAction action) {
        action.perform(getUiObject());
    }

    /**
     * @param actions the {@link AutomatorAction}s to perform on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void perform(AutomatorAction... actions) {
        for (AutomatorAction action : actions) {
            action.perform(getUiObject());
        }
    }

    /**
     * @param assertion the {@link AutomatorAssertion} to assert on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void check(AutomatorAssertion assertion) {
        assertion.check(getUiObject());
    }

    /**
     * @param assertions the {@link AutomatorAssertion}s to assert on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void check(AutomatorAssertion... assertions) {
        for (AutomatorAssertion assertion : assertions) {
            assertion.check(getUiObject());
        }
    }

    private UiObject getUiObject() {
        return mMatcher.getUiObject(mDevice);
    }
}
