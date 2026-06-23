//
// ICardListEventCallback.aidl
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfo;

/**
 * ログインコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardListEventCallback {
    void onSuccess(in CardInfo[] cards);
    void onError(int id, in String msg);
}
