package com.amazonaws.mobileconnectors.pinpoint.analytics.monetization;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;

/* JADX INFO: loaded from: classes.dex */
public class AmazonMonetizationEventBuilder extends MonetizationEventBuilder {
    private static final Log log = LogFactory.getLog((Class<?>) AmazonMonetizationEventBuilder.class);

    protected AmazonMonetizationEventBuilder(AnalyticsClient analyticsClient) {
        super(analyticsClient);
        setStore("Amazon");
    }

    public static AmazonMonetizationEventBuilder create(AnalyticsClient analyticsClient) {
        return new AmazonMonetizationEventBuilder(analyticsClient);
    }

    public AmazonMonetizationEventBuilder withProductId(String str) {
        setProductId(str);
        return this;
    }

    @Deprecated
    public AmazonMonetizationEventBuilder withFormattedItemPrice(String str) {
        setFormattedItemPrice(str);
        return this;
    }

    public AmazonMonetizationEventBuilder withQuantity(Double d) {
        setQuantity(d);
        return this;
    }

    public AmazonMonetizationEventBuilder withItemPrice(Double d) {
        setItemPrice(d);
        return this;
    }

    public AmazonMonetizationEventBuilder withCurrency(String str) {
        setCurrency(str);
        return this;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.analytics.monetization.MonetizationEventBuilder
    protected boolean isValid() {
        if (getProductId() == null) {
            log.warn("Amazon Monetization event is not valid: it is missing the product id");
            return false;
        }
        if (getQuantity() == null) {
            log.warn("Amazon Monetization event is not valid: it is missing the quantity");
            return false;
        }
        if (getFormattedItemPrice() != null) {
            return true;
        }
        if (getCurrency() == null) {
            log.warn("Amazon Monetization event is not valid: it is missing the localized currency");
            return false;
        }
        if (getItemPrice() != null) {
            return true;
        }
        log.warn("Amazon Monetization event is not valid: it is missing the localized item price");
        return false;
    }
}
