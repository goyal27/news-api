package com.sapient.news.exception;

import lombok.Getter;
import lombok.NonNull;

import static java.lang.String.format;
@Getter
public enum ErrorCode {
    BAD_REQUEST_ERROR("EC1", "Bad Request: %s"),
    INTERNAL_SERVER_ERROR_CODE("EC4", "Internal Server: %s"),
    ERROR_INVALID_INTERVAL("EC2", "Invalid Interval Data %s "),
    ERROR_NEWS_CLIENT("EC3", "Error while fetching news %s "),
    ERROR_REQUEST_LIMIT_REACHED("EC5", "Request limit reached %s ")
    ;
    private final String code;
    private final String metaDescription;

    ErrorCode(@NonNull String code, @NonNull String metaDescription) {
        this.code = code;
        this.metaDescription = metaDescription;
    }

    public String formatMessage(@NonNull String message) {
        return code.concat(": ").concat(format(metaDescription, message)).trim();
    }
}
