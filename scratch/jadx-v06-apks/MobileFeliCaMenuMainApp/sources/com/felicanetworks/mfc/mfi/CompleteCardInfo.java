package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.text.TextUtils;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetCardListResponseJson;
import com.felicanetworks.mfc.mfi.util.AssetsUtil;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CompleteCardInfo extends CardInfo {
    private static final String INDIVIDUAL_SP_CARD_ASSETS_NAME = "individual_cards.json";
    protected String mAppletInstanceAid;
    protected Cache mCache;
    protected Finish mFinish;
    protected byte[] mIdm;
    protected int[] mNodeCodeList;
    protected ReissueStatus mReissueStatus;
    protected String mSeId;
    protected JSONObject mSpAdditionalInfo;
    protected int mSpStatus;
    protected SpType mSpType;

    public static class Cache implements Serializable {
        private static final long serialVersionUID = -2087642798382730766L;
        public String additionalInfoHash;
        public String appletInstanceAid;
        public String cardCategory;
        public String cid;
        public Finish finish;
        public byte[] idm;
        public int[] nodeCodeList;
        public int position;
        public boolean reissuePossibility;
        public ReissueStatus reissueStatus;
        public String seId;
        public String serviceId;
        public String serviceType;
        public SpType spType;
        public int state;
        public int task;
        public String walletAppId;
    }

    public enum Finish {
        EMPTY,
        NOT_YET,
        DONE
    }

    public enum ReissueStatus {
        REISSUABLE,
        UNREISSUABLE,
        PENDING,
        UNKNOWN
    }

    public enum SpType {
        INDIVIDUAL_SP,
        NOT_INDIVIDUAL_SP
    }

    public CompleteCardInfo(String str, String str2, String str3, int i, int i2, int i3, boolean z, String str4, String str5, String str6, String str7, Finish finish, ReissueStatus reissueStatus, String str8, byte[] bArr, int[] iArr) {
        super(str, str2, str3, i, i2, i3, z, str4, str5, str6);
        this.mFinish = Finish.EMPTY;
        this.mSpType = SpType.NOT_INDIVIDUAL_SP;
        this.mAppletInstanceAid = str7;
        this.mFinish = finish;
        this.mReissueStatus = reissueStatus;
        this.mSeId = str8;
        this.mIdm = bArr;
        this.mNodeCodeList = iArr;
    }

    protected CompleteCardInfo(Parcel parcel) {
        super(parcel);
        this.mFinish = Finish.EMPTY;
        this.mSpType = SpType.NOT_INDIVIDUAL_SP;
    }

    public CompleteCardInfo(CardInfo cardInfo, Cache cache) {
        super(cardInfo.getCid(), cardInfo.getServiceId(), cardInfo.getWalletAppId(), cardInfo.getCardStatus(), cardInfo.getCardPosition(), cardInfo.getTask(), cardInfo.getReissuePossibility(), cardInfo.getServiceType(), cardInfo.getAdditionalInfoHash(), cardInfo.getCardCategory());
        this.mFinish = Finish.EMPTY;
        this.mSpType = SpType.NOT_INDIVIDUAL_SP;
        this.mAppletInstanceAid = cache.appletInstanceAid;
        this.mFinish = cache.finish;
        this.mReissueStatus = cache.reissueStatus;
        this.mSeId = cache.seId;
        this.mIdm = cache.idm;
        this.mNodeCodeList = cache.nodeCodeList;
        this.mCache = cache;
    }

    public CompleteCardInfo(CompleteCardInfo completeCardInfo) {
        this(completeCardInfo.getCid(), completeCardInfo.getServiceId(), completeCardInfo.getWalletAppId(), completeCardInfo.getCardStatus(), completeCardInfo.getCardPosition(), completeCardInfo.getTask(), completeCardInfo.getReissuePossibility(), completeCardInfo.getServiceType(), completeCardInfo.getAdditionalInfoHash(), completeCardInfo.getCardCategory(), completeCardInfo.mAppletInstanceAid, completeCardInfo.mFinish, completeCardInfo.mReissueStatus, completeCardInfo.mSeId, completeCardInfo.mIdm, completeCardInfo.mNodeCodeList);
        setSpType(completeCardInfo.mSpType);
        setSpStatus(completeCardInfo.mSpStatus);
        setSpAdditionalInfo(completeCardInfo.mSpAdditionalInfo);
    }

    public void setFixStatus(int i) {
        this.mState = i;
        if (this.mState == 0 || 2 == this.mState) {
            setPosition(3);
        }
    }

    public void setPosition(int i) {
        this.mPosition = i;
    }

    public void setAppletInstanceAid(String str) {
        this.mAppletInstanceAid = str;
    }

    public void setSeId(String str) {
        this.mSeId = str;
    }

    public void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }

    public void setSpAdditionalInfo(JSONObject jSONObject) {
        this.mSpAdditionalInfo = jSONObject;
    }

    public void setSpStatus(int i) {
        this.mSpStatus = i;
    }

    public void setSpType(SpType spType) {
        this.mSpType = spType;
    }

    public void setAdditionalInfoHash(String str) {
        this.mAdditionalInfoHash = str;
    }

    public boolean statusIs(int i) {
        return this.mState == i;
    }

    public boolean statusIs(int i, int i2) {
        return this.mState == i && this.mPosition == i2;
    }

    public boolean statusIs(int i, int i2, Finish finish) {
        return this.mState == i && this.mPosition == i2 && this.mFinish == finish;
    }

    public boolean statusIs(int i, Finish finish) {
        return this.mState == i && this.mFinish == finish;
    }

    public Cache getCacheableData() {
        Cache cache = new Cache();
        Cache cache2 = this.mCache;
        if (cache2 != null) {
            return cache2;
        }
        cache.cid = this.mCid;
        cache.serviceId = this.mServiceId;
        cache.state = this.mState;
        cache.position = this.mPosition;
        cache.finish = this.mFinish;
        cache.reissueStatus = this.mReissueStatus;
        cache.appletInstanceAid = this.mAppletInstanceAid;
        if (this.mSpType == SpType.INDIVIDUAL_SP) {
            cache.serviceType = MfiClientConst.INDIVIDUAL_SP_INNER_SERVICE_TYPE;
        } else {
            cache.serviceType = this.mServiceType;
        }
        cache.spType = this.mSpType;
        cache.seId = this.mSeId;
        cache.idm = this.mIdm;
        cache.nodeCodeList = this.mNodeCodeList;
        cache.cardCategory = this.mCardCategory;
        cache.walletAppId = this.mWalletAppId;
        cache.task = this.mTask;
        cache.reissuePossibility = this.mReissuePossibility;
        cache.additionalInfoHash = this.mAdditionalInfoHash;
        return cache;
    }

    public Cache getForeignDataAsCache() throws JSONException {
        List<CardJson> cardList = new GetCardListResponseJson(AssetsUtil.load("individual_cards.json")).getCardList();
        Cache cache = new Cache();
        cache.cid = this.mCid;
        cache.serviceId = this.mServiceId;
        cache.state = this.mState;
        cache.position = this.mPosition;
        cache.finish = this.mFinish;
        cache.reissueStatus = this.mReissueStatus;
        cache.appletInstanceAid = this.mAppletInstanceAid;
        if (this.mSpType == SpType.INDIVIDUAL_SP) {
            Iterator<CardJson> it = cardList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CardJson next = it.next();
                if (TextUtils.equals(next.getCid(), this.mCid)) {
                    cache.serviceType = next.getServiceType();
                    break;
                }
            }
        } else {
            cache.serviceType = this.mServiceType;
        }
        cache.spType = this.mSpType;
        cache.seId = this.mSeId;
        cache.idm = this.mIdm;
        cache.nodeCodeList = this.mNodeCodeList;
        cache.cardCategory = this.mCardCategory;
        cache.walletAppId = this.mWalletAppId;
        cache.task = this.mTask;
        cache.reissuePossibility = this.mReissuePossibility;
        cache.additionalInfoHash = this.mAdditionalInfoHash;
        return cache;
    }

    public ReissueStatus getReissueStatus() {
        return this.mReissueStatus;
    }

    public String getSeId() {
        return this.mSeId;
    }

    public byte[] getIdm() {
        return this.mIdm;
    }

    public int[] getNodeCodeList() {
        return this.mNodeCodeList;
    }

    public String getAppletInstanceAid() {
        return this.mAppletInstanceAid;
    }

    public int getSpStatus() {
        return this.mSpStatus;
    }

    public JSONObject getSpAdditionalInfo() {
        return this.mSpAdditionalInfo;
    }

    public String dump() {
        return "mState=" + this.mState + ", mPosition=" + this.mPosition + ", mFinish=" + this.mFinish.name() + ", mAppletInstanceAid=" + this.mAppletInstanceAid;
    }
}
