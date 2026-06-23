#### FeliCa Enabler for corot
FeliCa enable patch for Xiaomi 13T Pro Japan (23088PND5R).

This module installs Mobile FeliCa apps, FeliCa configuration files, Japanese model properties, and Android hardware feature declarations required for Osaifu-Keitai device support checks.

On HyperOS 3 / Android 16, Osaifu-Keitai loads FeliCa configuration from `/product/etc/felica`. This module therefore installs the product-partition layout through the `system/product` mirror.

KernelSU users must install `mountify` before installing this module. KernelSU's built-in Meta mount does not add the new `/product/etc/felica` and Mobile FeliCa app paths on this device, so Osaifu-Keitai keeps failing device support checks unless modules are mounted through OverlayFS.

#### NOTICE

Do not add any FeliCa apps into Deny List.

After installation and reboot, Android should report these features:

```sh
pm list features | grep -E 'android.hardware.nfc|android.hardware.se.omapi.ese'
```

The FeliCa configuration must also be visible:

```sh
ls /product/etc/felica
cat /product/etc/felica/common.cfg
```
