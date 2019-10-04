package com.derteuffel.controller;

/**
 * Created by derteuffel on 10/10/2018.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
