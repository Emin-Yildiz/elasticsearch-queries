package com.example.kotlinpojo.tracing;

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
        String requestId = extractOrGenerateRequestId( (HttpServletRequest) request);
        String parentTransactionId = ((HttpServletRequest) request).getHeader("X-Transaction-ID");
        String transactionId = UUID.randomUUID().toString();

        MDC.put(MDCConstants.REQUEST_ID.key(), requestId);
        MDC.put(MDCConstants.TRANSACTION_ID.key(), transactionId);
        MDC.put(MDCConstants.PARENT_TRANSACTION_ID.key(), parentTransactionId);

        chain.doFilter(request, response);

        MDC.clear();
    }

    public String extractOrGenerateRequestId(HttpServletRequest request) {
        String requestId = request.getHeader("X-Request-ID");

        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString();
        }

        return requestId;
    }

}
