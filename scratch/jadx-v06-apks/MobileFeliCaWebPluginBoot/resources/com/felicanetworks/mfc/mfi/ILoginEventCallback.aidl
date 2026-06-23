//
// ILoginEventCallback
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * ログインコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ILoginEventCallback {
    void onSuccess();
    void onError(int id, in String msg);
}
