//
// IUnsupportMfiService1CardExistEventCallback.aidl
//
// Copyright 2020 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//
package com.felicanetworks.mfc.mfi;

/**
 * チップにMFI管理外のサービス事業者1カードが存在するかを確認する処理のコールバック用AIDLインターフェースです。
 * <p>
 */
oneway interface IUnsupportMfiService1CardExistEventCallback {
    /**
     * MFI管理外のサービス事業者1カードの存在確認処理成功時に呼ばれるコールバックです
     */
    void onSuccess(boolean exist, String localPartialCardInfoJson);
    /**
     * MFI管理外のサービス事業者1カードの存在確認処理失敗時に呼ばれるコールバックです。
     */
    void onError(int type, String msg);
}
