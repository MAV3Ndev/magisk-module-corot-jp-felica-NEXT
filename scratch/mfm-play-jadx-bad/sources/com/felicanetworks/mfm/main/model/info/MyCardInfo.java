package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyNanacoInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyWaonInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.CardIdPolicy;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MyCardInfo implements ServiceIdPolicy.ServiceIdHolder, CardIdPolicy.CardIdHolder {
    private static final String EMPTY_STRING = "";
    private static final String PRIFIX_HTTP = "http";
    private static final String RESOURCE_DRAWABLE = "drawable";
    private static final String RESOURCE_MIPMAP = "mipmap";
    private static Map<String, BitmapCache> cache = new HashMap();
    private ServiceInfo.AssetType _assetType;
    private NetworkExpert _networkExpert;
    private List<ServiceInfo.Point.PointData> _points;
    private MyCardAdditionalInfo cardAdditionalInfo;
    private String cardCategory;
    private String cardId;
    private CardPosition cardPosition;
    private CardSpStatus cardSpStatus;
    private CardStatus cardStatus;
    private boolean isCached;
    String mfiAdditionalInfoHashValue;
    private String serviceId;
    private List<ServiceInfo.History.HistoryData> _history = new ArrayList();
    private ServiceInfo.PrepaidEmoney prepaidEmoney = new ServiceInfo.PrepaidEmoney() { // from class: com.felicanetworks.mfm.main.model.info.MyCardInfo.1
        private int _balance = -1;

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
        public int getBalance() {
            return this._balance;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PrepaidEmoney
        public void setBalance(int balance) {
            this._balance = balance;
        }
    };
    private ServiceInfo.PostpayEmoney postpayEmoney = new ServiceInfo.PostpayEmoney() { // from class: com.felicanetworks.mfm.main.model.info.MyCardInfo.2
        private int _creditLimit = -1;
        private int _availableCredit = -1;

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public int getCreditLimit() {
            return this._creditLimit;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public void setCreditLimit(int creditLimit) {
            this._creditLimit = creditLimit;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public int getAvailableCredit() {
            return this._availableCredit;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public void setAvailableCredit(int availableCredit) {
            this._availableCredit = availableCredit;
        }
    };

    public enum CardPosition {
        POSITION_FOREGROUND,
        POSITION_BACKGROUND,
        POSITION_PENDING,
        POSITION_NOT_APPLICABLE,
        POSITION_NOT_EXIST
    }

    public enum CardStatus {
        STATUS_IN_PROCESS,
        STATUS_ACTIVE,
        STATUS_LOST,
        STATUS_DELETED
    }

    public interface OrderImgListener {
        void onComplete(Bitmap data);
    }

    public enum SupportMfiServiceType {
        UNSUPPORTED_MFI_SERVICE_1,
        SUPPORTED_MFI_SERVICE_1,
        SUPPORTED_MFI_OTHER_SERVICE
    }

    public MyCardInfo(String serviceId, String cardId, String cardCategory, CardStatus status, CardPosition position, String mfiAdditionalInfoHashValue, MyCardAdditionalInfo cardAdditionalInfo, NetworkExpert networkExpert, boolean cache2) {
        this.serviceId = serviceId;
        this.cardId = cardId;
        this.cardCategory = cardCategory;
        this.cardStatus = status;
        this.cardPosition = position;
        this.mfiAdditionalInfoHashValue = mfiAdditionalInfoHashValue;
        this.cardAdditionalInfo = cardAdditionalInfo;
        this._networkExpert = networkExpert;
        this.isCached = cache2;
    }

    @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
    public String sid() {
        return this.serviceId;
    }

    @Override // com.felicanetworks.mfm.main.policy.service.CardIdPolicy.CardIdHolder
    public String cid() {
        return this.cardId;
    }

    public String toString() {
        return "MyCardInfo{serviceId='" + this.serviceId + "', cardId='" + this.cardId + "', cardStatus=" + this.cardStatus + ", cardPosition=" + this.cardPosition + ", cardSpStatus=" + this.cardSpStatus + ", mfiAdditionalInfoHashValue=" + this.mfiAdditionalInfoHashValue + ", cardAdditionalInfo=" + this.cardAdditionalInfo + ", cardCategory='" + this.cardCategory + "', _networkExpert=" + this._networkExpert + ", _assetType=" + this._assetType + ", _points=" + this._points + ", _history=" + this._history + ", _balance=" + this.prepaidEmoney.getBalance() + ", _creditLimit=" + this.postpayEmoney.getCreditLimit() + ", _availableCredit=" + this.postpayEmoney.getAvailableCredit() + ", isCached=" + this.isCached + '}';
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getCardId() {
        return this.cardId;
    }

    public static String getServiceIdFromCid(String cid) {
        try {
            return cid.substring(7, 15);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getSpIfFromServiceType(String serviceType) {
        try {
            return serviceType.substring(1, 2);
        } catch (Exception unused) {
            return "";
        }
    }

    public boolean isSuicaCardId() {
        return TextUtils.equals("D00000000000000000000000000000000000000000000000000000000000001", this.cardId);
    }

    public String getCardName() {
        MyCardAdditionalInfo myCardAdditionalInfo = this.cardAdditionalInfo;
        if (myCardAdditionalInfo == null || myCardAdditionalInfo.getAdditionalInfo() == null) {
            return null;
        }
        return this.cardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.cardTypeName;
    }

    public String getContactName() {
        MyCardAdditionalInfo myCardAdditionalInfo = this.cardAdditionalInfo;
        if (myCardAdditionalInfo == null || myCardAdditionalInfo.getAdditionalInfo() == null) {
            return null;
        }
        return this.cardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.contactName;
    }

    public String getCardCategory() {
        return this.cardCategory;
    }

    public String getMfiAdditionalInfoHashValue() {
        return this.mfiAdditionalInfoHashValue;
    }

    public MyCardAdditionalInfo getCardAdditionalInfo() {
        return this.cardAdditionalInfo;
    }

    public void setCardAdditionalInfo(MyCardAdditionalInfo additionalInfo) {
        this.cardAdditionalInfo = additionalInfo;
    }

    public enum CardSpStatus {
        SP_STATUS_REISSUABLE(1),
        SP_STATUS_UNREISSUABLE(2),
        SP_STATUS_PENDING(3),
        SP_STATUS_UNKNOWN(4),
        SP_STATUS_NOT_APPLICABLE(5);

        private final int id;

        CardSpStatus(final int id) {
            this.id = id;
        }

        public int getInt() {
            return this.id;
        }

        public static CardSpStatus getType(int id) {
            for (CardSpStatus cardSpStatus : values()) {
                if (cardSpStatus.getInt() == id) {
                    return cardSpStatus;
                }
            }
            return SP_STATUS_NOT_APPLICABLE;
        }
    }

    public CardStatus getCardStatus() {
        return this.cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public CardPosition getCardPosition() {
        return this.cardPosition;
    }

    public void setCardPosition(CardPosition cardPosition) {
        this.cardPosition = cardPosition;
    }

    public CardSpStatus getCardSpStatus() {
        return this.cardSpStatus;
    }

    public void setCardSpStatus(CardSpStatus cardSpStatus) {
        this.cardSpStatus = cardSpStatus;
    }

    public boolean isActiveForeground() {
        return CardStatus.STATUS_ACTIVE == this.cardStatus && CardPosition.POSITION_FOREGROUND == this.cardPosition;
    }

    public boolean isActiveBackground() {
        return CardStatus.STATUS_ACTIVE == this.cardStatus && CardPosition.POSITION_BACKGROUND == this.cardPosition;
    }

    private static class BitmapCache {
        private HashMap<String, Bitmap> iconMap;

        private BitmapCache() {
            this.iconMap = new HashMap<>();
        }

        public void setIcon(String key, Bitmap bmp) {
            this.iconMap.put(key, bmp);
        }

        public Bitmap getIcon(String key) {
            return this.iconMap.get(key);
        }

        public boolean hasIcon(String key) {
            return this.iconMap.containsKey(key);
        }
    }

    public String getCardFaceImgUrl() {
        try {
            return this.cardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.cardArtUrl;
        } catch (Exception unused) {
            return null;
        }
    }

    public String getSpecialCardFaceImgUrl() {
        try {
            return this.cardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.specialCardArtUrl;
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean orderIconImg(final Context context, final String url, final OrderImgListener listener) {
        if (url == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.info.MyCardInfo.3
            @Override // java.lang.Runnable
            public void run() {
                BitmapCache bmpCage = MyCardInfo.this.getBmpCage();
                Bitmap icon = bmpCage.hasIcon(url) ? bmpCage.getIcon(url) : MyCardInfo.this.getImage(context, url);
                if (icon == null) {
                    Resources resources = context.getResources();
                    icon = BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_DEFAULT_SERVICE_ICON), MyCardInfo.RESOURCE_DRAWABLE, context.getPackageName()));
                } else {
                    bmpCage.setIcon(url, icon);
                }
                listener.onComplete(icon);
            }
        }).start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapCache getBmpCage() {
        if (cache.containsKey(this.cardId)) {
            return cache.get(this.cardId);
        }
        BitmapCache bitmapCache = new BitmapCache();
        cache.put(this.cardId, bitmapCache);
        return bitmapCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getImage(Context context, String url) {
        try {
            if (url.startsWith(RESOURCE_DRAWABLE) && !"".equals(url)) {
                Resources resources = context.getApplicationContext().getResources();
                return BitmapFactory.decodeResource(resources, resources.getIdentifier(url, RESOURCE_DRAWABLE, context.getPackageName()));
            }
            if (url.startsWith(RESOURCE_MIPMAP) && !"".equals(url)) {
                Resources resources2 = context.getResources();
                return BitmapFactory.decodeResource(resources2, resources2.getIdentifier(url, RESOURCE_MIPMAP, context.getPackageName()));
            }
            if (!url.startsWith(PRIFIX_HTTP)) {
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

    private boolean isDcardMini() {
        return FeliCaParams.SERVICE_ID_DCARD.equals(getServiceId()) && this._assetType == ServiceInfo.AssetType.ONLY_POSTPAY;
    }

    public void setAssetType(ServiceInfo.AssetType type) {
        this._assetType = type;
    }

    public String getPoint(int index) {
        ServiceInfo.Point.PointData pointData;
        int point;
        if ((this._assetType == ServiceInfo.AssetType.ONLY_POINT || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && index < this._points.size() && (pointData = this._points.get(index)) != null && (point = pointData.getPoint()) >= 0) {
            return String.valueOf(point);
        }
        return "";
    }

    public String getValidPoint() {
        if (this._assetType == ServiceInfo.AssetType.ONLY_POINT || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) {
            if ("SV000075".equals(this.serviceId)) {
                return String.valueOf(MyNanacoInfo.getValidPoint(this._points));
            }
            if ("SV000011".equals(this.serviceId)) {
                return String.valueOf(MyWaonInfo.getValidPoint(this._points));
            }
            return getPoint(0);
        }
        return "";
    }

    public List<ServiceInfo.History.HistoryData> getHistory() {
        return this._history;
    }

    public int getPointNum() {
        return this._points.size();
    }

    public String getPointExpiration(int index) {
        ServiceInfo.Point.PointData pointData;
        if ((this._assetType == ServiceInfo.AssetType.ONLY_POINT || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && index < this._points.size() && (pointData = this._points.get(index)) != null) {
            return String.valueOf(pointData.getExpiration());
        }
        return "";
    }

    public String getBalance() {
        ServiceInfo.PrepaidEmoney prepaidEmoney;
        int balance;
        if ((this._assetType == ServiceInfo.AssetType.ONLY_PREPAID || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && (prepaidEmoney = this.prepaidEmoney) != null && (balance = prepaidEmoney.getBalance()) >= 0) {
            return String.valueOf(balance);
        }
        return "-1";
    }

    public String getCreditLimit() {
        ServiceInfo.PostpayEmoney postpayEmoney;
        int creditLimit;
        if (this._assetType == ServiceInfo.AssetType.ONLY_POSTPAY && (postpayEmoney = this.postpayEmoney) != null && (creditLimit = postpayEmoney.getCreditLimit()) >= 0) {
            return NumberFormat.getNumberInstance().format(creditLimit);
        }
        return "";
    }

    public String getAvailableCredit() {
        ServiceInfo.PostpayEmoney postpayEmoney;
        int availableCredit;
        if (this._assetType == ServiceInfo.AssetType.ONLY_POSTPAY && (postpayEmoney = this.postpayEmoney) != null && (availableCredit = postpayEmoney.getAvailableCredit()) >= 0) {
            return NumberFormat.getNumberInstance().format(availableCredit);
        }
        return "";
    }

    public String getIDiDisplayName() {
        try {
            return this.cardAdditionalInfo.getAdditionalInfo().transitInfo.cardNumber.displayName;
        } catch (Exception unused) {
            return "";
        }
    }

    public String getIDiValue() {
        try {
            return this.cardAdditionalInfo.getAdditionalInfo().transitInfo.cardNumber.value;
        } catch (Exception unused) {
            return "";
        }
    }

    public void setPointData(List<ServiceInfo.Point.PointData> points) {
        this._points = points;
    }

    public void setHistory(List<ServiceInfo.History.HistoryData> history) {
        this._history = history;
    }

    public ServiceInfo.PrepaidEmoney getPrepaidEmoney() {
        return this.prepaidEmoney;
    }

    public ServiceInfo.PostpayEmoney getPostpaidEmoney() {
        return this.postpayEmoney;
    }

    public SupportMfiServiceType getUnsupportedMfiService1() {
        if (FeliCaParams.SERVICE_ID_SUICA.equals(this.serviceId)) {
            if ("D00000000000000000000000000000000000000000000000000000000000001".equals(cid())) {
                return SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1;
            }
            return SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1;
        }
        return SupportMfiServiceType.SUPPORTED_MFI_OTHER_SERVICE;
    }

    public boolean isCached() {
        return this.isCached;
    }
}
