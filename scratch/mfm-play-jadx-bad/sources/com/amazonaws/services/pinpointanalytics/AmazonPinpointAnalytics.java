package com.amazonaws.services.pinpointanalytics;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.pinpointanalytics.model.PutEventsRequest;

/* JADX INFO: loaded from: classes.dex */
public interface AmazonPinpointAnalytics {
    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    void putEvents(PutEventsRequest putEventsRequest) throws AmazonClientException;

    void setEndpoint(String str) throws IllegalArgumentException;

    void setRegion(Region region) throws IllegalArgumentException;

    void shutdown();
}
