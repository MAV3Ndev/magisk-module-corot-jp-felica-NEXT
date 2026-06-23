//
// ILogoutEventCallback.aidl
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * ログアウトコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ILogoutEventCallback {
    void onSuccess();
    void onError(int id, in String msg);
}
