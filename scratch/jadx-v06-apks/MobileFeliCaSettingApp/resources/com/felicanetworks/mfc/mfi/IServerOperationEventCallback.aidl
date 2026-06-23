//
// IServerOperationEventCallback.aidl
//
// Copyright 2020 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * サーバ契機処理のコールバック用AIDLインターフェースです。
 * <p>
 */
interface IServerOperationEventCallback {
    void onSuccess();
    void onError(int id, in String msg);
}
