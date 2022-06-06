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
public class BackupSet {
    private boolean restore;
    private String type;
    private String physicalDeviceName;
    private String databaseName;
    private int position;
    private String startDate;
    private String finishDate;

    public BackupSet() {
    }

    public BackupSet(boolean restore, String type, String physicalDeviceName, String databaseName, int position, String startDate, String finishDate) {
        this.restore = restore;
        this.type = type;
        this.physicalDeviceName = physicalDeviceName;
        this.databaseName = databaseName;
        this.position = position;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public boolean isRestore() {
        return restore;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhysicalDeviceName() {
        return physicalDeviceName;
    }

    public void setPhysicalDeviceName(String physicalDeviceName) {
        this.physicalDeviceName = physicalDeviceName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}