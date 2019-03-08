package com.pikchillytechnologies.engineeingacademy.Model;

public class DownloadedFileModel {

    private String m_DownloadedFileName;

    public DownloadedFileModel(String downloadedFile){
        this.m_DownloadedFileName = downloadedFile;
    }

    public String getM_DownloadedFileName() {
        return m_DownloadedFileName;
    }

    public void setM_DownloadedFileName(String m_DownloadedFileName) {
        this.m_DownloadedFileName = m_DownloadedFileName;
    }
}
