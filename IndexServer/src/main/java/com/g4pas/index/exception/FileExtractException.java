package com.g4pas.index.exception;

/**
 * Exception throw when the system encounters an issue extracting files
 */
public class FileExtractException extends EnrichServiceException {

    public FileExtractException() {
    }

    public FileExtractException(final String message) {
        super(message);
    }

    public FileExtractException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileExtractException(final Throwable cause) {
        super(cause);
    }

    public FileExtractException(final String message, final Throwable cause, final boolean enableSuppression,
                                final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
