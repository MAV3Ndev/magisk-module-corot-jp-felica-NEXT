//
// ISilentStartEventCallback
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * サイレントスタートコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ISilentStartEventCallback {
    void onSuccess();
    void onRequestActivity(in Intent intent);
    void onError(int id, in String msg);
}
