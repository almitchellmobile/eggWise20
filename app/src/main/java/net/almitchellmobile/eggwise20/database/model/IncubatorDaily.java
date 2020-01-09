package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_INCUBATORDAILY)
public class IncubatorDaily implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long incubatorDailyID;

    @ColumnInfo(name = "IncubatorID")
    public String incubatorID;

    @ColumnInfo(name = "EggLabel")
    public String eggLabel;

    @ColumnInfo(name = "ReadingDate")
    public java.util.Date readingDate;

    @ColumnInfo(name = "Temperature")
    public Double temperature;

    @ColumnInfo(name = "Humidity")
    public Double humidity;

    @ColumnInfo(name = "ReadingTime")
    public java.util.Date readingTime;

    @ColumnInfo(name = "IncubatorDailyComment")
    public String incubatorDailyComment;

    public IncubatorDaily(String incubatorID, String eggLabel, java.util.Date readingDate,
                          Double temperature, Double humidity, java.util.Date readingTime,
                          String incubatorDailyComment) {
        this.incubatorID = incubatorID;
        this.eggLabel = eggLabel;
        this.readingDate = readingDate;
        this.temperature = temperature;
        this.humidity = humidity;
        this.readingTime = readingTime;
        this.incubatorDailyComment = incubatorDailyComment;
    }

    @Ignore
    public IncubatorDaily(){}

    public Long getIncubatorDailyID() {
        return incubatorDailyID;
    }

    public void setIncubatorDailyID(Long incubatorDailyID) {
        this.incubatorDailyID = incubatorDailyID;
    }

    public String getIncubatorID() {
        return incubatorID;
    }

    public void setIncubatorID(String incubatorID) {
        this.incubatorID = incubatorID;
    }

    public String getEggLabel() {
        return eggLabel;
    }

    public void setEggLabel(String eggLabel) {
        this.eggLabel = eggLabel;
    }

    public java.util.Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(java.util.Date readingDate) {
        this.readingDate = readingDate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public java.util.Date getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(java.util.Date readingTime) {
        this.readingTime = readingTime;
    }

    public String getIncubatorDailyComment() {
        return incubatorDailyComment;
    }

    public void setIncubatorDailyComment(String incubatorDailyComment) {
        this.incubatorDailyComment = incubatorDailyComment;
    }
}
