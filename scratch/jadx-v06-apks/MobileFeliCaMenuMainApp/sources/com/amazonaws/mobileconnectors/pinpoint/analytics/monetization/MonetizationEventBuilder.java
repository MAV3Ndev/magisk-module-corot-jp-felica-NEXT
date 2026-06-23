package com.amazonaws.mobileconnectors.pinpoint.analytics.monetization;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsClient;
import com.amazonaws.mobileconnectors.pinpoint.analytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public abstract class MonetizationEventBuilder {
    static final String AMAZON_STORE = "Amazon";
    static final String GOOGLE_PLAY_STORE = "Google Play";
    static final String PURCHASE_EVENT_CURRENCY_ATTR = "_currency";
    static final String PURCHASE_EVENT_ITEM_PRICE_METRIC = "_item_price";
    static final String PURCHASE_EVENT_LOCALE_ATTR = "_locale";
    static final String PURCHASE_EVENT_NAME = "_monetization.purchase";
    static final String PURCHASE_EVENT_PRICE_FORMATTED_ATTR = "_item_price_formatted";
    static final String PURCHASE_EVENT_PRODUCT_ID_ATTR = "_product_id";
    static final String PURCHASE_EVENT_QUANTITY_METRIC = "_quantity";
    static final String PURCHASE_EVENT_STORE_ATTR = "_store";
    static final String PURCHASE_EVENT_TRANSACTION_ID_ATTR = "_transaction_id";
    static final String VIRTUAL_STORE = "Virtual";
    private static final Log log = LogFactory.getLog((Class<?>) MonetizationEventBuilder.class);
    private final AnalyticsClient analyticsClient;
    private String currency;
    private String formattedItemPrice;
    private Double itemPrice;
    private String productId;
    private Double quantity;
    private String store;
    private String transactionId;

    protected abstract boolean isValid();

    protected MonetizationEventBuilder(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public AnalyticsEvent build() {
        if (!isValid() || !doBaseValidation()) {
            return null;
        }
        AnalyticsEvent analyticsEventCreateEvent = this.analyticsClient.createEvent(PURCHASE_EVENT_NAME);
        analyticsEventCreateEvent.addAttribute(PURCHASE_EVENT_PRODUCT_ID_ATTR, this.productId);
        analyticsEventCreateEvent.addAttribute(PURCHASE_EVENT_STORE_ATTR, this.store);
        analyticsEventCreateEvent.addMetric(PURCHASE_EVENT_QUANTITY_METRIC, this.quantity);
        String str = this.formattedItemPrice;
        if (str != null) {
            analyticsEventCreateEvent.addAttribute(PURCHASE_EVENT_PRICE_FORMATTED_ATTR, str);
        }
        Double d = this.itemPrice;
        if (d != null) {
            analyticsEventCreateEvent.addMetric(PURCHASE_EVENT_ITEM_PRICE_METRIC, d);
        }
        String str2 = this.transactionId;
        if (str2 != null) {
            analyticsEventCreateEvent.addAttribute(PURCHASE_EVENT_TRANSACTION_ID_ATTR, str2);
        }
        String str3 = this.currency;
        if (str3 == null) {
            return analyticsEventCreateEvent;
        }
        analyticsEventCreateEvent.addAttribute(PURCHASE_EVENT_CURRENCY_ATTR, str3);
        return analyticsEventCreateEvent;
    }

    protected String getProductId() {
        return this.productId;
    }

    protected void setProductId(String str) {
        this.productId = str;
    }

    protected Double getQuantity() {
        return this.quantity;
    }

    protected void setQuantity(Double d) {
        this.quantity = d;
    }

    protected Double getItemPrice() {
        return this.itemPrice;
    }

    protected void setItemPrice(Double d) {
        this.itemPrice = d;
    }

    @Deprecated
    protected String getFormattedItemPrice() {
        return this.formattedItemPrice;
    }

    @Deprecated
    protected void setFormattedItemPrice(String str) {
        this.formattedItemPrice = str;
    }

    protected String getCurrency() {
        return this.currency;
    }

    protected void setCurrency(String str) {
        this.currency = str;
    }

    protected String getStore() {
        return this.store;
    }

    protected void setStore(String str) {
        this.store = str;
    }

    protected String getTransactionId() {
        return this.transactionId;
    }

    protected void setTransactionId(String str) {
        this.transactionId = str;
    }

    private boolean doBaseValidation() {
        if (this.analyticsClient == null) {
            log.warn("Cannot build Monetization event: the analyticsClient is null");
            return false;
        }
        if (StringUtil.isNullOrEmpty(this.productId)) {
            log.warn("Base Monetization event is not valid: it is missing the product id");
            return false;
        }
        if (this.quantity == null) {
            log.warn("Base Monetization event is not valid: it is missing the quantity");
            return false;
        }
        if (StringUtil.isNullOrEmpty(this.store)) {
            log.warn("Base Monetization event is not valid: it is missing the store");
            return false;
        }
        if ((!StringUtil.isNullOrEmpty(this.currency) && this.itemPrice != null) || !StringUtil.isNullOrEmpty(this.formattedItemPrice)) {
            return true;
        }
        log.warn("Base Monetization event is not valid: it requires the currency and price");
        return false;
    }
}
