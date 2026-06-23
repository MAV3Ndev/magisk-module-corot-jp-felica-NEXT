package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ServiceInfo implements ServiceIdPolicy.ServiceIdHolder {
    protected static final int INVALID_ASSET_VALUE = -1;
    protected DatabaseExpert _db;
    protected String _id;
    protected Linkage _linkage;
    protected String _name;
    protected String _provider;
    protected String _version;

    interface AssetInterface {
    }

    public enum AssetType {
        NONE,
        ONLY_PREPAID,
        ONLY_POSTPAY,
        ONLY_POINT,
        PREPAID_AND_POINT
    }

    public interface PostpayEmoney extends AssetInterface {
        int getAvailableCredit();

        int getCreditLimit();

        void setAvailableCredit(int i);

        void setCreditLimit(int i);
    }

    public interface PrepaidEmoney extends AssetInterface {
        int getBalance();

        void setBalance(int i);
    }

    public enum UseType {
        CHARGE,
        PAYMENT,
        TRAFFIC,
        OTHER
    }

    public String toString() {
        return "ServiceInfo{_id='" + this._id + "', _version='" + this._version + "', _name='" + this._name + "', _provider='" + this._provider + "', _linkage=" + this._linkage + ", _db=" + this._db + '}';
    }

    public ServiceInfo(String str, String str2, String str3, String str4, Linkage linkage, DatabaseExpert databaseExpert) {
        this._id = str;
        this._version = str2;
        this._name = str3;
        this._provider = str4;
        this._linkage = linkage;
        this._db = databaseExpert;
    }

    @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
    public String sid() {
        return this._id;
    }

    public String getId() {
        return this._id;
    }

    public String getVersion() {
        return this._version;
    }

    public String getName() {
        return this._name;
    }

    public String getProvider() {
        return this._provider;
    }

    public Bitmap getIcon(Context context) {
        Resources resources;
        int identifier;
        try {
            byte[] icon = this._db.getIcon(this._id);
            Bitmap bitmapDecodeByteArray = icon != null ? BitmapFactory.decodeByteArray(icon, 0, icon.length) : null;
            if (bitmapDecodeByteArray != null) {
                return bitmapDecodeByteArray;
            }
        } finally {
            try {
            } finally {
            }
        }
        return BitmapFactory.decodeResource(resources, identifier);
    }

    public Linkage getLinkage() {
        return this._linkage;
    }

    public boolean hasAsset() {
        return this instanceof AssetInterface;
    }

    public AssetType getAssetType() {
        AssetType assetType = AssetType.NONE;
        if (!hasPrepaidEmoney()) {
            return hasPostpayEmoney() ? AssetType.ONLY_POSTPAY : assetType;
        }
        if (hasPoint()) {
            return AssetType.PREPAID_AND_POINT;
        }
        return AssetType.ONLY_PREPAID;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PostpayEmoney getPostpayEmoney() {
        if (this instanceof PostpayEmoney) {
            return (PostpayEmoney) this;
        }
        return null;
    }

    public boolean hasPostpayEmoney() {
        return getPostpayEmoney() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PrepaidEmoney getPrepaidEmoney() {
        if (this instanceof PrepaidEmoney) {
            return (PrepaidEmoney) this;
        }
        return null;
    }

    public boolean hasPrepaidEmoney() {
        return getPrepaidEmoney() != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Point getPoint() {
        if (this instanceof Point) {
            return (Point) this;
        }
        return null;
    }

    public boolean hasPoint() {
        return getPoint() != null;
    }

    public boolean isFelicaPocketService() {
        return this._id.equals(FeliCaParams.SERVICE_ID_FP);
    }

    public interface Point extends AssetInterface {
        List<PointData> getPointDataList();

        int getValidPoint();

        void setPointDataList(List<PointData> list);

        public static class PointData {
            private Date _expiration;
            private int _point;

            public String toString() {
                return "PointData{_point=" + this._point + ", _expiration=" + this._expiration + '}';
            }

            public PointData(int i, Date date) {
                this._point = i;
                this._expiration = date != null ? (Date) date.clone() : null;
            }

            public int getPoint() {
                return this._point;
            }

            public Date getExpiration() {
                Date date = this._expiration;
                if (date != null) {
                    return (Date) date.clone();
                }
                return null;
            }
        }
    }

    public interface History extends AssetInterface {
        List<HistoryData> getHistoryDataList();

        void setHistoryDataList(List<HistoryData> list);

        public static class HistoryData {
            private int _block;
            private int _chargemoney;
            private boolean _isplus;
            private int _money;
            private UseType _usetype;
            private String _ymd;

            public enum UseType {
                CHARGE,
                PAYMENT,
                TRAFFIC,
                OTHER,
                CHARGE_AND_PAYMENT
            }

            public String toString() {
                return "HistoryData{_ymd=" + this._ymd + ", _usetype=" + this._usetype + ", _money=" + this._money + ", _chargemoney=" + this._chargemoney + ", _isplus=" + this._isplus + ", _block=" + this._block + '}';
            }

            public HistoryData(String str, UseType useType, int i, boolean z) {
                this._ymd = str;
                this._usetype = useType;
                this._money = i;
                this._isplus = z;
            }

            public HistoryData(String str, UseType useType, int i, int i2, boolean z, int i3) {
                this._ymd = str;
                this._usetype = useType;
                this._money = i;
                this._chargemoney = i2;
                this._isplus = z;
                this._block = i3;
            }

            public String getDate() {
                return this._ymd;
            }

            public UseType getUseType() {
                return this._usetype;
            }

            public int getMoney() {
                return this._money;
            }

            public int getChargeMoney() {
                return this._chargemoney;
            }

            public boolean getIsPlus() {
                return this._isplus;
            }

            public int getBlock() {
                return this._block;
            }
        }
    }
}
