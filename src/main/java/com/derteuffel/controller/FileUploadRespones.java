package com.derteuffel.controller;

/**
 * Created by derteuffel on 10/10/2018.
 */
public class FileUploadRespones {
    private String fileName;

    public FileUploadRespones() {

    }

    public FileUploadRespones(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
