[日本語 (Japanese)](README.md)

#### FeliCa Enabler for corot (NEXT)
FeliCa enable patch for Xiaomi 13T Pro Japan (23088PND5R).

This module installs official Mobile FeliCa apps, FeliCa configuration files, Japanese model properties, and dummy Android framework files required for Osaifu-Keitai device support and initialization checks.
Unlike the previous version, this NEXT version uses official unpatched APKs, allowing you to update the apps via the Google Play Store normally.

On HyperOS 3 / Android 16, Osaifu-Keitai loads FeliCa configuration from `/vendor/etc/felica`. This module installs the files there.

KernelSU users must install `mountify` before installing this module. KernelSU's built-in Meta mount cannot mount new directories under `/vendor`, so Osaifu-Keitai keeps failing unless configuration is mounted through `mountify` (OverlayFS).

#### Compatibility

- Confirmed working on **HyperTN 3.3 Taiwan Version**.
- Should also work on other custom ROMs like Xiaomi.eu (EuROM).

#### Disclaimer & Credits

This module was created and refactored with the assistance of **Gemini 3.1 Pro**. Please be aware that AI-generated code (AI Slop) may introduce unintended bugs. Please review the source code and use it at your **own risk**.

#### NOTICE

Do not add any FeliCa apps into Deny List.

After installation and reboot, Android should report these features:

```sh
pm list features | grep -E 'android.hardware.nfc|android.hardware.se.omapi.ese'
```

The FeliCa Client and configuration must also be visible:

```sh
pm path com.felicanetworks.mfc
ls /vendor/etc/felica
cat /vendor/etc/felica/common.cfg
ls /system/framework/com.felicanetworks.felica.jar
cmd package dump com.felicanetworks.mfc | grep -A3 -i "uses-libraries"
```
