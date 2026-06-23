//
// ICardAccessEventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfo;

/**
 * カード読み書き処理用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardAccessEventCallback {
    void onSuccess();
    void onError(int id, in String msg);
}
