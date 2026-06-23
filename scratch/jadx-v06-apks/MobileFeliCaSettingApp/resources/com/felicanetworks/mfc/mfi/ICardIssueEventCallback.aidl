//
// ICardIssueEventCallback.aidl
//
// Copyright 2017 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfo;

/**
 * カード発行処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardIssueEventCallback {
    /**
     * カード発行成功時に呼ばれるコールバックです。
     */
    void onSuccess(in CardInfo cardInfo);
    /**
     * カード発行失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
