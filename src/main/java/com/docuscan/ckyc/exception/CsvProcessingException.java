package com.docuscan.ckyc.exception;

public class CsvProcessingException extends Exception {
    public CsvProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvProcessingException(String message) {
        super(message);
    }
}
