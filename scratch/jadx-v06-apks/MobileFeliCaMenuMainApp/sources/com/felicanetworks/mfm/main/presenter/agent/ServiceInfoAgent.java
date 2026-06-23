package com.felicanetworks.mfm.main.presenter.agent;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ServiceInfoAgent extends InfoAgent implements ServiceIdPolicy.ServiceIdHolder {
    private ServiceInfo _client;
    protected LinkageAgent _linkage;

    public enum AssetType {
        NONE,
        ONLY_PREPAID,
        ONLY_POSTPAY,
        PREPAID_AND_POINT
    }

    public ServiceInfoAgent(ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = serviceInfo;
        this._linkage = new LinkageAgent(serviceInfo.getLinkage());
    }

    @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
    public String sid() {
        return getId();
    }

    public String getId() {
        return this._client.getId();
    }

    public String getVersion() {
        return this._client.getVersion();
    }

    public String getName() {
        return this._client.getName();
    }

    public String getProvider() {
        return this._client.getProvider();
    }

    public Bitmap getIcon() {
        return this._client.getIcon(PresenterData.getInstance().getContext());
    }

    public LinkageAgent getLinkage() {
        return this._linkage;
    }

    public AssetType getAssetType() {
        return AssetType.valueOf(this._client.getAssetType().name());
    }

    public boolean isFelicaPocketService() {
        return this._client.isFelicaPocketService();
    }

    boolean hasAsset() {
        return this._client.hasAsset();
    }

    public PostpayEmoney getPostpayEmoney() {
        if (this._client.hasPostpayEmoney()) {
            return new PostpayEmoney(this._client.getPostpayEmoney());
        }
        return null;
    }

    public boolean hasPostpayEmoney() {
        return this._client.hasPostpayEmoney();
    }

    public PrepaidEmoney getPrepaidEmoney() {
        if (this._client.hasPrepaidEmoney()) {
            return new PrepaidEmoney(this._client.getPrepaidEmoney());
        }
        return null;
    }

    public boolean hasPrepaidEmoney() {
        return this._client.hasPrepaidEmoney();
    }

    public Point getPoint() {
        if (this._client.hasPoint()) {
            return new Point(this._client.getPoint());
        }
        return null;
    }

    public boolean hasPoint() {
        return this._client.hasPoint();
    }

    public static class Point {
        private ServiceInfo.Point _client;

        Point(ServiceInfo.Point point) {
            this._client = point;
        }

        public List<PointData> getPointDataList() {
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.add(new PointData(new ServiceInfo.Point.PointData(this._client.getValidPoint(), null)));
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                return null;
            }
        }

        public static class PointData {
            private ServiceInfo.Point.PointData _client;

            PointData(ServiceInfo.Point.PointData pointData) {
                this._client = pointData;
            }

            public int getPoint() {
                return this._client.getPoint();
            }

            public Date getExpiration() {
                return this._client.getExpiration();
            }
        }
    }

    public static class PostpayEmoney {
        private ServiceInfo.PostpayEmoney _client;

        PostpayEmoney(ServiceInfo.PostpayEmoney postpayEmoney) {
            this._client = postpayEmoney;
        }

        public int getCreditLimit() {
            return this._client.getCreditLimit();
        }

        public int getAvailableCredit() {
            return this._client.getAvailableCredit();
        }
    }

    public static class PrepaidEmoney {
        private ServiceInfo.PrepaidEmoney _client;

        PrepaidEmoney(ServiceInfo.PrepaidEmoney prepaidEmoney) {
            this._client = prepaidEmoney;
        }

        public int getBalance() {
            return this._client.getBalance();
        }
    }
}
