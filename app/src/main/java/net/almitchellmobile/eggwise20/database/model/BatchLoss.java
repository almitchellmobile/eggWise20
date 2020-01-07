package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_BATCHLOSS)
public class BatchLoss implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long batchLossID;

    @ColumnInfo(name = "SettingID")
    public Long settingID;

    @ColumnInfo(name = "BatchLabel")
    public String batchLabel;

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName;

    @ColumnInfo(name = "EggLossCount")
    public Integer eggLossCount;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

    @ColumnInfo(name = "BatchlossComment")
    public String batchlossComment;

    @ColumnInfo(name = "ReadingDate")
    public Date readingDate;

    @ColumnInfo(name = "ReadingTime")
    public Timestamp readingTime;

    public BatchLoss(Long settingID, String batchLabel, String incubatorName, Integer eggLossCount, Integer numberOfEggs, String batchlossComment, Date readingDate, Timestamp readingTime) {
        this.settingID = settingID;
        this.batchLabel = batchLabel;
        this.incubatorName = incubatorName;
        this.eggLossCount = eggLossCount;
        this.numberOfEggs = numberOfEggs;
        this.batchlossComment = batchlossComment;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
    }

    @Ignore
    public BatchLoss(){};

    public Long getBatchLossID() {
        return this.batchLossID;
    }

    public void setBatchLossID(Long batchLossID) {
        this.batchLossID = batchLossID;
    }

    public Long getSettingID() {
        return this.settingID;
    }

    public void setSettingID(Long settingID) {
        this.settingID = settingID;
    }

    public String getBatchLabel() {
        return this.batchLabel;
    }

    public void setBatchLabel(String batchLabel) {
        this.batchLabel = batchLabel;
    }

    public String getIncubatorName() {
        return this.incubatorName;
    }

    public void setIncubatorName(String incubatorName) {
        this.incubatorName = incubatorName;
    }

    public Integer getEggLossCount() {
        return this.eggLossCount;
    }

    public void setEggLossCount(Integer eggLossCount) {
        this.eggLossCount = eggLossCount;
    }

    public Integer getNumberOfEggs() {
        return this.numberOfEggs;
    }

    public void setNumberOfEggs(Integer numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }

    public String getBatchlossComment() {
        return batchlossComment;
    }

    public void setBatchlossComment(String batchlossComment) {
        this.batchlossComment = batchlossComment;
    }

    public Date getReadingDate() {
        return this.readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

    public Timestamp getReadingTime() {
        return this.readingTime;
    }

    public void setReadingTime(Timestamp readingTime) {
        this.readingTime = readingTime;
    }
}
