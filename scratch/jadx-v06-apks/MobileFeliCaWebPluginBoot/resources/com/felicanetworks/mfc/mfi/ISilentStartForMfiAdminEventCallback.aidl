//
// ISilentStartForMfiAdminEventCallback
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * 管理者権限でのMFIサーバの利用開始処理用AIDLインタフェースです。
 * <p>
 */
oneway interface ISilentStartForMfiAdminEventCallback {
    void onSuccess();
    void onSuccessNoLogin();
    void onRequestActivity(in Intent intent);
    void onError(int id, in String msg);
}
