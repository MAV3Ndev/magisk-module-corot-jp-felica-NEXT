package com.felicanetworks.mfc.mfi.mfw.i.fbl;

import android.database.Cursor;
import android.net.Uri;
import com.felicanetworks.mfc.mfi.BadPropertyException;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstructDeviceIdentificationData {
    public abstract String get() throws BadPropertyException;

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:29:0x00ca */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:51:0x004d */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    protected String getFromProvider() throws Throwable {
        String string;
        String skuUrl = Property.getSkuUrl();
        String skuKeyValue = Property.getSkuKeyValue();
        LogMgr.log(7, "001 skuUrl=" + skuUrl);
        LogMgr.log(7, "002 skuKeyValue=" + skuKeyValue);
        String deviceIdentificationInfo = Property.getDeviceIdentificationInfo();
        LogMgr.log(7, "003 deviceIdentificationInfo=" + deviceIdentificationInfo);
        ?? r8 = 0;
        if (skuUrl == null || skuKeyValue == null) {
            if (skuUrl != null || skuKeyValue != null) {
                LogMgr.log(2, "702 Either skuUrl or skuKeyValue is null.");
                throw new BadPropertyException(200, ObfuscatedMsgUtil.executionPoint());
            }
            if (deviceIdentificationInfo == null) {
                return null;
            }
            LogMgr.log(6, "006 deviceIdentificationData=" + deviceIdentificationInfo);
            return deviceIdentificationInfo;
        }
        try {
            try {
                try {
                    LogMgr.log(6, "003 Get SKU");
                    Cursor cursorQuery = FelicaAdapter.getInstance().getContentResolver().query(Uri.parse(skuUrl), null, null, null, null);
                    try {
                        if (cursorQuery == null) {
                            throw new BadPropertyException(238, MfiClientCallbackConst.MSG_DEVICE_IDENTIFICATION_DATA_NULL);
                        }
                        cursorQuery.moveToFirst();
                        int columnIndex = cursorQuery.getColumnIndex(skuKeyValue);
                        int type = cursorQuery.getType(columnIndex);
                        LogMgr.log(6, "004 SKU FieldType=" + type);
                        if (1 == type) {
                            string = Integer.toString(cursorQuery.getInt(columnIndex));
                        } else if (3 == type) {
                            string = cursorQuery.getString(columnIndex);
                        } else {
                            throw new BadPropertyException(238, null);
                        }
                        if (string == null) {
                            LogMgr.log(2, "700 deviceIdentificationData is null");
                            throw new BadPropertyException(238, null);
                        }
                        LogMgr.log(6, "005 deviceIdentificationData=" + string);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    } catch (BadPropertyException e) {
                        r8 = cursorQuery;
                        throw e;
                    } catch (Exception e2) {
                        e = e2;
                        LogMgr.log(2, "701 Fail to get SKU");
                        LogMgr.printStackTrace(7, e);
                        throw new BadPropertyException(238, null);
                    }
                } catch (Throwable th) {
                    th = th;
                    if (r8 != 0) {
                        r8.close();
                    }
                    throw th;
                }
            } catch (BadPropertyException e3) {
                throw e3;
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
            r8 = skuUrl;
        }
    }
}
