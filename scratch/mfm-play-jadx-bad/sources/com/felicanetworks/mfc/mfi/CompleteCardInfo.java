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

/* JADX INFO: loaded from: classes3.dex */
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
        public String cardType;
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

    public CompleteCardInfo(String cid, String serviceId, String walletAppId, int state, int position, int task, boolean reissuePossibility, String serviceType, String additionalInfoHash, String cardCategory, String appletInstanceAid, Finish finish, ReissueStatus reissueStatus, String seId, byte[] idm, int[] nodeCodeList, String cardType) {
        super(cid, serviceId, walletAppId, state, position, task, reissuePossibility, serviceType, additionalInfoHash, cardCategory, cardType);
        this.mFinish = Finish.EMPTY;
        this.mSpType = SpType.NOT_INDIVIDUAL_SP;
        this.mAppletInstanceAid = appletInstanceAid;
        this.mFinish = finish;
        this.mReissueStatus = reissueStatus;
        this.mSeId = seId;
        this.mIdm = idm;
        this.mNodeCodeList = nodeCodeList;
    }

    protected CompleteCardInfo(Parcel in) {
        super(in);
        this.mFinish = Finish.EMPTY;
        this.mSpType = SpType.NOT_INDIVIDUAL_SP;
    }

    public CompleteCardInfo(CardInfo src, Cache cache) {
        super(src.getCid(), src.getServiceId(), src.getWalletAppId(), src.getCardStatus(), src.getCardPosition(), src.getTask(), src.getReissuePossibility(), src.getServiceType(), src.getAdditionalInfoHash(), src.getCardCategory(), cache.cardType);
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

    public CompleteCardInfo(CompleteCardInfo src) {
        this(src.getCid(), src.getServiceId(), src.getWalletAppId(), src.getCardStatus(), src.getCardPosition(), src.getTask(), src.getReissuePossibility(), src.getServiceType(), src.getAdditionalInfoHash(), src.getCardCategory(), src.mAppletInstanceAid, src.mFinish, src.mReissueStatus, src.mSeId, src.mIdm, src.mNodeCodeList, src.getCardType());
        setSpType(src.mSpType);
        setSpStatus(src.mSpStatus);
        setSpAdditionalInfo(src.mSpAdditionalInfo);
    }

    public void setFixStatus(int fixStatus) {
        this.mState = fixStatus;
        if (this.mState == 0 || 2 == this.mState) {
            setPosition(3);
        }
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public void setAppletInstanceAid(String aid) {
        this.mAppletInstanceAid = aid;
    }

    public void setSeId(String seId) {
        this.mSeId = seId;
    }

    public void setIdm(byte[] idm) {
        this.mIdm = idm;
    }

    public void setSpAdditionalInfo(JSONObject spAdditionalInfo) {
        this.mSpAdditionalInfo = spAdditionalInfo;
    }

    public void setSpStatus(int spStatus) {
        this.mSpStatus = spStatus;
    }

    public void setSpType(SpType spType) {
        this.mSpType = spType;
    }

    public void setAdditionalInfoHash(String additionalInfoHash) {
        this.mAdditionalInfoHash = additionalInfoHash;
    }

    public boolean statusIs(int state) {
        return this.mState == state;
    }

    public boolean statusIs(int state, int position) {
        return this.mState == state && this.mPosition == position;
    }

    public boolean statusIs(int state, int position, Finish finish) {
        return this.mState == state && this.mPosition == position && this.mFinish == finish;
    }

    public boolean statusIs(int state, Finish finish) {
        return this.mState == state && this.mFinish == finish;
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
        cache.cardType = this.mCardType;
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
        cache.cardType = this.mCardType;
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
