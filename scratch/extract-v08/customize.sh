REPLACE="
/product/app/MobileFeliCaClient
/product/etc/felica
"

if [ "$KSU" = "true" ]; then
  if [ ! -d /data/adb/modules/mountify ] && [ ! -d /data/adb/modules_update/mountify ]; then
    abort "! KernelSU requires mountify because this module installs new paths under /product."
  fi
fi
