//
// IPipeEventCallback.aidl
//
// Copyright 2019 FeliCa Networks, Inc.
// All Rights Reserved.
// FeliCa Networks, Inc. Proprietary/Confidential.
//

package com.felicanetworks.mfc.mfi;

import android.os.ParcelFileDescriptor;

/**
 * AGP2以降のカード一覧取得、付加情報付カード一覧取得など、パイプで一覧を送受信コールバック用の
 * AIDLインタフェースです。
 * <p>
 */
oneway interface IPipeEventCallback {
    void onStart(in ParcelFileDescriptor fd);
    void onError(int id, in String msg);
}
