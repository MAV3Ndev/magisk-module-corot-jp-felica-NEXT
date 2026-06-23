//
// IInitializedEventCallback
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * 端末の初期設定用AIDLインタフェースです。
 * <p>
 */
oneway interface IInitializedEventCallback {
    void onSuccess();
    void onError(int id, in String msg);
}
