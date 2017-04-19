package com.lukekorth.deviceautomator;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DeviceAutomatorTest {

    private UiDevice mUiDevice;
    private DeviceAutomator mDeviceAutomator;

    @Before
    public void setup() {
        mUiDevice = mock(UiDevice.class);
        mDeviceAutomator = new DeviceAutomator(mUiDevice, null);
    }

    @Test
    public void isChecked_returnsTrueIfObjectIsChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isChecked()).thenReturn(true);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);
        mDeviceAutomator = new DeviceAutomator(mUiDevice, matcher);

        assertTrue(mDeviceAutomator.isChecked());
    }

    @Test
    public void isChecked_returnsFalseIfObjectIsNotChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isChecked()).thenReturn(false);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);
        mDeviceAutomator = new DeviceAutomator(mUiDevice, matcher);

        assertFalse(mDeviceAutomator.isChecked());
    }

    @Test
    public void typeText_supportsSymbols() {
        mDeviceAutomator.typeText("`~!@#$%^&*()-_=+[]\\|;:'\",<.>/?");

        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_GRAVE, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_GRAVE, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_1, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_2, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_3, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_4, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_5, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_6, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_7, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_8, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_9, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_0, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_MINUS, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_MINUS, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_EQUALS, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_EQUALS, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_LEFT_BRACKET, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_RIGHT_BRACKET, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_BACKSLASH, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_BACKSLASH, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_SEMICOLON, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_SEMICOLON, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_APOSTROPHE, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_APOSTROPHE, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_COMMA, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_COMMA, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_PERIOD, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_PERIOD, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_SLASH, 0);
        verify(mUiDevice).pressKeyCode(KeyEvent.KEYCODE_SLASH, KeyEvent.META_SHIFT_LEFT_ON | KeyEvent.META_SHIFT_ON);
    }
}
