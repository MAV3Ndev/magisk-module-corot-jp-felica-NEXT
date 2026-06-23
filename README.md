[English](README_en.md)

#### FeliCa Enabler for corot (NEXT)
Xiaomi 13T Pro 日本版 (23088PND5R) 用の FeliCa (おサイフケータイ) 有効化パッチです。

このモジュールは、おサイフケータイのデバイスサポートと初期化チェックに必要な、**公式のモバイルFeliCaアプリ**、FeliCa設定ファイル、日本モデル用プロパティ、およびダミーのAndroidフレームワークファイルをインストールします。
過去のバージョンとは異なり、この「NEXT」バージョンでは**無改造の公式APK**を使用しているため、Google Playストアからアプリを通常通りアップデートすることが可能です。

HyperOS 3 / Android 16 では、おサイフケータイは `/vendor/etc/felica` からFeliCa設定を読み込みます。本モジュールはこのパスに設定ファイルを配置します。

**KernelSUユーザーは、このモジュールをインストールする前に `mountify` をインストールする必要があります。**
KernelSU内蔵のMetaマウントは `/vendor` 配下に新しいディレクトリをマウントできないため、`mountify` (OverlayFS) 経由で設定をマウントしない限り、おサイフケータイのデバイスサポートチェックに失敗し続けます。

#### 動作環境

- **HyperTN 3.3 Taiwan Version** にて動作確認済みです。
- Xiaomi.eu (EuROM) などの他のカスタムROMでも動作するはずです。

#### 免責事項・クレジット

本モジュールは **Gemini 3.1 Pro** の補助のもとで作成・リファクタリングされました。AI (AI Slop) の介入により意図せぬ不具合が発生する可能性がありますので、ご利用の際はソースコードを参照の上、**自己責任**でご使用ください。

#### 注意事項

FeliCa関連のアプリをDeny List (Magisk Hide等) に追加しないでください。

インストールして再起動した後、Androidで以下の機能が報告されることを確認してください：

```sh
pm list features | grep -E 'android.hardware.nfc|android.hardware.se.omapi.ese'
```

FeliCaクライアントと設定ファイルが可視状態になっているかも確認できます：

```sh
pm path com.felicanetworks.mfc
ls /vendor/etc/felica
cat /vendor/etc/felica/common.cfg
ls /system/framework/com.felicanetworks.felica.jar
cmd package dump com.felicanetworks.mfc | grep -A3 -i "uses-libraries"
```
