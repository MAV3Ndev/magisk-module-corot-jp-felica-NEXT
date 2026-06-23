package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.db.EntityCardCache;
import com.felicanetworks.mfc.mfi.db.MfiDatabase;
import com.felicanetworks.mfc.mfi.db.MfiDatabaseSingleton;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
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

    public static void cache(String appCallerInfo, String appIdInfo, String accountId, Map<String, CompleteCardInfo.Cache> cardInfoMap, Map<String, CardIdentifiableInfo.Cache> cardIdInfoMap) {
        LogMgr.log(4, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(2, "%s %s:%s", "700", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        if (cardInfoMap == null || cardIdInfoMap == null || accountId == null) {
            LogMgr.log(4, "%s Maps=%s,%s,Account=%s", "900", cardInfoMap, cardIdInfoMap, accountId);
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, CompleteCardInfo.Cache>> it = cardInfoMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Map.Entry<String, CardIdentifiableInfo.Cache>> it2 = cardIdInfoMap.entrySet().iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getValue());
        }
        SaveData saveData = new SaveData();
        saveData.appCallerInfo = appCallerInfo;
        saveData.appIdInfo = appIdInfo;
        saveData.accountId = accountId;
        saveData.cardInfoArray = (CompleteCardInfo.Cache[]) arrayList.toArray(new CompleteCardInfo.Cache[0]);
        saveData.cardIdInfoArray = (CardIdentifiableInfo.Cache[]) arrayList2.toArray(new CardIdentifiableInfo.Cache[0]);
        EntityCardCache entityCardCache = new EntityCardCache();
        entityCardCache.id = appIdInfo;
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0082 A[Catch: IOException -> 0x0086, TRY_LEAVE, TryCatch #1 {IOException -> 0x0086, blocks: (B:26:0x007d, B:28:0x0082), top: B:33:0x007d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] convertObjectToByteArray(Object obj) throws Throwable {
        ObjectOutputStream objectOutputStream;
        StringBuilder sb;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = null;
        byteArray = null;
        byteArray = null;
        byte[] byteArray = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                try {
                    objectOutputStream.writeObject(obj);
                    byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.close();
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(":");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                } catch (IOException e2) {
                    e = e2;
                    LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    try {
                        byteArrayOutputStream.close();
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                    } catch (IOException e3) {
                        e = e3;
                        sb = new StringBuilder("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(":");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            } catch (Throwable th) {
                th = th;
                objectOutputStream2 = objectOutputStream;
                try {
                    byteArrayOutputStream.close();
                    if (objectOutputStream2 != null) {
                        objectOutputStream2.close();
                    }
                } catch (IOException e4) {
                    LogMgr.log(2, "701 " + e4.getClass().getSimpleName() + ":" + e4.getMessage());
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            objectOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream.close();
            if (objectOutputStream2 != null) {
            }
            throw th;
        }
        return byteArray;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b9 A[Catch: IOException -> 0x00bd, TRY_LEAVE, TryCatch #7 {IOException -> 0x00bd, blocks: (B:37:0x00b4, B:39:0x00b9), top: B:53:0x00b4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Object convertByteArrayToObject(byte[] bArr) throws Throwable {
        ObjectInputStream objectInputStream;
        StringBuilder sb;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream2 = null;
        object = null;
        object = null;
        object = null;
        object = null;
        object = null;
        Object object = null;
        try {
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
                try {
                    object = objectInputStream.readObject();
                    try {
                        byteArrayInputStream.close();
                        objectInputStream.close();
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder("702 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(":");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                } catch (IOException e2) {
                    e = e2;
                    LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    try {
                        byteArrayInputStream.close();
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                    } catch (IOException e3) {
                        e = e3;
                        sb = new StringBuilder("702 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(":");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                } catch (ClassNotFoundException e4) {
                    e = e4;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    try {
                        byteArrayInputStream.close();
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                    } catch (IOException e5) {
                        e = e5;
                        sb = new StringBuilder("702 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(":");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            } catch (Throwable th) {
                th = th;
                objectInputStream2 = objectInputStream;
                try {
                    byteArrayInputStream.close();
                    if (objectInputStream2 != null) {
                        objectInputStream2.close();
                    }
                } catch (IOException e6) {
                    LogMgr.log(2, "702 " + e6.getClass().getSimpleName() + ":" + e6.getMessage());
                }
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            objectInputStream = null;
        } catch (ClassNotFoundException e8) {
            e = e8;
            objectInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream.close();
            if (objectInputStream2 != null) {
            }
            throw th;
        }
        return object;
    }

    public static CardList load(String appCallerInfo, String appIdInfo, String accountId) {
        return load(appCallerInfo, appIdInfo, accountId, false);
    }

    public static CardList loadOnly(String appCallerInfo, String appIdInfo, String accountId) {
        return load(appCallerInfo, appIdInfo, accountId, true);
    }

    private static CardList load(String appCallerInfo, String appIdInfo, String accountId, boolean loadOnly) {
        SaveData saveData;
        LogMgr.log(4, "000 " + appCallerInfo + ":" + appIdInfo + ":" + accountId);
        CardList cardList = null;
        if (appCallerInfo == null) {
            LogMgr.log(2, "701 appCallerInfo is null.");
            return null;
        }
        if (appIdInfo == null) {
            LogMgr.log(2, "702 appIdInfo is null.");
            return null;
        }
        if (accountId == null) {
            LogMgr.log(2, "703 accountId is null.");
            return null;
        }
        if (!loadOnly) {
            CacheUtil.deleteFiles(FelicaAdapter.getInstance().getCacheDir(), FILE_NAME, null);
            CacheUtil.deleteFiles(FelicaAdapter.getInstance().getFilesDir(), FILE_NAME, null);
        }
        MfiDatabase mfiDatabaseSingleton = MfiDatabaseSingleton.getInstance(FelicaAdapter.getInstance());
        EntityCardCache entityCardCacheSelect = mfiDatabaseSingleton.cardCacheDao().select(appIdInfo);
        if (!loadOnly) {
            mfiDatabaseSingleton.cardCacheDao().delete(appIdInfo);
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
                    if (appCallerInfo.equals(saveData.appCallerInfo) && appIdInfo.equals(saveData.appIdInfo) && accountId.equals(saveData.accountId)) {
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
