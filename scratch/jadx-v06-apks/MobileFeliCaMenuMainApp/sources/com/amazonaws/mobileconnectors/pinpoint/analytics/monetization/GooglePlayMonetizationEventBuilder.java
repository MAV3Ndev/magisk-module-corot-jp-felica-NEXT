package com.amazonaws.mobileconnectors.pinpoint.analytics.monetization;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;

/* JADX INFO: loaded from: classes.dex */
public class GooglePlayMonetizationEventBuilder extends MonetizationEventBuilder {
    private static final Log log = LogFactory.getLog((Class<?>) GooglePlayMonetizationEventBuilder.class);

    protected GooglePlayMonetizationEventBuilder(AnalyticsClient analyticsClient) {
        super(analyticsClient);
        setStore("Google Play");
    }

    public static GooglePlayMonetizationEventBuilder create(AnalyticsClient analyticsClient) {
        return new GooglePlayMonetizationEventBuilder(analyticsClient);
    }

    public GooglePlayMonetizationEventBuilder withProductId(String str) {
        setProductId(str);
        return this;
    }

    @Deprecated
    public GooglePlayMonetizationEventBuilder withFormattedItemPrice(String str) {
        setFormattedItemPrice(str);
        return this;
    }

    public GooglePlayMonetizationEventBuilder withQuantity(Double d) {
        setQuantity(d);
        return this;
    }

    public GooglePlayMonetizationEventBuilder withItemPrice(Double d) {
        setItemPrice(d);
        return this;
    }

    public GooglePlayMonetizationEventBuilder withCurrency(String str) {
        setCurrency(str);
        return this;
    }

    public GooglePlayMonetizationEventBuilder withTransactionId(String str) {
        setTransactionId(str);
        return this;
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.analytics.monetization.MonetizationEventBuilder
    protected boolean isValid() {
        if (getProductId() == null) {
            log.warn("Cannot build Google Monetization event: it is missing the product id");
            return false;
        }
        if (getQuantity() == null) {
            log.warn("Google Monetization event is not valid: it is missing the quantity");
            return false;
        }
        if (getFormattedItemPrice() == null) {
            if (getCurrency() == null) {
                log.warn("Google Monetization event is not valid: it is missing the localized currency");
                return false;
            }
            if (getItemPrice() == null) {
                log.warn("Google Monetization event is not valid: it is missing the localized item price");
                return false;
            }
        }
        if (getTransactionId() != null) {
            return true;
        }
        log.warn("Google Monetization event is not valid: it is missing the transaction id");
        return false;
    }
}
