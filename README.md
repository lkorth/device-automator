# device-automator

 [![Build Status](https://travis-ci.org/lkorth/device-automator.svg?branch=master)](https://travis-ci.org/lkorth/device-automator)

Device Automator is an Android library built on top of the [UI Automator testing
framework](http://developer.android.com/training/testing/ui-testing/uiautomator-testing.html).
Device Automator provides an easy to use syntax for writing UI Automator tests that interact across
apps and the device itself. The Device Automator API very closely resembles the
[Espresso](https://google.github.io/android-testing-support-library/docs/espresso/basics/index.html)
API and similarly encourages test authors to think in terms of what a user might do while
interacting with the application - locating UI elements and interacting with them.

## Setup

### Download device-automator

device-automator is in an early state of development and is available via [JitPack](https://jitpack.io).

Add the following to your repositories in your `build.gradle` file:

```groovy
repositories {
  // ...
  maven { url "https://jitpack.io" }
}
```

Add the dependency in your `build.gradle` file:

```groovy
dependencies {
  androidTestCompile 'com.github.lkorth:device-automator:master-SNAPSHOT'
}
```

### Set the instrumentation runner

Add the following line to your `build.gradle` file in `android.defaultConfig`:

```
testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
```

## Writing Tests

It's recommended that you start every test from your device's home screen. To do that, run the
following before each test:

```java
onDevice().onHomeScreen();
```

To launch an app, call:

```java
onDevice().launchApp("com.myapp.package");
```

To click on a view:

```java
onDevice(withText("My Button")).perform(click());
```

To type text:

```java
onDevice(withText("Enter text here")).perform(setText("foobar"));
```

To make assertions after interacting:

```java
onDevice(withContentDescription("message field")).check(text(containsString("my message")));
```
