//
// ICardDisableV2EventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * カード非活性化処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardDisableV2EventCallback {
    /**
     * カード非活性化成功時に呼ばれるコールバックです。
     */
    void onSuccess(in String cardInfoJson);
    /**
     * カード非活性化失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
