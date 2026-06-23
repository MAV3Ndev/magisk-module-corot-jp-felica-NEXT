package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class FpServiceInfo {
    private static Map<String, BitmapCache> cache = new HashMap();
    private String _colorCode;
    private String _iconUrl;
    private Intent _linkIntent;
    private NetworkExpert _networkExpert;
    private long _point;
    private String _pointUnit;
    private String _serviceName;
    private int _serviceNumber;
    private String _serviceProvider;
    private Type _type;

    public interface OrderImgListener {
        void onComplete(Bitmap data);
    }

    public enum Type {
        POINT,
        OTHER,
        UNSUPPORTED,
        UNKNOWN
    }

    public FpServiceInfo(int _serviceNumber, String _serviceName, long _point, String _pointUnit, String _iconUrl, String _serviceProvider, String _linkIntent, NetworkExpert _networkExpert, Type _type) {
        this._serviceNumber = _serviceNumber;
        this._serviceName = _serviceName;
        this._point = _point;
        this._pointUnit = _pointUnit;
        this._iconUrl = _iconUrl;
        this._serviceProvider = _serviceProvider;
        this._linkIntent = new Intent("android.intent.action.VIEW", Uri.parse(_linkIntent));
        this._networkExpert = _networkExpert;
        this._type = _type;
    }

    public String toString() {
        return "FpServiceInfo{_serviceNumber=" + this._serviceNumber + ", _serviceName='" + this._serviceName + "', _point=" + this._point + ", _pointUnit='" + this._pointUnit + "', _iconUrl='" + this._iconUrl + "', _serviceProvider='" + this._serviceProvider + "', _linkIntent=" + this._linkIntent + ", _networkExpert=" + this._networkExpert + ", _type=" + this._type + ", _colorCode='" + this._colorCode + "'}";
    }

    public int getServiceNumber() {
        return this._serviceNumber;
    }

    public String getServiceName() {
        return this._serviceName;
    }

    public long getPoint() {
        return this._point;
    }

    public String getPointUnit() {
        return this._pointUnit;
    }

    public String getServiceProvider() {
        return this._serviceProvider;
    }

    public Type getType() {
        return this._type;
    }

    public Intent getLinkIntent() {
        return this._linkIntent;
    }

    public int getColorCode() {
        if (this._colorCode == null) {
            if (Type.POINT == this._type || Type.OTHER == this._type) {
                this._colorCode = (String) Sg.getValue(Sg.Key.SETTING_FP_SERVICE_COLOR_BLUE);
            } else {
                this._colorCode = (String) Sg.getValue(Sg.Key.SETTING_FP_SERVICE_COLOR_GRAY);
            }
        }
        return Color.rgb(Integer.parseInt(this._colorCode.substring(0, 2), 16), Integer.parseInt(this._colorCode.substring(2, 4), 16), Integer.parseInt(this._colorCode.substring(4, 6), 16));
    }

    private static class BitmapCache {
        private Bitmap icon;

        private BitmapCache() {
            this.icon = null;
        }

        public void setIcon(Bitmap bmp) {
            this.icon = bmp;
        }

        public Bitmap getIcon() {
            return this.icon;
        }

        public boolean hasIcon() {
            return this.icon != null;
        }
    }

    public static void clearCache() {
        cache = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapCache getBmpCage() {
        if (cache.containsKey(String.valueOf(this._serviceNumber))) {
            return cache.get(String.valueOf(this._serviceNumber));
        }
        BitmapCache bitmapCache = new BitmapCache();
        cache.put(String.valueOf(this._serviceNumber), bitmapCache);
        return bitmapCache;
    }

    public boolean orderIconImg(final Context context, final OrderImgListener listener) {
        if (this._iconUrl == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.info.FpServiceInfo.1
            @Override // java.lang.Runnable
            public void run() {
                Bitmap image;
                BitmapCache bmpCage = FpServiceInfo.this.getBmpCage();
                if (bmpCage.hasIcon()) {
                    image = bmpCage.getIcon();
                } else {
                    FpServiceInfo fpServiceInfo = FpServiceInfo.this;
                    image = fpServiceInfo.getImage(fpServiceInfo._iconUrl);
                }
                if (image == null) {
                    Resources resources = context.getResources();
                    image = BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_DEFAULT_SERVICE_ICON), "drawable", context.getPackageName()));
                } else {
                    bmpCage.setIcon(image);
                }
                listener.onComplete(image);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getImage(String url) {
        try {
            if (!url.startsWith("http")) {
                return null;
            }
            ImageProtocol imageProtocol = this._networkExpert.getImageProtocol();
            ImageProtocol.Result result = imageProtocol.parse(this._networkExpert.connect(imageProtocol.create(new ImageProtocol.Parameter(url))));
            return BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
        } catch (Throwable th) {
            LogUtil.warning(th);
            return null;
        }
    }
}
