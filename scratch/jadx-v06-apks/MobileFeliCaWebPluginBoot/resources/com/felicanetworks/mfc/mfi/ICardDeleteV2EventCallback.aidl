//
// ICardDeleteV2EventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * カード削除処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardDeleteV2EventCallback {
    /**
     * カード削除成功時に呼ばれるコールバックです。
     */
    void onSuccess();
    /**
     * カード削除失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
