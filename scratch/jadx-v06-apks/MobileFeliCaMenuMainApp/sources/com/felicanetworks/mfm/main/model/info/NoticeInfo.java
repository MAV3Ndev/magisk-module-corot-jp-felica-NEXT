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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class NoticeInfo {
    private static final String ACCESS_STATUS_NOT_ACCESS = "0";
    private static final String EMPTY_STRING = "";
    private static final String GROWTHPUSH_OBJECT_KEY_NOTIFICATION_ID = "notificationId";
    private static final String INTENT_EXTRAS_KEY_GROWTHPUSH_OBJECT = "growthpush";
    private static final String INTENT_EXTRAS_KEY_ICON_FILE = "icon";
    private static final String INTENT_EXTRAS_KEY_IMAGE_FILE = "img";
    private static final String INTENT_EXTRAS_KEY_IMAGE_PATH = "ipath";
    private static final String INTENT_EXTRAS_KEY_LINKAGE_SCHEME = "ls";
    private static final String INTENT_EXTRAS_KEY_MESSAGE_OBJECT = "message";
    private static final String INTENT_EXTRAS_KEY_NCOND_OBJECT = "ncond";
    private static final String INTENT_EXTRAS_KEY_REGISTERED_SERVICE_LIST = "1";
    private static final String INTENT_EXTRAS_KEY_UNREGISTERED_SERVICE_LIST = "0";
    private static final String MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL = "btn";
    private static final String MESSAGE_OBJECT_KEY_EXPIRATION_DATE = "ed";
    private static final String MESSAGE_OBJECT_KEY_HEADER = "hd";
    private static final String MESSAGE_OBJECT_KEY_PATTERN_ID = "pid";
    private static final String MESSAGE_OBJECT_KEY_SEND_DATE = "sd";
    private static final String MESSAGE_OBJECT_KEY_TEXT = "txt";
    private static final String MESSAGE_OBJECT_KEY_TYPE = "tp";
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
    private String _patternId;
    private String _scheme;
    private String _sendDate;
    private String _status;
    private String _title;

    public interface OrderImgListener {
        void onComplete(Bitmap bitmap);
    }

    private static class BitmapCache {
        private Bitmap icon;
        private Bitmap image;

        private BitmapCache() {
            this.icon = null;
            this.image = null;
        }

        public void setIcon(Bitmap bitmap) {
            this.icon = bitmap;
        }

        public void setImage(Bitmap bitmap) {
            this.image = bitmap;
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

        public Ncond(int i, ArrayList<String> arrayList) {
            this.serviceId = new ArrayList<>();
            this.key = i;
            this.serviceId = arrayList;
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

    public NoticeInfo(NoticeData noticeData, NetworkExpert networkExpert) {
        this._noticeId = noticeData.noticeId;
        this._patternId = noticeData.patternId;
        this._messegeType = noticeData.messageType;
        this._sendDate = noticeData.sendDate;
        this._expirationDate = noticeData.expirationDate;
        this._title = noticeData.title;
        this._message = noticeData.message;
        this._iconUrl = noticeData.iconUrl;
        this._iconName = noticeData.iconName;
        this._imageName = noticeData.imageName;
        this._scheme = noticeData.scheme;
        this._status = noticeData.status;
        this._buttonLable = noticeData.buttonLable;
        this._detailAccess = noticeData.detailAccess;
        this._networkExpert = networkExpert;
    }

    public NoticeInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, NetworkExpert networkExpert) {
        this._noticeId = str;
        this._patternId = str2;
        this._messegeType = str3;
        this._sendDate = str4;
        this._expirationDate = str5;
        this._title = str6;
        this._message = str7;
        this._iconUrl = str8;
        this._iconName = str9;
        this._imageName = str10;
        this._scheme = str11;
        this._status = str12;
        this._buttonLable = str13;
        this._detailAccess = str14;
        this._networkExpert = networkExpert;
    }

    public NoticeInfo(Context context, Intent intent) {
        if (parse(intent)) {
            this._networkExpert = new NetworkExpert(new ModelContext(context));
            return;
        }
        this._noticeId = null;
        this._patternId = null;
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
        return "NoticeInfo{, _networkExpert=" + this._networkExpert + ", _noticeId='" + this._noticeId + "', _patternId='" + this._patternId + "', _messegeType='" + this._messegeType + "', _sendDate='" + this._sendDate + "', _expirationDate='" + this._expirationDate + "', _title='" + this._title + "', _message='" + this._message + "', _iconUrl='" + this._iconUrl + "', _iconImgName='" + this._iconName + "', _imageName='" + this._imageName + "', _scheme='" + this._scheme + "', _status='" + this._status + "', _buttonLable='" + this._buttonLable + "', _detailAccess='" + this._detailAccess + "'}";
    }

    public boolean valid() {
        return (this._noticeId == null || this._messegeType == null || this._sendDate == null || this._expirationDate == null || this._title == null || this._message == null || this._scheme == null) ? false : true;
    }

    public String getId() {
        return this._noticeId;
    }

    public String getPatternId() {
        return this._patternId;
    }

    public boolean isRead() {
        return "1".equals(this._status);
    }

    public void setReadStatus(NoticeFunc noticeFunc) {
        if ("0".equals(this._status)) {
            AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_OPEN_MESSAGE, this);
            noticeFunc.setReadStatus(this._noticeId);
        }
    }

    public void setDetailAccessStatus(NoticeFunc noticeFunc) {
        if ("0".equals(this._detailAccess)) {
            noticeFunc.setDetailAccess(this._noticeId);
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

    public boolean orderIconImg(final Context context, final OrderImgListener orderImgListener) {
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
                orderImgListener.onComplete(image);
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

    public boolean orderMsgImg(final Context context, final OrderImgListener orderImgListener) {
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
                orderImgListener.onComplete(bmpCage.getImage());
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getImage(Context context, String str, String str2) {
        Bitmap bitmapDecodeByteArray;
        try {
            String str3 = str + str2;
            if (str3.startsWith(RESOURCE_DRAWABLE) && !"".equals(str2)) {
                Resources resources = context.getApplicationContext().getResources();
                bitmapDecodeByteArray = BitmapFactory.decodeResource(resources, resources.getIdentifier(str2, RESOURCE_DRAWABLE, context.getPackageName()));
            } else if (str3.startsWith(RESOURCE_MIPMAP) && !"".equals(str2)) {
                Resources resources2 = context.getResources();
                bitmapDecodeByteArray = BitmapFactory.decodeResource(resources2, resources2.getIdentifier(str2, RESOURCE_MIPMAP, context.getPackageName()));
            } else {
                if (!str3.startsWith(PRIFIX_HTTP)) {
                    return null;
                }
                ImageProtocol imageProtocol = this._networkExpert.getImageProtocol();
                ImageProtocol.Result result = imageProtocol.parse(this._networkExpert.connect(imageProtocol.create(new ImageProtocol.Parameter(str3))));
                bitmapDecodeByteArray = BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
            }
            return bitmapDecodeByteArray;
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
        if (!this._scheme.startsWith("market:")) {
            return intent;
        }
        intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
        return intent;
    }

    public String getBtnMsg() {
        return this._buttonLable;
    }

    private boolean parse(Intent intent) {
        String str = INTENT_EXTRAS_KEY_LINKAGE_SCHEME;
        String str2 = INTENT_EXTRAS_KEY_IMAGE_FILE;
        try {
            StringBuilder sb = new StringBuilder();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Iterator<String> it = extras.keySet().iterator();
                while (it.hasNext()) {
                    sb.append(intent.getExtras().get(it.next()));
                    str2 = str2;
                    str = str;
                }
            }
            String str3 = str;
            String str4 = str2;
            if (4096 < sb.toString().getBytes(StandardCharsets.UTF_8).length) {
                return false;
            }
            JSONObject jSONObject = intent.getExtras().containsKey(INTENT_EXTRAS_KEY_GROWTHPUSH_OBJECT) ? new JSONObject(intent.getExtras().getString(INTENT_EXTRAS_KEY_GROWTHPUSH_OBJECT)) : null;
            if (jSONObject != null) {
                this._noticeId = !jSONObject.isNull(GROWTHPUSH_OBJECT_KEY_NOTIFICATION_ID) ? jSONObject.getString(GROWTHPUSH_OBJECT_KEY_NOTIFICATION_ID) : null;
            }
            JSONObject jSONObject2 = intent.getExtras().containsKey(INTENT_EXTRAS_KEY_MESSAGE_OBJECT) ? new JSONObject(intent.getExtras().getString(INTENT_EXTRAS_KEY_MESSAGE_OBJECT)) : null;
            if (jSONObject2 != null) {
                this._title = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_HEADER) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_HEADER) : null;
                this._message = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_TEXT) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_TEXT) : null;
                this._patternId = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_PATTERN_ID) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_PATTERN_ID) : null;
                String string = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_TYPE) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_TYPE) : null;
                this._messegeType = string;
                if (string != null) {
                    DataCheckerUtil.checkByteLength(string, 1, true);
                    DataCheckerUtil.checkFixValue(this._messegeType, new String[]{"0", "1"});
                }
                String string2 = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_SEND_DATE) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_SEND_DATE) : null;
                this._sendDate = string2;
                if (string2 != null) {
                    DataCheckerUtil.checkByteLength(string2, 12, true);
                    DataCheckerUtil.checkDecNumberFormat(this._sendDate);
                }
                String string3 = !jSONObject2.isNull(MESSAGE_OBJECT_KEY_EXPIRATION_DATE) ? jSONObject2.getString(MESSAGE_OBJECT_KEY_EXPIRATION_DATE) : null;
                this._expirationDate = string3;
                if (string3 != null) {
                    DataCheckerUtil.checkByteLength(string3, 12, true);
                    DataCheckerUtil.checkDecNumberFormat(this._expirationDate);
                }
                this._buttonLable = jSONObject2.isNull(MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL) ? null : jSONObject2.getString(MESSAGE_OBJECT_KEY_DETAIL_BUTTON_LABEL);
            }
            if (intent.getExtras().containsKey(INTENT_EXTRAS_KEY_IMAGE_PATH)) {
                this._iconUrl = intent.getExtras().getString(INTENT_EXTRAS_KEY_IMAGE_PATH);
            }
            if (intent.getExtras().containsKey(INTENT_EXTRAS_KEY_ICON_FILE)) {
                this._iconName = intent.getExtras().getString(INTENT_EXTRAS_KEY_ICON_FILE);
            }
            if (intent.getExtras().containsKey(str4)) {
                this._imageName = intent.getExtras().getString(str4);
            }
            if (intent.getExtras().containsKey(str3)) {
                this._scheme = intent.getExtras().getString(str3);
            }
            if (intent.getExtras().containsKey(INTENT_EXTRAS_KEY_NCOND_OBJECT)) {
                if (intent.getExtras().getString(INTENT_EXTRAS_KEY_NCOND_OBJECT).isEmpty()) {
                    throw new Exception("Format Error");
                }
                JSONArray jSONArray = new JSONArray(intent.getExtras().getString(INTENT_EXTRAS_KEY_NCOND_OBJECT));
                this._ncond = new ArrayList<>();
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    if (jSONArray.getJSONObject(i).has("1") || jSONArray.getJSONObject(i).has("0")) {
                        String next = jSONArray.getJSONObject(i).keys().next();
                        JSONArray jSONArray2 = jSONArray.getJSONObject(i).getJSONArray(next);
                        int length2 = jSONArray2.length();
                        if (length2 == 0) {
                            throw new Exception("Format Error");
                        }
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < length2; i2++) {
                            DataCheckerUtil.checkByteLength(jSONArray2.getString(i2), 8, true);
                            arrayList.add(jSONArray2.getString(i2));
                        }
                        this._ncond.add(new Ncond(Integer.parseInt(next), arrayList));
                    }
                }
                if (this._ncond.size() == 0) {
                    throw new Exception("Format Error");
                }
            }
            this._status = "0";
            this._detailAccess = "0";
            return valid();
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    private Date convStr2Date(String str) {
        DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo");
        if (str == null) {
            return null;
        }
        try {
            return dateFormatter.parse(str);
        } catch (ParseException e) {
            LogUtil.warning(e);
            return null;
        }
    }
}
