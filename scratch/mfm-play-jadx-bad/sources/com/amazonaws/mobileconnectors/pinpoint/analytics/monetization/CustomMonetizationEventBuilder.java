package com.amazonaws.mobileconnectors.pinpoint.analytics.monetization;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;

/* JADX INFO: loaded from: classes.dex */
public class CustomMonetizationEventBuilder extends MonetizationEventBuilder {
    private static final Log log = LogFactory.getLog((Class<?>) CustomMonetizationEventBuilder.class);

    protected CustomMonetizationEventBuilder(AnalyticsClient analyticsClient) {
        super(analyticsClient);
    }

    public static CustomMonetizationEventBuilder create(AnalyticsClient analyticsClient) {
        return new CustomMonetizationEventBuilder(analyticsClient);
    }

    public CustomMonetizationEventBuilder withStore(String str) {
        setStore(str);
        return this;
    }

    public CustomMonetizationEventBuilder withProductId(String str) {
        setProductId(str);
        return this;
    }

    public CustomMonetizationEventBuilder withQuantity(Double d) {
        setQuantity(d);
        return this;
    }

    @Deprecated
    public CustomMonetizationEventBuilder withFormattedItemPrice(String str) {
        setFormattedItemPrice(str);
        return this;
    }

    public CustomMonetizationEventBuilder withItemPrice(double d) {
        setItemPrice(Double.valueOf(d));
        return this;
    }

    public CustomMonetizationEventBuilder withCurrency(String str) {
        setCurrency(str);
        return this;
    }

    public CustomMonetizationEventBuilder withTransactionId(String str) {
        setTransactionId(str);
        return this;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.analytics.monetization.MonetizationEventBuilder
    protected boolean isValid() {
        if (getStore() == null) {
            log.warn("Custom Monetization event is not valid: it is missing the store");
            return false;
        }
        if (getProductId() == null) {
            log.warn("Custom Monetization event is not valid: it is missing the product id");
            return false;
        }
        if (getQuantity() == null) {
            log.warn("Custom Monetization event is not valid: it is missing the quantity");
            return false;
        }
        if (getCurrency() != null && getItemPrice() != null) {
            return true;
        }
        log.warn("Custom Monetization event is not valid: it requires the formatted localized price or the currency and price");
        return false;
    }
}
