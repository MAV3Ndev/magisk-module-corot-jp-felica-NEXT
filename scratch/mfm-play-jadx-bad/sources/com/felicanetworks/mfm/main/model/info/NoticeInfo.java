package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeInfo {
    private static final String ACCESS_STATUS_NOT_ACCESS = "0";
    private static final String EMPTY_STRING = "";
    public static final String INTENT_EXTRAS_KEY_IMAGE_PATH = "ipath";
    public static final String INTENT_EXTRAS_KEY_LINKAGE_SCHEME = "ls";
    public static final String INTENT_EXTRAS_KEY_MESSAGE_BODY = "messageBody";
    public static final String INTENT_EXTRAS_KEY_MESSAGE_EXTRA = "messageExtra";
    private static final int INTENT_EXTRAS_VALUE_MAX_LENGTH = 200;
    private static final String MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL = "btn";
    private static final String MESSAGE_OBJECT_KEY_EXPIRATION_DATE = "ed";
    private static final String MESSAGE_OBJECT_KEY_HEADER = "hd";
    private static final String MESSAGE_OBJECT_KEY_ICON_FILE = "icon";
    private static final String MESSAGE_OBJECT_KEY_IMAGE_FILE = "img";
    private static final String MESSAGE_OBJECT_KEY_NOTIFICATION_ID = "notificationId";
    private static final String MESSAGE_OBJECT_KEY_SEND_DATE = "sd";
    private static final String PRIFIX_HTTP = "http";
    private static final String READ_STATUS_READ = "1";
    private static final String READ_STATUS_UNREAD = "0";
    private static final String RESOURCE_DRAWABLE = "drawable";
    private static final String RESOURCE_MIPMAP = "mipmap";
    private static Map<String, BitmapCache> cache = new HashMap();
    private String _buttonLable;
    private String _detailAccess;
    private String _expirationDate;
    private String _iconName;
    private String _iconUrl;
    private String _imageName;
    private String _message;
    private String _messegeType;
    private ArrayList<Ncond> _ncond;
    private NetworkExpert _networkExpert;
    private String _noticeId;
    private String _scheme;
    private String _sendDate;
    private String _status;
    private String _title;

    public interface OrderImgListener {
        void onComplete(Bitmap data);
    }

    private static class BitmapCache {
        private Bitmap icon;
        private Bitmap image;

        private BitmapCache() {
            this.icon = null;
            this.image = null;
        }

        public void setIcon(Bitmap bmp) {
            this.icon = bmp;
        }

        public void setImage(Bitmap bmp) {
            this.image = bmp;
        }

        public Bitmap getIcon() {
            return this.icon;
        }

        public Bitmap getImage() {
            return this.image;
        }

        public boolean hasIcon() {
            return this.icon != null;
        }

        public boolean hasImage() {
            return this.image != null;
        }
    }

    public static class Ncond {
        private int key;
        private ArrayList<String> serviceId;

        public Ncond(int sKey, ArrayList<String> sid) {
            new ArrayList();
            this.key = sKey;
            this.serviceId = sid;
        }

        public int getKey() {
            return this.key;
        }

        public ArrayList<String> getServiceId() {
            return this.serviceId;
        }
    }

    public static void clearCache() {
        cache = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapCache getBmpCage() {
        if (cache.containsKey(this._noticeId)) {
            return cache.get(this._noticeId);
        }
        BitmapCache bitmapCache = new BitmapCache();
        cache.put(this._noticeId, bitmapCache);
        return bitmapCache;
    }

    public NoticeInfo(NoticeData data, NetworkExpert networkExpert) {
        this._noticeId = data.noticeId;
        this._messegeType = data.messageType;
        this._sendDate = data.sendDate;
        this._expirationDate = data.expirationDate;
        this._title = data.title;
        this._message = data.message;
        this._iconUrl = data.iconUrl;
        this._iconName = data.iconName;
        this._imageName = data.imageName;
        this._scheme = data.scheme;
        this._status = data.status;
        this._buttonLable = data.buttonLable;
        this._detailAccess = data.detailAccess;
        this._networkExpert = networkExpert;
    }

    public NoticeInfo(String noticeId, String messageType, String sendDate, String expirationDate, String title, String message, String iconUrl, String iconName, String imageName, String scheme, String status, String buttonLable, String detailAccess, NetworkExpert networkExpert) {
        this._noticeId = noticeId;
        this._messegeType = messageType;
        this._sendDate = sendDate;
        this._expirationDate = expirationDate;
        this._title = title;
        this._message = message;
        this._iconUrl = iconUrl;
        this._iconName = iconName;
        this._imageName = imageName;
        this._scheme = scheme;
        this._status = status;
        this._buttonLable = buttonLable;
        this._detailAccess = detailAccess;
        this._networkExpert = networkExpert;
    }

    public NoticeInfo(Context context, Intent i) {
        if (parse(i)) {
            this._networkExpert = new NetworkExpert(new ModelContext(context));
            return;
        }
        this._noticeId = null;
        this._messegeType = null;
        this._sendDate = null;
        this._expirationDate = null;
        this._title = null;
        this._message = null;
        this._iconUrl = null;
        this._iconName = null;
        this._imageName = null;
        this._scheme = null;
        this._status = null;
        this._buttonLable = null;
        this._detailAccess = null;
        this._networkExpert = null;
        this._ncond = null;
    }

    public String toString() {
        return "NoticeInfo{, _networkExpert=" + this._networkExpert + ", _noticeId='" + this._noticeId + "', _messegeType='" + this._messegeType + "', _sendDate='" + this._sendDate + "', _expirationDate='" + this._expirationDate + "', _title='" + this._title + "', _message='" + this._message + "', _iconUrl='" + this._iconUrl + "', _iconImgName='" + this._iconName + "', _imageName='" + this._imageName + "', _scheme='" + this._scheme + "', _status='" + this._status + "', _buttonLable='" + this._buttonLable + "', _detailAccess='" + this._detailAccess + "'}";
    }

    public boolean valid() {
        return (this._noticeId == null || this._sendDate == null || this._expirationDate == null || this._title == null || this._message == null || this._scheme == null) ? false : true;
    }

    public String getId() {
        return this._noticeId;
    }

    public boolean isRead() {
        return "1".equals(this._status);
    }

    public void setReadStatus(NoticeFunc func) {
        if ("0".equals(this._status)) {
            AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_OPEN_MESSAGE, this);
            func.setReadStatus(this._noticeId);
        }
    }

    public void setDetailAccessStatus(NoticeFunc func) {
        if ("0".equals(this._detailAccess)) {
            func.setDetailAccess(this._noticeId);
        }
    }

    public String getTitle() {
        return this._title;
    }

    public String getMessage() {
        return this._message;
    }

    public String getPushType() {
        return this._messegeType;
    }

    public boolean isForcePushType() {
        return "1".equals(getPushType());
    }

    public Date getSendDate() {
        return convStr2Date(this._sendDate);
    }

    public Date getExpirationDate() {
        return convStr2Date(this._expirationDate);
    }

    public String getIconUrl() {
        return this._iconUrl;
    }

    public String getIconName() {
        return this._iconName;
    }

    public String getImageName() {
        return this._imageName;
    }

    public String getScheme() {
        return this._scheme;
    }

    public String getDetailAccess() {
        return this._detailAccess;
    }

    public String getStatus() {
        return this._status;
    }

    public ArrayList<Ncond> getNcond() {
        return this._ncond;
    }

    public boolean isExpiration() {
        Date date;
        DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo");
        String str = dateFormatter.format(new Date());
        Date expirationDate = getExpirationDate();
        try {
            date = dateFormatter.parse(str);
        } catch (ParseException e) {
            LogUtil.warning(e);
            date = null;
        }
        return date == null || expirationDate == null || expirationDate.compareTo(date) < 0;
    }

    public String getIconImgUrl() {
        if (this._iconUrl == null || this._iconName == null) {
            return null;
        }
        return this._iconUrl + this._iconName;
    }

    public boolean orderIconImg(final Context context, final OrderImgListener listener) {
        if (getIconImgUrl() == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.info.NoticeInfo.1
            @Override // java.lang.Runnable
            public void run() {
                Bitmap image;
                BitmapCache bmpCage = NoticeInfo.this.getBmpCage();
                if (bmpCage.hasIcon()) {
                    image = bmpCage.getIcon();
                } else {
                    NoticeInfo noticeInfo = NoticeInfo.this;
                    image = noticeInfo.getImage(context, noticeInfo._iconUrl, NoticeInfo.this._iconName);
                }
                if (image == null) {
                    Resources resources = context.getResources();
                    image = BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_DEFAULT_NOTICE_ICON), NoticeInfo.RESOURCE_DRAWABLE, context.getPackageName()));
                } else {
                    bmpCage.setIcon(image);
                }
                listener.onComplete(image);
            }
        }).start();
        return true;
    }

    public String getMsgImgUrl() {
        if (this._iconUrl == null || this._imageName == null) {
            return null;
        }
        return this._iconUrl + this._imageName;
    }

    public boolean orderMsgImg(final Context context, final OrderImgListener listener) {
        if (getMsgImgUrl() == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.info.NoticeInfo.2
            @Override // java.lang.Runnable
            public void run() {
                BitmapCache bmpCage = NoticeInfo.this.getBmpCage();
                if (!bmpCage.hasImage()) {
                    NoticeInfo noticeInfo = NoticeInfo.this;
                    bmpCage.setImage(noticeInfo.getImage(context, noticeInfo._iconUrl, NoticeInfo.this._imageName));
                }
                listener.onComplete(bmpCage.getImage());
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getImage(Context context, String path, String name) {
        try {
            String str = path + name;
            if (str.startsWith(RESOURCE_DRAWABLE) && !"".equals(name)) {
                Resources resources = context.getApplicationContext().getResources();
                return BitmapFactory.decodeResource(resources, resources.getIdentifier(name, RESOURCE_DRAWABLE, context.getPackageName()));
            }
            if (str.startsWith(RESOURCE_MIPMAP) && !"".equals(name)) {
                Resources resources2 = context.getResources();
                return BitmapFactory.decodeResource(resources2, resources2.getIdentifier(name, RESOURCE_MIPMAP, context.getPackageName()));
            }
            if (!str.startsWith(PRIFIX_HTTP)) {
                return null;
            }
            ImageProtocol imageProtocol = this._networkExpert.getImageProtocol();
            ImageProtocol.Result result = imageProtocol.parse(this._networkExpert.connect(imageProtocol.create(new ImageProtocol.Parameter(str))));
            return BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
        } catch (Throwable th) {
            LogUtil.warning(th);
            return null;
        }
    }

    public Intent getLinkIntent() {
        String str = this._scheme;
        if (str == null) {
            return null;
        }
        if (str.startsWith("http:") && "0".equals(this._noticeId)) {
            this._scheme = this._scheme.replace("http:", "https:");
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this._scheme));
        if (this._scheme.startsWith("market:")) {
            intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
        }
        return intent;
    }

    public String getBtnMsg() {
        return this._buttonLable;
    }

    private boolean parse(Intent intent) {
        boolean z;
        try {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return false;
            }
            z = false;
            if (extras.containsKey(INTENT_EXTRAS_KEY_MESSAGE_BODY)) {
                try {
                    String string = extras.getString(INTENT_EXTRAS_KEY_MESSAGE_BODY);
                    this._message = string;
                    if (string != null && string.length() > 200) {
                        return false;
                    }
                } catch (Exception e) {
                    e = e;
                }
            }
            if (extras.containsKey(INTENT_EXTRAS_KEY_MESSAGE_EXTRA)) {
                String string2 = extras.getString(INTENT_EXTRAS_KEY_MESSAGE_EXTRA);
                if (string2 != null && string2.length() > 200) {
                    return false;
                }
                if (string2 != null) {
                    JSONObject jSONObject = new JSONObject(string2);
                    this._title = !jSONObject.isNull(MESSAGE_OBJECT_KEY_HEADER) ? jSONObject.getString(MESSAGE_OBJECT_KEY_HEADER) : null;
                    String string3 = !jSONObject.isNull(MESSAGE_OBJECT_KEY_SEND_DATE) ? jSONObject.getString(MESSAGE_OBJECT_KEY_SEND_DATE) : null;
                    this._sendDate = string3;
                    if (string3 != null) {
                        DataCheckerUtil.checkByteLength(string3, 12, true);
                        DataCheckerUtil.checkDecNumberFormat(this._sendDate);
                    }
                    String string4 = !jSONObject.isNull(MESSAGE_OBJECT_KEY_EXPIRATION_DATE) ? jSONObject.getString(MESSAGE_OBJECT_KEY_EXPIRATION_DATE) : null;
                    this._expirationDate = string4;
                    if (string4 != null) {
                        DataCheckerUtil.checkByteLength(string4, 12, true);
                        DataCheckerUtil.checkDecNumberFormat(this._expirationDate);
                    }
                    this._buttonLable = !jSONObject.isNull(MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL) ? jSONObject.getString(MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL) : null;
                    this._noticeId = !jSONObject.isNull(MESSAGE_OBJECT_KEY_NOTIFICATION_ID) ? jSONObject.getString(MESSAGE_OBJECT_KEY_NOTIFICATION_ID) : null;
                    this._iconName = !jSONObject.isNull(MESSAGE_OBJECT_KEY_ICON_FILE) ? jSONObject.getString(MESSAGE_OBJECT_KEY_ICON_FILE) : null;
                    this._imageName = jSONObject.isNull(MESSAGE_OBJECT_KEY_IMAGE_FILE) ? null : jSONObject.getString(MESSAGE_OBJECT_KEY_IMAGE_FILE);
                }
            }
            if (extras.containsKey(INTENT_EXTRAS_KEY_IMAGE_PATH)) {
                String string5 = extras.getString(INTENT_EXTRAS_KEY_IMAGE_PATH);
                this._iconUrl = string5;
                if (string5 != null && string5.length() > 200) {
                    return false;
                }
            }
            if (extras.containsKey(INTENT_EXTRAS_KEY_LINKAGE_SCHEME)) {
                String string6 = extras.getString(INTENT_EXTRAS_KEY_LINKAGE_SCHEME);
                this._scheme = string6;
                if (string6 != null && string6.length() > 200) {
                    return false;
                }
            }
            this._status = "0";
            this._detailAccess = "0";
            return valid();
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
        LogUtil.warning(e);
        return z;
    }

    private Date convStr2Date(String dateString) {
        DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo");
        if (dateString != null) {
            try {
                return dateFormatter.parse(dateString);
            } catch (ParseException e) {
                LogUtil.warning(e);
            }
        }
        return null;
    }
}
