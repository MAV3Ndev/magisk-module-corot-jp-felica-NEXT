package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfc.mfi.CardInfo;
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

/* JADX INFO: loaded from: classes.dex */
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
        public void setBalance(int i) {
            this._balance = i;
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
        public void setCreditLimit(int i) {
            this._creditLimit = i;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public int getAvailableCredit() {
            return this._availableCredit;
        }

        @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo.PostpayEmoney
        public void setAvailableCredit(int i) {
            this._availableCredit = i;
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
        void onComplete(Bitmap bitmap);
    }

    public enum SupportMfiServiceType {
        UNSUPPORTED_MFI_SERVICE_1,
        SUPPORTED_MFI_SERVICE_1,
        SUPPORTED_MFI_OTHER_SERVICE
    }

    public MyCardInfo(String str, String str2, String str3, CardStatus cardStatus, CardPosition cardPosition, String str4, MyCardAdditionalInfo myCardAdditionalInfo, NetworkExpert networkExpert) {
        this.serviceId = str;
        this.cardId = str2;
        this.cardCategory = str3;
        this.cardStatus = cardStatus;
        this.cardPosition = cardPosition;
        this.mfiAdditionalInfoHashValue = str4;
        this.cardAdditionalInfo = myCardAdditionalInfo;
        this._networkExpert = networkExpert;
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
        return "MyCardInfo{serviceId='" + this.serviceId + "', cardId='" + this.cardId + "', cardStatus=" + this.cardStatus + ", cardPosition=" + this.cardPosition + ", cardSpStatus=" + this.cardSpStatus + ", mfiAdditionalInfoHashValue=" + this.mfiAdditionalInfoHashValue + ", cardAdditionalInfo=" + this.cardAdditionalInfo + ", cardCategory='" + this.cardCategory + "', _networkExpert=" + this._networkExpert + ", _assetType=" + this._assetType + ", _points=" + this._points + ", _history=" + this._history + ", _balance=" + this.prepaidEmoney.getBalance() + ", _creditLimit=" + this.postpayEmoney.getCreditLimit() + ", _availableCredit=" + this.postpayEmoney.getAvailableCredit() + '}';
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getCardId() {
        return this.cardId;
    }

    public static String getServiceIdFromCid(String str) {
        try {
            return str.substring(7, 15);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getSpIfFromServiceType(String str) {
        try {
            return str.substring(1, 2);
        } catch (Exception unused) {
            return "";
        }
    }

    public boolean isSuicaCardId() {
        return TextUtils.equals(CardInfo.CID_UNSUPPORT_MFI_SERVICE_1, this.cardId);
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

    public void setCardAdditionalInfo(MyCardAdditionalInfo myCardAdditionalInfo) {
        this.cardAdditionalInfo = myCardAdditionalInfo;
    }

    public enum CardSpStatus {
        SP_STATUS_REISSUABLE(1),
        SP_STATUS_UNREISSUABLE(2),
        SP_STATUS_PENDING(3),
        SP_STATUS_UNKNOWN(4),
        SP_STATUS_NOT_APPLICABLE(5);

        private final int id;

        CardSpStatus(int i) {
            this.id = i;
        }

        public int getInt() {
            return this.id;
        }

        public static CardSpStatus getType(int i) {
            for (CardSpStatus cardSpStatus : values()) {
                if (cardSpStatus.getInt() == i) {
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

        public void setIcon(String str, Bitmap bitmap) {
            this.iconMap.put(str, bitmap);
        }

        public Bitmap getIcon(String str) {
            return this.iconMap.get(str);
        }

        public boolean hasIcon(String str) {
            return this.iconMap.containsKey(str);
        }
    }

    public String getCardFaceImgUrl() {
        try {
            return this.cardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.cardArtUrl;
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean orderIconImg(final Context context, final String str, final OrderImgListener orderImgListener) {
        if (str == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.info.MyCardInfo.3
            @Override // java.lang.Runnable
            public void run() {
                BitmapCache bmpCage = MyCardInfo.this.getBmpCage();
                Bitmap icon = bmpCage.hasIcon(str) ? bmpCage.getIcon(str) : MyCardInfo.this.getImage(context, str);
                if (icon == null) {
                    Resources resources = context.getResources();
                    icon = BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_DEFAULT_SERVICE_ICON), MyCardInfo.RESOURCE_DRAWABLE, context.getPackageName()));
                } else {
                    bmpCage.setIcon(str, icon);
                }
                orderImgListener.onComplete(icon);
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
    public Bitmap getImage(Context context, String str) {
        Bitmap bitmapDecodeByteArray;
        try {
            if (str.startsWith(RESOURCE_DRAWABLE) && !"".equals(str)) {
                Resources resources = context.getApplicationContext().getResources();
                bitmapDecodeByteArray = BitmapFactory.decodeResource(resources, resources.getIdentifier(str, RESOURCE_DRAWABLE, context.getPackageName()));
            } else if (str.startsWith(RESOURCE_MIPMAP) && !"".equals(str)) {
                Resources resources2 = context.getResources();
                bitmapDecodeByteArray = BitmapFactory.decodeResource(resources2, resources2.getIdentifier(str, RESOURCE_MIPMAP, context.getPackageName()));
            } else {
                if (!str.startsWith(PRIFIX_HTTP)) {
                    return null;
                }
                ImageProtocol imageProtocol = this._networkExpert.getImageProtocol();
                ImageProtocol.Result result = imageProtocol.parse(this._networkExpert.connect(imageProtocol.create(new ImageProtocol.Parameter(str))));
                bitmapDecodeByteArray = BitmapFactory.decodeByteArray(result.bytes, 0, result.bytes.length);
            }
            return bitmapDecodeByteArray;
        } catch (Throwable th) {
            LogUtil.warning(th);
            return null;
        }
    }

    private boolean isDcardMini() {
        return FeliCaParams.SERVICE_ID_DCARD.equals(getServiceId()) && this._assetType == ServiceInfo.AssetType.ONLY_POSTPAY;
    }

    public void setAssetType(ServiceInfo.AssetType assetType) {
        this._assetType = assetType;
    }

    public String getPoint(int i) {
        ServiceInfo.Point.PointData pointData;
        int point;
        return ((this._assetType == ServiceInfo.AssetType.ONLY_POINT || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && i < this._points.size() && (pointData = this._points.get(i)) != null && (point = pointData.getPoint()) >= 0) ? String.valueOf(point) : "";
    }

    public String getValidPoint() {
        if (this._assetType != ServiceInfo.AssetType.ONLY_POINT && this._assetType != ServiceInfo.AssetType.PREPAID_AND_POINT) {
            return "";
        }
        if ("SV000075".equals(this.serviceId)) {
            return String.valueOf(MyNanacoInfo.getValidPoint(this._points));
        }
        if ("SV000011".equals(this.serviceId)) {
            return String.valueOf(MyWaonInfo.getValidPoint(this._points));
        }
        return getPoint(0);
    }

    public List<ServiceInfo.History.HistoryData> getHistory() {
        return this._history;
    }

    public int getPointNum() {
        return this._points.size();
    }

    public String getPointExpiration(int i) {
        ServiceInfo.Point.PointData pointData;
        return ((this._assetType == ServiceInfo.AssetType.ONLY_POINT || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && i < this._points.size() && (pointData = this._points.get(i)) != null) ? String.valueOf(pointData.getExpiration()) : "";
    }

    public String getBalance() {
        ServiceInfo.PrepaidEmoney prepaidEmoney;
        int balance;
        return ((this._assetType == ServiceInfo.AssetType.ONLY_PREPAID || this._assetType == ServiceInfo.AssetType.PREPAID_AND_POINT) && (prepaidEmoney = this.prepaidEmoney) != null && (balance = prepaidEmoney.getBalance()) >= 0) ? String.valueOf(balance) : "-1";
    }

    public String getCreditLimit() {
        ServiceInfo.PostpayEmoney postpayEmoney;
        int creditLimit;
        return (this._assetType != ServiceInfo.AssetType.ONLY_POSTPAY || (postpayEmoney = this.postpayEmoney) == null || (creditLimit = postpayEmoney.getCreditLimit()) < 0) ? "" : NumberFormat.getNumberInstance().format(creditLimit);
    }

    public String getAvailableCredit() {
        ServiceInfo.PostpayEmoney postpayEmoney;
        int availableCredit;
        return (this._assetType != ServiceInfo.AssetType.ONLY_POSTPAY || (postpayEmoney = this.postpayEmoney) == null || (availableCredit = postpayEmoney.getAvailableCredit()) < 0) ? "" : NumberFormat.getNumberInstance().format(availableCredit);
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

    public void setPointData(List<ServiceInfo.Point.PointData> list) {
        this._points = list;
    }

    public void setHistory(List<ServiceInfo.History.HistoryData> list) {
        this._history = list;
    }

    public ServiceInfo.PrepaidEmoney getPrepaidEmoney() {
        return this.prepaidEmoney;
    }

    public ServiceInfo.PostpayEmoney getPostpaidEmoney() {
        return this.postpayEmoney;
    }

    public SupportMfiServiceType getUnsupportedMfiService1() {
        if (FeliCaParams.SERVICE_ID_SUICA.equals(this.serviceId)) {
            if (CardInfo.CID_UNSUPPORT_MFI_SERVICE_1.equals(cid())) {
                return SupportMfiServiceType.UNSUPPORTED_MFI_SERVICE_1;
            }
            return SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1;
        }
        return SupportMfiServiceType.SUPPORTED_MFI_OTHER_SERVICE;
    }
}
