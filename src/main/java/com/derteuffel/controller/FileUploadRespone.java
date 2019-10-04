package com.derteuffel.controller;

/**
 * Created by derteuffel on 10/10/2018.
 */
public class FileUploadRespone {
    private String fileName;
    private String fileDownloadUri;

    public FileUploadRespone() {

    }

    public FileUploadRespone(String fileName, String fileDownloadUri) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }
}
