package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.BannerFileProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.BannerImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class OrderBannerWorker extends Thread {
    private ModelContext _context;
    private DatabaseExpert _db;
    private Listener _listener;
    private Type _type;

    interface Listener {
        void onComplete(List<BannerInfo> bannerInfoList);
    }

    enum Type {
        MY_SERVICE { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            DatabaseExpert.BannerSettings setting(DatabaseExpert db) throws DatabaseException {
                return db.getMyServiceBannerSettings();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            List<DatabaseExpert.Banner> list(DatabaseExpert db) throws DatabaseException {
                return db.getMyServiceBannerList();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            DatabaseExpert.BannerPos pos() {
                return DatabaseExpert.BannerPos.MY_SERVICE;
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            BannerFileProtocol.Parameter fileReqParam() {
                return new BannerFileProtocol.Parameter(BannerFileProtocol.Parameter.Type.MY_SERVICE);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            BannerImageProtocol.Parameter imageReqParam(String imgName) {
                return new BannerImageProtocol.Parameter(BannerImageProtocol.Parameter.Type.MY_SERVICE, imgName);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultId() {
                return (String) Sg.getValue(Sg.Key.SETTING_ID_DEFAULT_BANNER_SERVICE);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultScheme() {
                return (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DEFAULT_BANNER_SERVICE);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultImageName() {
                return (String) Sg.getValue(Sg.Key.SETTING_IMAGE_DEFAULT_BANNER_SERVICE);
            }
        },
        NOTIFICATION { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            DatabaseExpert.BannerSettings setting(DatabaseExpert db) throws DatabaseException {
                return db.getNoticeBannerSettings();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            List<DatabaseExpert.Banner> list(DatabaseExpert db) throws DatabaseException {
                return db.getNoticeBannerList();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            DatabaseExpert.BannerPos pos() {
                return DatabaseExpert.BannerPos.NOTIFICATION;
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            BannerFileProtocol.Parameter fileReqParam() {
                return new BannerFileProtocol.Parameter(BannerFileProtocol.Parameter.Type.NOTIFICATION);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            BannerImageProtocol.Parameter imageReqParam(String imgName) {
                return new BannerImageProtocol.Parameter(BannerImageProtocol.Parameter.Type.NOTIFICATION, imgName);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultId() {
                return (String) Sg.getValue(Sg.Key.SETTING_ID_DEFAULT_BANNER_NOTICE);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultScheme() {
                return (String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_URL_DEFAULT_BANNER_NOTICE);
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            String defaultImageName() {
                return (String) Sg.getValue(Sg.Key.SETTING_IMAGE_DEFAULT_BANNER_NOTICE);
            }
        };

        String defaultId() {
            return null;
        }

        String defaultImageName() {
            return null;
        }

        String defaultScheme() {
            return null;
        }

        BannerFileProtocol.Parameter fileReqParam() {
            return null;
        }

        BannerImageProtocol.Parameter imageReqParam(String imgName) {
            return null;
        }

        List<DatabaseExpert.Banner> list(DatabaseExpert db) throws DatabaseException {
            return null;
        }

        DatabaseExpert.BannerPos pos() {
            return null;
        }

        DatabaseExpert.BannerSettings setting(DatabaseExpert db) throws DatabaseException {
            return null;
        }
    }

    OrderBannerWorker(ModelContext context, Type type, Listener listener) {
        this._context = context;
        this._listener = listener;
        this._type = type;
        this._db = context.getOpenedDatabaseExpert();
    }

    protected OrderBannerWorker() {
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[INVOKE]}, finally: {[INVOKE, IGET, INVOKE, INVOKE, INVOKE, IGET, INVOKE, IF] complete} */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0192  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        BannerFileProtocol.Result result;
        BannerImageProtocol.Result result2;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                checkInterrupted();
                String strConvertFromCalendarToString = FuncUtil.convertFromCalendarToString("yyyyMMdd", FuncUtil.getCurrentCalendar());
                DatabaseExpert.BannerSettings bannerSettings = this._type.setting(this._db);
                String str = bannerSettings.upToDate;
                boolean zEquals = strConvertFromCalendarToString.equals(bannerSettings.lastCheckDate);
                boolean z = !zEquals;
                checkInterrupted();
                if (zEquals) {
                    result = null;
                } else {
                    BannerFileProtocol bannerFileProtocol = this._context.getNetworkExpert().getBannerFileProtocol();
                    try {
                        result = bannerFileProtocol.parse(this._context.getNetworkExpert().connect(bannerFileProtocol.create(this._type.fileReqParam())));
                        this._db.setBannerSettings(new DatabaseExpert.BannerSettings(bannerSettings.pos, null, strConvertFromCalendarToString));
                    } catch (NetworkExpertException unused) {
                        this._db.setBannerSettings(new DatabaseExpert.BannerSettings(bannerSettings.pos, null, strConvertFromCalendarToString));
                        z = false;
                        result = null;
                    } catch (Throwable th) {
                        this._db.setBannerSettings(new DatabaseExpert.BannerSettings(bannerSettings.pos, null, strConvertFromCalendarToString));
                        throw th;
                    }
                    if (result == null || str.equals(result.update)) {
                        z = false;
                    }
                }
                if (z) {
                    ArrayList arrayList2 = new ArrayList();
                    for (int i = 0; i < result.banners.size(); i++) {
                        BannerFileProtocol.Result.Banner banner = result.banners.get(i);
                        arrayList2.add(new DatabaseExpert.BannerText(this._type.pos(), banner.id, i, banner.imgName, banner.scheme, banner.start, banner.end));
                    }
                    this._db.setBannerTexts(arrayList2);
                    BannerImageProtocol bannerImageProtocol = this._context.getNetworkExpert().getBannerImageProtocol();
                    List<DatabaseExpert.Banner> list = this._type.list(this._db);
                    ArrayList arrayList3 = new ArrayList();
                    for (Map.Entry<String, String[]> entry : getNeedsImgBanners(result.banners, list).entrySet()) {
                        checkInterrupted();
                        String[] value = entry.getValue();
                        String str2 = value[0];
                        String str3 = value[1];
                        try {
                            result2 = bannerImageProtocol.parse(this._context.getNetworkExpert().connect(bannerImageProtocol.create(this._type.imageReqParam(str2))));
                        } catch (NetworkExpertException unused2) {
                            arrayList3.add(entry.getKey());
                            result2 = null;
                        }
                        if (result2 != null) {
                            this._db.setBanner(new DatabaseExpert.BannerImage(this._type.pos(), entry.getKey(), result2.binary, str3));
                        }
                    }
                    if (arrayList3.isEmpty()) {
                        this._db.setBannerSettings(new DatabaseExpert.BannerSettings(bannerSettings.pos, result.update, null));
                    }
                }
                List<DatabaseExpert.Banner> list2 = this._type.list(this._db);
                if (!list2.isEmpty()) {
                    arrayList.addAll(makeBannerInfoList(list2));
                }
            } catch (InterruptedException unused3) {
                if (arrayList.isEmpty()) {
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                if (arrayList.isEmpty()) {
                }
            }
            if (arrayList.isEmpty()) {
                arrayList.addAll(makeDefault());
            }
            this._listener.onComplete(arrayList);
        } catch (Throwable th2) {
            if (arrayList.isEmpty()) {
                arrayList.addAll(makeDefault());
            }
            this._listener.onComplete(arrayList);
            throw th2;
        }
    }

    private List<BannerInfo> makeDefault() {
        ArrayList arrayList = new ArrayList();
        String strDefaultId = this._type.defaultId();
        String strDefaultScheme = this._type.defaultScheme();
        String strDefaultImageName = this._type.defaultImageName();
        Context androidContext = this._context.getAndroidContext();
        Resources resources = androidContext.getResources();
        arrayList.add(new BannerInfo(strDefaultId, strDefaultImageName, BitmapFactory.decodeResource(resources, resources.getIdentifier(strDefaultImageName, "drawable", androidContext.getPackageName())), strDefaultScheme, "", "", ""));
        return arrayList;
    }

    private List<BannerInfo> makeBannerInfoList(List<DatabaseExpert.Banner> dbBannerList) {
        ArrayList arrayList = new ArrayList();
        for (DatabaseExpert.Banner banner : dbBannerList) {
            try {
                Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(banner.imgBinary, 0, banner.imgBinary.length);
                if (bitmapDecodeByteArray != null) {
                    arrayList.add(new BannerInfo(banner.id, banner.imgName, bitmapDecodeByteArray, banner.scheme, banner.start, banner.end, banner.update));
                }
            } catch (Throwable th) {
                LogUtil.warning(th);
            }
        }
        return arrayList;
    }

    private Map<String, String[]> getNeedsImgBanners(List<BannerFileProtocol.Result.Banner> netBannerList, List<DatabaseExpert.Banner> dbBannerList) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        for (DatabaseExpert.Banner banner : dbBannerList) {
            map2.put(banner.id, banner);
        }
        for (BannerFileProtocol.Result.Banner banner2 : netBannerList) {
            if (!map2.containsKey(banner2.id)) {
                map.put(banner2.id, new String[]{banner2.imgName, banner2.updated});
            } else {
                DatabaseExpert.Banner banner3 = (DatabaseExpert.Banner) map2.get(banner2.id);
                if (banner3.imgBinary.length == 0) {
                    map.put(banner2.id, new String[]{banner2.imgName, banner2.updated});
                } else if (!banner2.updated.equals(banner3.update)) {
                    map.put(banner2.id, new String[]{banner2.imgName, banner2.updated});
                }
            }
        }
        return map;
    }
}
