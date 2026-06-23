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
