package com.example.searchservice.feign.error;


import com.example.searchservice.domain.exception.exception.NotAvailableException;
import com.example.searchservice.feign.model.error.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {

    Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;

        try (InputStream body = response.body().asInputStream()){
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url());

        } catch (IOException exception) {
            return new Exception(exception.getMessage());
        }

        switch (response.status()) {
            case 404:
                log.error(message.message());
                throw new NotAvailableException(message.message());
            default:
                return errorDecoder.decode(methodKey, response);
        }

    }
}

