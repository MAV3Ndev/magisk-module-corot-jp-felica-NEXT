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
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OrderBannerWorker extends Thread {
    private ModelContext _context;
    private DatabaseExpert _db;
    private Listener _listener;
    private Type _type;

    interface Listener {
        void onComplete(List<BannerInfo> list);
    }

    enum Type {
        MY_SERVICE { // from class: com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            DatabaseExpert.BannerSettings setting(DatabaseExpert databaseExpert) throws DatabaseException {
                return databaseExpert.getMyServiceBannerSettings();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            List<DatabaseExpert.Banner> list(DatabaseExpert databaseExpert) throws DatabaseException {
                return databaseExpert.getMyServiceBannerList();
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
            BannerImageProtocol.Parameter imageReqParam(String str) {
                return new BannerImageProtocol.Parameter(BannerImageProtocol.Parameter.Type.MY_SERVICE, str);
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
            DatabaseExpert.BannerSettings setting(DatabaseExpert databaseExpert) throws DatabaseException {
                return databaseExpert.getNoticeBannerSettings();
            }

            @Override // com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.Type
            List<DatabaseExpert.Banner> list(DatabaseExpert databaseExpert) throws DatabaseException {
                return databaseExpert.getNoticeBannerList();
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
            BannerImageProtocol.Parameter imageReqParam(String str) {
                return new BannerImageProtocol.Parameter(BannerImageProtocol.Parameter.Type.NOTIFICATION, str);
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

        BannerImageProtocol.Parameter imageReqParam(String str) {
            return null;
        }

        List<DatabaseExpert.Banner> list(DatabaseExpert databaseExpert) throws DatabaseException {
            return null;
        }

        DatabaseExpert.BannerPos pos() {
            return null;
        }

        DatabaseExpert.BannerSettings setting(DatabaseExpert databaseExpert) throws DatabaseException {
            return null;
        }
    }

    OrderBannerWorker(ModelContext modelContext, Type type, Listener listener) {
        this._context = modelContext;
        this._listener = listener;
        this._type = type;
        this._db = modelContext.getOpenedDatabaseExpert();
    }

    protected OrderBannerWorker() {
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x019c  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 425
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.OrderBannerWorker.run():void");
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

    private List<BannerInfo> makeBannerInfoList(List<DatabaseExpert.Banner> list) {
        ArrayList arrayList = new ArrayList();
        for (DatabaseExpert.Banner banner : list) {
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

    private Map<String, String[]> getNeedsImgBanners(List<BannerFileProtocol.Result.Banner> list, List<DatabaseExpert.Banner> list2) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        for (DatabaseExpert.Banner banner : list2) {
            map2.put(banner.id, banner);
        }
        for (BannerFileProtocol.Result.Banner banner2 : list) {
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
