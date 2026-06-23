### v1 - 2023/12/14
* Initial Release

### v2 - 2024/01/20
* Add SIM2 slot support

### v3 - 2024/01/20
* Rollback SIM2 support

### v4 - 2026/06/23
* Add FeliCa/NFC-F/eSE hardware feature declarations
* Clean duplicate product model property and SIM2 secure element declaration

### v5 - 2026/06/23
* Move FeliCa apps and configuration to the product partition path used by HyperOS 3
* Install FeliCa hardware feature declarations under product permissions

### v6 - 2026/06/23
* Require mountify on KernelSU because built-in Meta mount does not add the new product paths needed by FeliCa
* Drop the legacy Magisk recovery installer layout from release zips

### v7 - 2026/06/23
* Keep only MobileFeliCaClient under product app paths
* Remove bundled Menu, Setting, and WebPlugin system app copies because current Play Store packages are already installed and the old product copies create updated-system-app duplicate state

### v8 - 2026/06/23
* Mark the FeliCa reader/writer path as supported in common.cfg

### v9 - 2026/06/23
* Add the missing com.felicanetworks.felica shared Java library declaration and stub API so Mobile FeliCa Client can load its RF controller classes

### v10 - 2026/06/23
* Mark the GP eSE GetInstanceStatus command as unsupported so Mobile FeliCa Client uses select-response lifecycle detection on corot
