package com.example.searchservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)  {
        String ipAddress = getClientIp(request);
        logger.info("[Pre Handle] Request IP: {} | URI: {} | Method: {}",
                ipAddress,
                request.getRequestURI(),
                request.getMethod());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model){
        String ipAddress = getClientIp(request);
        logger.info("[Post Handle] Request IP: {} | URI: {} | Method: {} | Status: {}",
                ipAddress,
                request.getRequestURI(),
                request.getMethod(),
                response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception){
        String ipAddress = getClientIp(request);
        String message = response.getStatus() >= 400 ? "Request is failed" : "Request is complete";;

        if (exception != null) {
            String errorMessage = exception.getMessage() != null ? exception.getMessage() : "Unknown error";
            logger.info("[After Completion] Request IP: {} | URI: {} | Method: {} | Status: {} | Error Message: {}",
                    ipAddress,
                    request.getRequestURI(),
                    request.getMethod(),
                    response.getStatus(),
                    errorMessage);
        }else{
            logger.info("[After Completion] Request IP: {} | URI: {} | Method: {} | Status: {} | Message: {}",
                    ipAddress,
                    request.getRequestURI(),
                    request.getMethod(),
                    response.getStatus(),
                    message);
        }

    }

    public String getClientIp(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (header != null && !header.isEmpty()) {
            return header.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}