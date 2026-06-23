package com.amazonaws.mobileconnectors.pinpoint.analytics.monetization;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;

/* JADX INFO: loaded from: classes.dex */
public class VirtualMonetizationEventBuilder extends MonetizationEventBuilder {
    private static final Log log = LogFactory.getLog((Class<?>) VirtualMonetizationEventBuilder.class);

    protected VirtualMonetizationEventBuilder(AnalyticsClient analyticsClient) {
        super(analyticsClient);
        setStore("Virtual");
    }

    public static VirtualMonetizationEventBuilder create(AnalyticsClient analyticsClient) {
        return new VirtualMonetizationEventBuilder(analyticsClient);
    }

    public VirtualMonetizationEventBuilder withProductId(String str) {
        setProductId(str);
        return this;
    }

    public VirtualMonetizationEventBuilder withItemPrice(double d) {
        setItemPrice(Double.valueOf(d));
        return this;
    }

    public VirtualMonetizationEventBuilder withQuantity(Double d) {
        setQuantity(d);
        return this;
    }

    public VirtualMonetizationEventBuilder withCurrency(String str) {
        setCurrency(str);
        return this;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.analytics.monetization.MonetizationEventBuilder
    protected boolean isValid() {
        if (getProductId() == null) {
            log.warn("Virtual Monetization event is not valid: it is missing the product id");
            return false;
        }
        if (getQuantity() == null) {
            log.warn("Virtual Monetization event is not valid: it is missing the quantity");
            return false;
        }
        if (getItemPrice() == null) {
            log.warn("Virtual Monetization event is not valid: it is missing the numerical price");
            return false;
        }
        if (getCurrency() != null) {
            return true;
        }
        log.warn("Virtual Monetization event is not valid: it is missing the currency");
        return false;
    }
}
