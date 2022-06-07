/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nghia
 */
public class DatabaseFile {

    private String fileType;
    private String logicalName;
    private String originalFileName;
    private String restoreAs;

    public DatabaseFile() {
    }

    public DatabaseFile(String fileType, String logicalName, String originalFileName, String restoreAs) {
        this.fileType = fileType;
        this.logicalName = logicalName;
        this.originalFileName = originalFileName;
        this.restoreAs = restoreAs;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getLogicalName() {
        return logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getRestoreAs() {
        return restoreAs;
    }

    public void setRestoreAs(String restoreAs) {
        this.restoreAs = restoreAs;
    }
}
