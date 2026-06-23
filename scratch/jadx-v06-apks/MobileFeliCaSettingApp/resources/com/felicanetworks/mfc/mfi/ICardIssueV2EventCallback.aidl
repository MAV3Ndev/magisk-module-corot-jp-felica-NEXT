//
// ICardIssueV2EventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * カード発行処理のコールバック用AIDLインタフェースです。
 * <p>
 */
oneway interface ICardIssueV2EventCallback {
    /**
     * カード発行成功時に呼ばれるコールバックです。
     */
    void onSuccess(in String cardInfoJson);
    /**
     * カード発行失敗時に呼ばれるコールバックです。
     */
    void onError(int id, in String msg);
}
