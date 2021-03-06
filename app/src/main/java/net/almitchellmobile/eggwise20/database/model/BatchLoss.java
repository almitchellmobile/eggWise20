package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.TimestampConverter;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = Constants.TABLE_NAME_BATCH_LOSS)
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
    @TypeConverters({TimestampConverter.class})
    public String readingDate;

    @ColumnInfo(name = "ReadingTime")
    @TypeConverters({TimestampConverter.class})
    public String readingTime;


    public BatchLoss(Long settingID, String batchLabel, String incubatorName,
                     Integer eggLossCount, Integer numberOfEggs, String batchlossComment,
                     String readingDate, String readingTime) {
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
        return batchLossID;
    }

    public void setBatchLossID(Long batchLossID) {
        this.batchLossID = batchLossID;
    }

    public Long getSettingID() {
        return settingID;
    }

    public void setSettingID(Long settingID) {
        this.settingID = settingID;
    }

    public String getBatchLabel() {
        return batchLabel;
    }

    public void setBatchLabel(String batchLabel) {
        this.batchLabel = batchLabel;
    }

    public String getIncubatorName() {
        return incubatorName;
    }

    public void setIncubatorName(String incubatorName) {
        this.incubatorName = incubatorName;
    }

    public Integer getEggLossCount() {
        return eggLossCount;
    }

    public void setEggLossCount(Integer eggLossCount) {
        this.eggLossCount = eggLossCount;
    }

    public Integer getNumberOfEggs() {
        return numberOfEggs;
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

    public String getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }
}
