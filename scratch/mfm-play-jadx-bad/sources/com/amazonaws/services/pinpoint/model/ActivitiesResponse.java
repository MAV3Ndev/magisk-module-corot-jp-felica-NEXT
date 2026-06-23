package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ActivitiesResponse implements Serializable {
    private List<ActivityResponse> item;
    private String nextToken;

    public List<ActivityResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<ActivityResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public ActivitiesResponse withItem(ActivityResponse... activityResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(activityResponseArr.length);
        }
        for (ActivityResponse activityResponse : activityResponseArr) {
            this.item.add(activityResponse);
        }
        return this;
    }

    public ActivitiesResponse withItem(Collection<ActivityResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public ActivitiesResponse withNextToken(String str) {
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
        if (obj == null || !(obj instanceof ActivitiesResponse)) {
            return false;
        }
        ActivitiesResponse activitiesResponse = (ActivitiesResponse) obj;
        if ((activitiesResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (activitiesResponse.getItem() != null && !activitiesResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((activitiesResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return activitiesResponse.getNextToken() == null || activitiesResponse.getNextToken().equals(getNextToken());
    }
}
