package com.derteuffel.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by derteuffel on 10/10/2018.
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
