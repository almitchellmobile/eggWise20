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
    @ColumnInfo(name = "EggDailyID")
    public Long eggDailyID;

    @ColumnInfo(name = "SettingID")
    public Long settingID;

    @ColumnInfo(name = "EggBatchID")
    public Long eggBatchID;

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

    @ColumnInfo(name = "EggWeightAverageDay0")
    public Double eggWeightAverageDay0 = 0.0D;

    @ColumnInfo(name = "EggDailyComment")
    public String eggDailyComment = "";

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName = "";

    @ColumnInfo(name = "NumberOfEggsRemaining")
    public Integer numberOfEggsRemaining = 0;

    @ColumnInfo(name = "EggWeightSum")
    public Double eggWeightSum = 0.0D;

    @ColumnInfo(name = "ActualWeightLossPercent")
    public Double actualWeightLossPercent = 0.0D;

    @ColumnInfo(name = "TargetWeightLossPercent")
    public Double targetWeightLossPercent = 0.0D;

    @ColumnInfo(name = "WeightLossDeviation")
    public Double weightLossDeviation = 0.0D;

    @ColumnInfo(name = "EggWeightAverageCurrent")
    public Double eggWeightAverageCurrent = 0.0D;

    @ColumnInfo(name = "TargetWeightLossInteger")
    public Integer targetWeightLossInteger = 0;

    @ColumnInfo(name = "IncubationDays")
    public Integer incubationDays = 0;

    public EggDaily(Long settingID, Long eggBatchID, String batchLabel, String eggLabel, String readingDate, String setDate, Integer readingDayNumber, Double eggWeight, Double eggWeightAverageDay0, String eggDailyComment, String incubatorName, Integer numberOfEggsRemaining, Double eggWeightSum, Double actualWeightLossPercent, Double targetWeightLossPercent, Double weightLossDeviation, Double eggWeightAverageCurrent, Integer targetWeightLossInteger, Integer incubationDays) {
        this.settingID = settingID;
        this.eggBatchID = eggBatchID;
        this.batchLabel = batchLabel;
        this.eggLabel = eggLabel;
        this.readingDate = readingDate;
        this.setDate = setDate;
        this.readingDayNumber = readingDayNumber;
        this.eggWeight = eggWeight;
        this.eggWeightAverageDay0 = eggWeightAverageDay0;
        this.eggDailyComment = eggDailyComment;
        this.incubatorName = incubatorName;
        this.numberOfEggsRemaining = numberOfEggsRemaining;
        this.eggWeightSum = eggWeightSum;
        this.actualWeightLossPercent = actualWeightLossPercent;
        this.targetWeightLossPercent = targetWeightLossPercent;
        this.weightLossDeviation = weightLossDeviation;
        this.eggWeightAverageCurrent = eggWeightAverageCurrent;
        this.targetWeightLossInteger = targetWeightLossInteger;
        this.incubationDays = incubationDays;
    }

    @Override
    public String toString() {
        return "EggDaily{" +
                "eggDailyID=" + eggDailyID +
                ", settingID=" + settingID +
                ", eggBatchID=" + eggBatchID +
                ", batchLabel='" + batchLabel + '\'' +
                ", eggLabel='" + eggLabel + '\'' +
                ", readingDate='" + readingDate + '\'' +
                ", setDate='" + setDate + '\'' +
                ", readingDayNumber=" + readingDayNumber +
                ", eggWeight=" + eggWeight +
                ", eggWeightAverageDay0=" + eggWeightAverageDay0 +
                ", eggDailyComment='" + eggDailyComment + '\'' +
                ", incubatorName='" + incubatorName + '\'' +
                ", numberOfEggsRemaining=" + numberOfEggsRemaining +
                ", eggWeightSum=" + eggWeightSum +
                ", actualWeightLossPercent=" + actualWeightLossPercent +
                ", targetWeightLossPercent=" + targetWeightLossPercent +
                ", weightLossDeviation=" + weightLossDeviation +
                ", eggWeightAverageCurrent=" + eggWeightAverageCurrent +
                ", targetWeightLossInteger=" + targetWeightLossInteger +
                ", incubationDays=" + incubationDays +
                '}';
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

    public Long getEggBatchID() {
        return eggBatchID;
    }

    public void setEggBatchID(Long eggBatchID) {
        this.eggBatchID = eggBatchID;
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

    public Double getEggWeightAverageDay0() {
        return eggWeightAverageDay0;
    }

    public void setEggWeightAverageDay0(Double eggWeightAverageDay0) {
        this.eggWeightAverageDay0 = eggWeightAverageDay0;
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

    public Double getActualWeightLossPercent() {
        return actualWeightLossPercent;
    }

    public void setActualWeightLossPercent(Double actualWeightLossPercent) {
        this.actualWeightLossPercent = actualWeightLossPercent;
    }

    public Double getTargetWeightLossPercent() {
        return targetWeightLossPercent;
    }

    public void setTargetWeightLossPercent(Double targetWeightLossPercent) {
        this.targetWeightLossPercent = targetWeightLossPercent;
    }

    public Double getWeightLossDeviation() {
        return weightLossDeviation;
    }

    public void setWeightLossDeviation(Double weightLossDeviation) {
        this.weightLossDeviation = weightLossDeviation;
    }

    public Double getEggWeightAverageCurrent() {
        return eggWeightAverageCurrent;
    }

    public void setEggWeightAverageCurrent(Double eggWeightAverageCurrent) {
        this.eggWeightAverageCurrent = eggWeightAverageCurrent;
    }

    public Integer getTargetWeightLossInteger() {
        return targetWeightLossInteger;
    }

    public void setTargetWeightLossInteger(Integer targetWeightLossInteger) {
        this.targetWeightLossInteger = targetWeightLossInteger;
    }

    public Integer getIncubationDays() {
        return incubationDays;
    }

    public void setIncubationDays(Integer incubationDays) {
        this.incubationDays = incubationDays;
    }
}
