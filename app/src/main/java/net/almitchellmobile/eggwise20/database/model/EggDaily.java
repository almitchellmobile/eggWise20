package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.TimestampConverter;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = Constants.TABLE_NAME_EGG_DAILY)
public class EggDaily implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long eggDailyID;

    @ColumnInfo(name = "settingID")
    public Long settingID;

    @ColumnInfo(name = "BatchLabel")
    public String batchLabel = "";

    @ColumnInfo(name = "EggLabel")
    public String eggLabel = "";

    @ColumnInfo(name = "ReadingDate")
    @TypeConverters({TimestampConverter.class})
    public String readingDate = "";

    @ColumnInfo(name = "SetDate")
    @TypeConverters({TimestampConverter.class})
    public String setDate = "";

    @ColumnInfo(name = "ReadingDayNumber")
    public Integer readingDayNumber = 0;

    @ColumnInfo(name = "EggWeight")
    public Double eggWeight = 0.0D;

    @ColumnInfo(name = "EggWeightAverage")
    public Double eggWeightAverage = 0.0D;

    @ColumnInfo(name = "EggDailyComment")
    public String eggDailyComment = "";

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName = "";

    @ColumnInfo(name = "NumberOfEggsRemaining")
    public Integer numberOfEggsRemaining = 0;

    @ColumnInfo(name = "EggWeightSum")
    public Double eggWeightSum = 0.0D;

    public EggDaily(Long settingID, String batchLabel, String eggLabel, String readingDate, String setDate, Integer readingDayNumber, Double eggWeight, Double eggWeightAverage, String eggDailyComment, String incubatorName, Integer numberOfEggsRemaining, Double eggWeightSum) {
        this.settingID = settingID;
        this.batchLabel = batchLabel;
        this.eggLabel = eggLabel;
        this.readingDate = readingDate;
        this.setDate = setDate;
        this.readingDayNumber = readingDayNumber;
        this.eggWeight = eggWeight;
        this.eggWeightAverage = eggWeightAverage;
        this.eggDailyComment = eggDailyComment;
        this.incubatorName = incubatorName;
        this.numberOfEggsRemaining = numberOfEggsRemaining;
        this.eggWeightSum = eggWeightSum;
    }

    @Ignore
    public EggDaily(){}

    public Long getEggDailyID() {
        return eggDailyID;
    }

    public void setEggDailyID(Long eggDailyID) {
        this.eggDailyID = eggDailyID;
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

    public String getEggLabel() {
        return eggLabel;
    }

    public void setEggLabel(String eggLabel) {
        this.eggLabel = eggLabel;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public Integer getReadingDayNumber() {
        return readingDayNumber;
    }

    public void setReadingDayNumber(Integer readingDayNumber) {
        this.readingDayNumber = readingDayNumber;
    }

    public Double getEggWeight() {
        return eggWeight;
    }

    public void setEggWeight(Double eggWeight) {
        this.eggWeight = eggWeight;
    }

    public Double getEggWeightAverage() {
        return eggWeightAverage;
    }

    public void setEggWeightAverage(Double eggWeightAverage) {
        this.eggWeightAverage = eggWeightAverage;
    }

    public String getEggDailyComment() {
        return eggDailyComment;
    }

    public void setEggDailyComment(String eggDailyComment) {
        this.eggDailyComment = eggDailyComment;
    }

    public String getIncubatorName() {
        return incubatorName;
    }

    public void setIncubatorName(String incubatorName) {
        this.incubatorName = incubatorName;
    }

    public Integer getNumberOfEggsRemaining() {
        return numberOfEggsRemaining;
    }

    public void setNumberOfEggsRemaining(Integer numberOfEggsRemaining) {
        this.numberOfEggsRemaining = numberOfEggsRemaining;
    }

    public Double getEggWeightSum() {
        return eggWeightSum;
    }

    public void setEggWeightSum(Double eggWeightSum) {
        this.eggWeightSum = eggWeightSum;
    }
}
