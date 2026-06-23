package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ChannelsResponse implements Serializable {
    private Map<String, ChannelResponse> channels;

    public Map<String, ChannelResponse> getChannels() {
        return this.channels;
    }

    public void setChannels(Map<String, ChannelResponse> map) {
        this.channels = map;
    }

    public ChannelsResponse withChannels(Map<String, ChannelResponse> map) {
        this.channels = map;
        return this;
    }

    public ChannelsResponse addChannelsEntry(String str, ChannelResponse channelResponse) {
        if (this.channels == null) {
            this.channels = new HashMap();
        }
        if (this.channels.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.channels.put(str, channelResponse);
        return this;
    }

    public ChannelsResponse clearChannelsEntries() {
        this.channels = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getChannels() != null) {
            sb.append("Channels: " + getChannels());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getChannels() == null ? 0 : getChannels().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ChannelsResponse)) {
            return false;
        }
        ChannelsResponse channelsResponse = (ChannelsResponse) obj;
        if ((channelsResponse.getChannels() == null) ^ (getChannels() == null)) {
            return false;
        }
        return channelsResponse.getChannels() == null || channelsResponse.getChannels().equals(getChannels());
    }
}
