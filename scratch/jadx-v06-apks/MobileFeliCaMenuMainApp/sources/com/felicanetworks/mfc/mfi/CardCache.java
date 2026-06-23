package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.db.EntityCardCache;
import com.felicanetworks.mfc.mfi.db.MfiDatabase;
import com.felicanetworks.mfc.mfi.db.MfiDatabaseSingleton;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CardCache {
    private static final String FILE_NAME = "mfi_card_data";

    public static class CardList {
        public long cachedTime;
        public Map<String, CardIdentifiableInfo.Cache> cardIdInfoMap;
        public Map<String, CompleteCardInfo.Cache> cardInfoMap;
    }

    private static class SaveData implements Serializable {
        private static final long serialVersionUID = 726460738355826983L;
        private String accountId;
        private String appCallerInfo;
        private String appIdInfo;
        private CardIdentifiableInfo.Cache[] cardIdInfoArray;
        private CompleteCardInfo.Cache[] cardInfoArray;

        private SaveData() {
        }
    }

    public static void cache(String str, String str2, String str3, Map<String, CompleteCardInfo.Cache> map, Map<String, CardIdentifiableInfo.Cache> map2) {
        LogMgr.log(4, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        if (map == null || map2 == null || str3 == null) {
            LogMgr.log(4, "%s Maps=%s,%s,Account=%s", "900", map, map2, str3);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, CompleteCardInfo.Cache>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Map.Entry<String, CardIdentifiableInfo.Cache>> it2 = map2.entrySet().iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getValue());
        }
        SaveData saveData = new SaveData();
        saveData.appCallerInfo = str;
        saveData.appIdInfo = str2;
        saveData.accountId = str3;
        saveData.cardInfoArray = (CompleteCardInfo.Cache[]) arrayList.toArray(new CompleteCardInfo.Cache[0]);
        saveData.cardIdInfoArray = (CardIdentifiableInfo.Cache[]) arrayList2.toArray(new CardIdentifiableInfo.Cache[0]);
        EntityCardCache entityCardCache = new EntityCardCache();
        entityCardCache.id = str2;
        entityCardCache.version = getVersion();
        entityCardCache.cached_time = System.currentTimeMillis();
        entityCardCache.serialized_data = convertObjectToByteArray(saveData);
        if (entityCardCache.serialized_data != null) {
            MfiDatabase mfiDatabaseSingleton = MfiDatabaseSingleton.getInstance(FelicaAdapter.getInstance());
            mfiDatabaseSingleton.cardCacheDao().insert(entityCardCache);
            mfiDatabaseSingleton.cardCacheDao().cutOffOverLimitRecords();
        }
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0088 A[Catch: IOException -> 0x008c, TRY_LEAVE, TryCatch #6 {IOException -> 0x008c, blocks: (B:26:0x0083, B:28:0x0088), top: B:42:0x0083 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static byte[] convertObjectToByteArray(java.lang.Object r8) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "701 "
            java.lang.String r1 = ":"
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            r3 = 0
            r4 = 2
            java.io.ObjectOutputStream r5 = new java.io.ObjectOutputStream     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48
            r5.writeObject(r8)     // Catch: java.io.IOException -> L44 java.lang.Throwable -> L81
            byte[] r3 = r2.toByteArray()     // Catch: java.io.IOException -> L44 java.lang.Throwable -> L81
            r2.close()     // Catch: java.io.IOException -> L1e
            r5.close()     // Catch: java.io.IOException -> L1e
            goto L80
        L1e:
            r8 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
        L24:
            r2.append(r0)
            java.lang.Class r0 = r8.getClass()
            java.lang.String r0 = r0.getSimpleName()
            r2.append(r0)
            r2.append(r1)
            java.lang.String r8 = r8.getMessage()
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            com.felicanetworks.mfc.util.LogMgr.log(r4, r8)
            goto L80
        L44:
            r8 = move-exception
            goto L4a
        L46:
            r8 = move-exception
            goto L83
        L48:
            r8 = move-exception
            r5 = r3
        L4a:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L81
            r6.<init>()     // Catch: java.lang.Throwable -> L81
            java.lang.String r7 = "700 "
            r6.append(r7)     // Catch: java.lang.Throwable -> L81
            java.lang.Class r7 = r8.getClass()     // Catch: java.lang.Throwable -> L81
            java.lang.String r7 = r7.getSimpleName()     // Catch: java.lang.Throwable -> L81
            r6.append(r7)     // Catch: java.lang.Throwable -> L81
            r6.append(r1)     // Catch: java.lang.Throwable -> L81
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L81
            r6.append(r8)     // Catch: java.lang.Throwable -> L81
            java.lang.String r8 = r6.toString()     // Catch: java.lang.Throwable -> L81
            com.felicanetworks.mfc.util.LogMgr.log(r4, r8)     // Catch: java.lang.Throwable -> L81
            r2.close()     // Catch: java.io.IOException -> L79
            if (r5 == 0) goto L80
            r5.close()     // Catch: java.io.IOException -> L79
            goto L80
        L79:
            r8 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            goto L24
        L80:
            return r3
        L81:
            r8 = move-exception
            r3 = r5
        L83:
            r2.close()     // Catch: java.io.IOException -> L8c
            if (r3 == 0) goto Lb1
            r3.close()     // Catch: java.io.IOException -> L8c
            goto Lb1
        L8c:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            java.lang.Class r0 = r2.getClass()
            java.lang.String r0 = r0.getSimpleName()
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r2.getMessage()
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            com.felicanetworks.mfc.util.LogMgr.log(r4, r0)
        Lb1:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.CardCache.convertObjectToByteArray(java.lang.Object):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00c5 A[Catch: IOException -> 0x00c9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00c9, blocks: (B:36:0x00c0, B:38:0x00c5), top: B:43:0x00c0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.Object convertByteArrayToObject(byte[] r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 239
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.CardCache.convertByteArrayToObject(byte[]):java.lang.Object");
    }

    public static CardList load(String str, String str2, String str3) {
        return load(str, str2, str3, false);
    }

    public static CardList loadOnly(String str, String str2, String str3) {
        return load(str, str2, str3, true);
    }

    private static CardList load(String str, String str2, String str3, boolean z) {
        SaveData saveData;
        LogMgr.log(4, "000 " + str + ":" + str2 + ":" + str3);
        CardList cardList = null;
        if (str == null) {
            LogMgr.log(2, "701 appCallerInfo is null.");
            return null;
        }
        if (str2 == null) {
            LogMgr.log(2, "702 appIdInfo is null.");
            return null;
        }
        if (str3 == null) {
            LogMgr.log(2, "703 accountId is null.");
            return null;
        }
        if (!z) {
            CacheUtil.deleteFiles(FelicaAdapter.getInstance().getCacheDir(), FILE_NAME, null);
            CacheUtil.deleteFiles(FelicaAdapter.getInstance().getFilesDir(), FILE_NAME, null);
        }
        MfiDatabase mfiDatabaseSingleton = MfiDatabaseSingleton.getInstance(FelicaAdapter.getInstance());
        EntityCardCache entityCardCacheSelect = mfiDatabaseSingleton.cardCacheDao().select(str2);
        if (!z) {
            mfiDatabaseSingleton.cardCacheDao().delete(str2);
        }
        try {
        } catch (Exception e) {
            LogMgr.log(2, "730 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            saveData = null;
        }
        if (entityCardCacheSelect == null) {
            LogMgr.log(6, "997 The data is not cached.");
            return null;
        }
        if (entityCardCacheSelect.version != null && !entityCardCacheSelect.version.isEmpty()) {
            if (!getVersion().equals(entityCardCacheSelect.version)) {
                LogMgr.log(6, "998 The cached data is different from the current version.");
                return null;
            }
            if (entityCardCacheSelect.serialized_data != null && entityCardCacheSelect.serialized_data.length != 0) {
                saveData = (SaveData) convertByteArrayToObject(entityCardCacheSelect.serialized_data);
                if (saveData != null) {
                    if (str.equals(saveData.appCallerInfo) && str2.equals(saveData.appIdInfo) && str3.equals(saveData.accountId)) {
                        cardList = new CardList();
                        cardList.cardInfoMap = new HashMap();
                        for (CompleteCardInfo.Cache cache : saveData.cardInfoArray) {
                            cardList.cardInfoMap.put(cache.cid, cache);
                        }
                        cardList.cardIdInfoMap = new HashMap();
                        for (CardIdentifiableInfo.Cache cache2 : saveData.cardIdInfoArray) {
                            cardList.cardIdInfoMap.put(cache2.serviceId, cache2);
                        }
                        cardList.cachedTime = entityCardCacheSelect.cached_time;
                    } else {
                        LogMgr.log(6, "713 Not match caller application.");
                    }
                } else {
                    LogMgr.log(6, "712 SaveData is null.");
                }
                LogMgr.log(4, "999");
                return cardList;
            }
            LogMgr.log(2, "720 Serialized data was recorded empty.");
            return null;
        }
        LogMgr.log(2, "710 A non-versioned record has been read.");
        return null;
    }

    private static String getVersion() {
        return FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
    }
}
