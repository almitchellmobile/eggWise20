package net.almitchellmobile.eggwise20.database.model;


import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.TimestampConverter;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = Constants.TABLE_NAME_EGG_BATCH)
public class EggBatch implements Serializable  {

    @PrimaryKey(autoGenerate = true)
    public Long eggBatchID;

    @ColumnInfo(name = "BatchLabel")
    public String batchLabel;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

    @ColumnInfo(name = "SpeciesID")
    public Long speciesID;

    @ColumnInfo(name = "SpeciesName")
    public String speciesName;

    @ColumnInfo(name = "CommonName")
    public String commonName;

    @ColumnInfo(name = "LayDate")
    @TypeConverters({TimestampConverter.class})
    public String layDate;

    @ColumnInfo(name = "SetDate")
    @TypeConverters({TimestampConverter.class})
    public String setDate;

    @ColumnInfo(name = "HatchDate")
    @TypeConverters({TimestampConverter.class})
    public String hatchDate;

    @ColumnInfo(name = "IncubatorID")
    public Long incubatorID;

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName;

    @ColumnInfo(name = "Location")
    public String location;

    @ColumnInfo(name = "TrackingOption")
    public Integer trackingOption;

    @ColumnInfo(name = "DesiredWeightLoss")
    public Double desiredWeightLoss;

    public EggBatch(String batchLabel, Integer numberOfEggs, Long speciesID, String speciesName, String commonName, String layDate, String setDate, String hatchDate, Long incubatorID, String incubatorName, String location, Integer trackingOption, Double desiredWeightLoss) {
        this.batchLabel = batchLabel;
        this.numberOfEggs = numberOfEggs;
        this.speciesID = speciesID;
        this.speciesName = speciesName;
        this.commonName = commonName;
        this.layDate = layDate;
        this.setDate = setDate;
        this.hatchDate = hatchDate;
        this.incubatorID = incubatorID;
        this.incubatorName = incubatorName;
        this.location = location;
        this.trackingOption = trackingOption;
        this.desiredWeightLoss = desiredWeightLoss;
    }


    @Ignore
    public EggBatch(){}

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

    public Integer getNumberOfEggs() {
        return numberOfEggs;
    }

    public void setNumberOfEggs(Integer numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }

    public Long getSpeciesID() {
        return speciesID;
    }

    public void setSpeciesID(Long speciesID) {
        this.speciesID = speciesID;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLayDate() {
        return layDate;
    }

    public void setLayDate(String layDate) {
        this.layDate = layDate;
    }

    public String getSetDate() {
        return setDate;
    }

    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public String getHatchDate() {
        return hatchDate;
    }

    public void setHatchDate(String hatchDate) {
        this.hatchDate = hatchDate;
    }

    public Long getIncubatorID() {
        return incubatorID;
    }

    public void setIncubatorID(Long incubatorID) {
        this.incubatorID = incubatorID;
    }

    public String getIncubatorName() {
        return incubatorName;
    }

    public void setIncubatorName(String incubatorName) {
        this.incubatorName = incubatorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTrackingOption() {
        return trackingOption;
    }

    public void setTrackingOption(Integer trackingOption) {
        this.trackingOption = trackingOption;
    }

    public Double getDesiredWeightLoss() {
        return desiredWeightLoss;
    }

    public void setDesiredWeightLoss(Double desiredWeightLoss) {
        this.desiredWeightLoss = desiredWeightLoss;
    }
}
