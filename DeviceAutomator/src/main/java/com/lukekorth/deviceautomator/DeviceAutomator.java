package com.lukekorth.deviceautomator;

import android.content.Intent;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.uiautomator.Until.hasObject;
import static junit.framework.Assert.assertTrue;
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
     * Asserts that the foreground app has the given package name. Waits for up to 5 seconds for the
     * given package to become the foreground app.
     *
     * @param packageName package name to check against the foreground app.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator checkForegroundAppIs(String packageName) {
        return checkForegroundAppIs(packageName, 5000);
    }

    /**
     * Asserts that the foreground app has the given package name. Waits for up to the given timeout
     * for the given package to become the foreground app.
     *
     * @param packageName package name to check against the foreground app.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator checkForegroundAppIs(String packageName, long timeout) {
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000);
        assertTrue(mDevice.hasObject(By.pkg(packageName).depth(0)));
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);

        mDevice.wait(hasObject(By.pkg(intent.getPackage()).depth(0)), timeout);

        return this;
    }

    /**
     * Waits for the ui element specified in {@link #onDevice(UiObjectMatcher)} to be visible with
     * a timeout of 5 seconds.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator waitForExists() {
        waitForExists(5000);
        return this;
    }

    /**
     * Waits for the ui element specified in {@link #onDevice(UiObjectMatcher)} to be visible with
     * the given timeout.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator waitForExists(long timeout) {
        mMatcher.getUiObject(mDevice).waitForExists(timeout);
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
        action.perform(mMatcher.getUiSelector(), mMatcher.getUiObject(mDevice));
    }

    /**
     * @param actions the {@link AutomatorAction}s to perform on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void perform(AutomatorAction... actions) {
        for (AutomatorAction action : actions) {
            action.perform(mMatcher.getUiSelector(), mMatcher.getUiObject(mDevice));
        }
    }

    /**
     * @param assertion the {@link AutomatorAssertion} to assert on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void check(AutomatorAssertion assertion) {
        assertion.check(mMatcher.getUiObject(mDevice));
    }

    /**
     * @param assertions the {@link AutomatorAssertion}s to assert on the ui element specified in
     *        {@link #onDevice(UiObjectMatcher)}.
     */
    public void check(AutomatorAssertion... assertions) {
        for (AutomatorAssertion assertion : assertions) {
            assertion.check(mMatcher.getUiObject(mDevice));
        }
    }

    /**
     * Simulates a short press of a key code for each character of the text.
     *
     * @param text text to type.
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator typeText(String text) {
        KeyEvent[] events = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD)
                .getEvents(text.toCharArray());
        for (KeyEvent event : events) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mDevice.pressKeyCode(event.getKeyCode());
            }
        }

        return this;
    }

    /**
     * Simulates a short press on the BACK button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressBack() {
        mDevice.pressBack();
        return this;
    }

    /**
     * Simulates a short press on the MENU button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressMenu() {
        mDevice.pressMenu();
        return this;
    }

    /**
     * Simulates a short press on the Recent Apps button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressRecentApps() {
        try {
            mDevice.pressRecentApps();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Simulates a short press on the SEARCH button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressSearch() {
        mDevice.pressSearch();
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
     * Simulates a short press on the DELETE key.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDelete() {
        mDevice.pressDelete();
        return this;
    }

    /**
     * Simulates a short press on the TAB key.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressTab() {
        typeText("\t");
        return this;
    }

    /**
     * Simulates a short press on the DOWN button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDPadDown() {
        mDevice.pressDPadDown();
        return this;
    }

    /**
     * Simulates a short press on the CENTER button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDPadCenter() {
        mDevice.pressDPadCenter();
        return this;
    }

    /**
     * Simulates a short press on the LEFT button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDPadLeft() {
        mDevice.pressDPadLeft();
        return this;
    }

    /**
     * Simulates a short press on the RIGHT button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDPadRight() {
        mDevice.pressDPadRight();
        return this;
    }

    /**
     * Simulates a short press on the UP button.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator pressDPadUp() {
        mDevice.pressDPadUp();
        return this;
    }

    /**
     * Opens the notification shade.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator openNotification() {
        mDevice.openNotification();
        return this;
    }

    /**
     * Opens the Quick Settings shade.
     *
     * @return {@link DeviceAutomator} for method chaining.
     */
    public DeviceAutomator openQuickSettings() {
        mDevice.openQuickSettings();
        return this;
    }

    /**
     * Checks the power manager if the screen is ON.
     *
     * @return {@code true} if the screen is ON else {@code false}
     */
    public boolean isScreenOn() {
        try {
            return mDevice.isScreenOn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
