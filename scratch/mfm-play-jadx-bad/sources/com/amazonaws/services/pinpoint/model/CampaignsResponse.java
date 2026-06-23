package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CampaignsResponse implements Serializable {
    private List<CampaignResponse> item;
    private String nextToken;

    public List<CampaignResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<CampaignResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public CampaignsResponse withItem(CampaignResponse... campaignResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(campaignResponseArr.length);
        }
        for (CampaignResponse campaignResponse : campaignResponseArr) {
            this.item.add(campaignResponse);
        }
        return this;
    }

    public CampaignsResponse withItem(Collection<CampaignResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public CampaignsResponse withNextToken(String str) {
        this.nextToken = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getItem() == null ? 0 : getItem().hashCode()) + 31) * 31) + (getNextToken() != null ? getNextToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignsResponse)) {
            return false;
        }
        CampaignsResponse campaignsResponse = (CampaignsResponse) obj;
        if ((campaignsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (campaignsResponse.getItem() != null && !campaignsResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((campaignsResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return campaignsResponse.getNextToken() == null || campaignsResponse.getNextToken().equals(getNextToken());
    }
}
