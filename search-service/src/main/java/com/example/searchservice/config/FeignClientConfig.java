package com.example.searchservice.config;

import com.example.global.enums.MDCConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String transactionId = MDC.get(MDCConstants.TRANSACTION_ID.key());
        String requestId = MDC.get(MDCConstants.REQUEST_ID.key());

        if (transactionId != null) {
            template.header("X-Transaction-ID", transactionId);
        }
        if (requestId != null) {
            template.header("X-Request-ID", requestId);
        }
    }
}