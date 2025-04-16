package com.example.searchservice.feign;

import com.example.searchservice.config.FeignClientConfig;
import com.example.searchservice.domain.exception.exception.NotAvailableException;
import com.example.searchservice.domain.exception.exception.ServiceUnavailableException;
import com.example.searchservice.domain.response.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @GetMapping(path = "/api/v1/users/string")
    @CircuitBreaker(name = "getUserStringCircuitBreaker", fallbackMethod = "getUserStringFallBack")
    Response<String> getUserString();

    default Response<String> getUserStringFallBack(Exception exception) {
        if(exception.getClass().equals(NotAvailableException.class)){
            throw new NotAvailableException("User not found.");
        } else if (exception.getClass().equals(feign.RetryableException.class)) {
            throw new ServiceUnavailableException(exception.getMessage());
        }
        return new Response<>(null,null,"");
    }

}
