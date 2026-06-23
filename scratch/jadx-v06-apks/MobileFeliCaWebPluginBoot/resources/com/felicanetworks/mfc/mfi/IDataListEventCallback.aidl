//
// IDataListEventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

/**
 * FY19-02以降のカード一覧取得、付加情報付カード一覧取得など、分割Listで一覧を送受信する
 * コールバック用のAIDLインタフェースです。
 * <p>
 */
interface IDataListEventCallback {
    void onPart(in List<String> partialDataList, boolean more);
    oneway void onFinished(int size);
    oneway void onError(int id, in String msg);
}