//
// ICardDisableEventCallback.aidl
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfo;

/**
 * カード非活性化処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardDisableEventCallback {
    /**
     * カード非活性化成功時に呼ばれるコールバックです。
     */
    void onSuccess(in CardInfo cardInfo);
    /**
     * カード非活性化失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
