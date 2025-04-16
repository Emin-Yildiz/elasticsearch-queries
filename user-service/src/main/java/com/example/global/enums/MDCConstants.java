package com.example.global.enums;

//public class MDCConstants {
//    public static final String REQUEST_ID = "requestId";
//    public static final String TRANSACTION_ID = "transactionId";
//    public static final String PARENT_TRANSACTION_ID = "parentTransactionId";
//}

public enum MDCConstants {
    REQUEST_ID("requestId"),
    TRANSACTION_ID("transactionId"),
    PARENT_TRANSACTION_ID("parentTransactionId");

    private final String key;

    MDCConstants(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }


}