//
// ICachedCardEnableEventCallback.aidl
//
// Copyright 2020 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * キャッシュされたカード活性化処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICachedCardEnableEventCallback {
    /**
     * カード活性化成功時に呼ばれるコールバックです。
     */
    void onSuccess(in String enableCardInfoJson, in String disableCardInfoJson);
    /**
     * カード活性化失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
