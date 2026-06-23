//
// ICardDeleteEventCallback.aidl
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfo;

/**
 * カード削除処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardDeleteEventCallback {
    /**
     * カード削除成功時に呼ばれるコールバックです。
     */
    void onSuccess();
    /**
     * カード削除失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
