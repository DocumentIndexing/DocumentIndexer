package com.g4pas.index.exception;

/**
 * Generic Exception thrown within The Enriching Flow
 */
public class TransformServiceException extends Exception {
    public TransformServiceException() {
    }

    public TransformServiceException(final String message) {
        super(message);
    }

    public TransformServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransformServiceException(final Throwable cause) {
        super(cause);
    }

    public TransformServiceException(final String message, final Throwable cause, final boolean enableSuppression,
                                     final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
