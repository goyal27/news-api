package com.sapient.news.util;

public class Constant {
    private Constant(){}

    // region SLF4J
    public static final String LOGGER_IMPL = "net.spy.log.LoggerImpl";
    public static final String LOG4J_LOGGER = "net.spy.memcached.compat.log.Log4JLogger";
    // end region

    // region environment
    public static final String UTC_TIME_FORMAT = "UTC";
    // end region

    //region project
    public static final String PROJECT_CODE = "SNA";
    public static final String TRACE_ID_HEADER = "traceId";
    public static final int REQUEST_MAX_PAY_LOAD_LENGTH = 10000;
    public static final String REQUEST_DATA = "REQUEST DATA : ";
    //end region

    //region endpoints
    public static final String NEWS_API = "/v1/news";
    //end region

    //region error
    public static final String INVALID_INTERVAL =
            "Both interval and unit be either null or non-null together";
    public static final String RECEIVED_A_BAD_REQUEST_MESSAGE = "Received a bad request";
    public static final String EXCEPTION_ERROR_MESSAGE_FORMAT = "%s.%s.%s";
    public static final String NEWS_CLIENT_EXCEPTION = "Exception while calling news api for request %s ";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error %s";
    public static final String RATE_LIMIT_REACHED =
            "You can only make %s requests per second";
    //end region
}
