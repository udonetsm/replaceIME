# Replace Input Method Behavior for OxygenOS

An Xposed module that changes what the **input method (IME) button** in the navigation bar does while the keyboard is shown.

By default, OxygenOS opens a "Select input method" dialog. With this module, tapping the button instead **switches to the next input method or subtype** (for example, the next keyboard language), similar to the globe button in AOSP. If only one entry is available, nothing is switched and the dialog is dismissed.

## Requirements

- OxygenOS 15 (Android 15), root
- An Xposed-compatible framework (LSPosed or Vector)

Tested on OxygenOS 15 (build `CPH2569_15.0.0.1902`) with Magisk 30.7 and Vector 2.2 by JingMatrix.

## Installation

1. Install the APK.
2. Enable the module in Vector (or LSPosed).
3. In the module's scope, select **System Framework**.
4. Reboot.

To uninstall, disable the module and reboot. The default dialog behavior returns.

## How it works

The module hooks `InputMethodMenuControllerExtImpl.showInputMethodMenu` in `system_server`. Instead of showing the picker, it calls the picker's own click listener with the index of the next item in the list, so the switch goes through the same system path as choosing an item manually.

## Limitations

- The hook targets an OxygenOS-specific class. A system update may rename it or change its signature, and the module will stop working until it is updated.
- Language switching only works if your keyboard exposes its languages to the system as subtypes. Otherwise the button cycles between keyboards only.

## Disclaimer

Parts of this module were written with the help of AI. Use it at your own risk.

## License

Add a license of your choice (for example, MIT or GPL-3.0).
