//
// ILinkageDataListEventCallback
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * リンケージデータ一覧の取得処理用AIDLインタフェースです。
 * <p>
 */
oneway interface ILinkageDataListEventCallback {
    void onSuccess(in String[] linkageData);
    void onError(int id, in String msg);
}
