package com.amazonaws.mobileconnectors.pinpoint.targeting.endpointProfile;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONBuilder;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable;
import java.util.MissingResourceException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EndpointProfileLocation implements JSONSerializable {
    private static final Log log = LogFactory.getLog((Class<?>) EndpointProfileLocation.class);
    private String country;
    private Double latitude = null;
    private Double longitude = null;
    private String postalCode = "";
    private String city = "";
    private String region = "";

    public EndpointProfileLocation(PinpointContext pinpointContext) {
        String country;
        this.country = "";
        try {
            country = pinpointContext.getApplicationContext().getResources().getConfiguration().locale.getISO3Country();
        } catch (MissingResourceException unused) {
            log.debug("Locale getISO3Country failed, falling back to getCountry.");
            country = pinpointContext.getApplicationContext().getResources().getConfiguration().locale.getCountry();
        }
        this.country = country;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double d) {
        if (d == null) {
            return;
        }
        this.latitude = d;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double d) {
        if (d == null) {
            return;
        }
        this.longitude = d;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String str) {
        if (str == null) {
            return;
        }
        this.postalCode = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        if (str == null) {
            return;
        }
        this.city = str;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String str) {
        if (str == null) {
            return;
        }
        this.region = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        if (str == null) {
            return;
        }
        this.country = str;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.internal.core.util.JSONSerializable
    public JSONObject toJSONObject() {
        JSONBuilder jSONBuilder = new JSONBuilder(null);
        jSONBuilder.withAttribute("Latitude", getLatitude());
        jSONBuilder.withAttribute("Longitude", getLongitude());
        jSONBuilder.withAttribute("PostalCode", getPostalCode());
        jSONBuilder.withAttribute("City", getCity());
        jSONBuilder.withAttribute("Region", getRegion());
        jSONBuilder.withAttribute("Country", getCountry());
        return jSONBuilder.toJSONObject();
    }
}
