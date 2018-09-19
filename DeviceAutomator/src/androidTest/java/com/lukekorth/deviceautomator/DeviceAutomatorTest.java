package com.lukekorth.deviceautomator;

import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
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

    @Before
    public void setup() {
        mUiDevice = mock(UiDevice.class);
    }

    @Test
    public void exists_returnsTrueIfObjectExists() {
        UiObject object = mock(UiObject.class);
        when(object.exists()).thenReturn(true);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);

        assertTrue(getDeviceAutomator(matcher).exists());
    }

    @Test
    public void exists_returnsFalseIfObjectDoesNotExist() {
        UiObject object = mock(UiObject.class);
        when(object.exists()).thenReturn(false);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);

        assertFalse(getDeviceAutomator(matcher).exists());
    }

    @Test
    public void exists_doesNotThrowIfObjectIsNull() {
        assertFalse(getDeviceAutomator(null).exists());
    }

    @Test
    public void isChecked_returnsTrueIfObjectIsChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isChecked()).thenReturn(true);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);
        DeviceAutomator deviceAutomator = getDeviceAutomator(matcher);

        assertTrue(deviceAutomator.isChecked());
    }

    @Test
    public void isChecked_returnsFalseIfObjectIsNotChecked() throws UiObjectNotFoundException {
        UiObject object = mock(UiObject.class);
        when(object.isChecked()).thenReturn(false);
        UiObjectMatcher matcher = mock(UiObjectMatcher.class);
        when(matcher.getUiObject(mUiDevice)).thenReturn(object);
        DeviceAutomator deviceAutomator = getDeviceAutomator(matcher);

        assertFalse(deviceAutomator.isChecked());
    }

    @Test
    public void typeText_supportsSymbols() {
        getDeviceAutomator(null).typeText("`~!@#$%^&*()-_=+[]\\|;:'\",<.>/?");

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

    private DeviceAutomator getDeviceAutomator(UiObjectMatcher matcher) {
        return new DeviceAutomator(mUiDevice, matcher);
    }
}
