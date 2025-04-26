package com.analia.web.rs.filesystem;


import jakarta.ws.rs.FormParam;

public class FileUploadForm {
    private byte[] filedata;

    public FileUploadForm() {
    }

    public byte[] getFileData() {
        return filedata;
    }

    @FormParam("filedata")
    public void setFileData(final byte[] filedata) {
        this.filedata = filedata;
    }
}