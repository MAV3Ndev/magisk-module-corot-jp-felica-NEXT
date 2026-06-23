//
// IUnsupportMfiService1CardDeleteEventCallback.aidl
//
// Copyright 2020 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//
package com.felicanetworks.mfc.mfi;

/**
 * チップに存在するMFI管理外のサービス事業者1カードを削除する処理のコールバック用AIDLインターフェースです。<br>
 * <p>
 */
oneway interface IUnsupportMfiService1CardDeleteEventCallback {
    /**
     * MFI管理外のサービス事業者1カードの削除成功時に呼ばれるコールバックです。
     */
    void onSuccess();
    /**
     * MFI管理外のサービス事業者1カードの削除失敗時に呼ばれるコールバックです。
     */
    void onError(int type, String msg);
}
