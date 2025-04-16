package com.example.searchservice.tracing;

import com.example.global.enums.MDCConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestTracingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestId = extractOrGenerateRequestId(httpRequest);
        String transactionId = UUID.randomUUID().toString();
        String parentTransactionId = httpRequest.getHeader("X-Parent-Transaction-ID");

        MDC.put(MDCConstants.REQUEST_ID.key(), requestId);
        MDC.put(MDCConstants.TRANSACTION_ID.key(), transactionId);
        MDC.put(MDCConstants.PARENT_TRANSACTION_ID.key(), parentTransactionId);

        chain.doFilter(request, response);

        MDC.clear();
    }

    private String extractOrGenerateRequestId(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-ID");
        return (requestId != null && !requestId.isEmpty()) ? requestId : UUID.randomUUID().toString();
    }

}