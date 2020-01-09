package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_EGGSETTING)
public class EggSetting implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long eggSettingID;

    @ColumnInfo(name = "EggLabel")
    public String eggLabel;

    @ColumnInfo(name = "Disposition")
    public String disposition;

    @ColumnInfo(name = "MotherID")
    public Long motherID;

    @ColumnInfo(name = "MotherName")
    public String motherName;

    @ColumnInfo(name = "FatherID")
    public Long fatherID;

    @ColumnInfo(name = "FatherName")
    public String fatherName;

    @ColumnInfo(name = "SpeciesID")
    public Long speciesID;

    @ColumnInfo(name = "EggHeight")
    public Double eggHeight;

    @ColumnInfo(name = "EggBreadth")
    public Double eggBreadth;

    @ColumnInfo(name = "LayDate")
    public java.util.Date layDate;

    @ColumnInfo(name = "SetDate")
    public java.util.Date setDate;

    @ColumnInfo(name = "IncubatorID")
    public Long incubatorID;

    @ColumnInfo(name = "Location")
    public String location;

    @ColumnInfo(name = "HatchDate")
    public java.util.Date hatchDate;

    @ColumnInfo(name = "LossAtPIP")
    public Double lossAtPIP;

    @ColumnInfo(name = "NewestLayDate")
    public java.util.Date newestLayDate;

    @ColumnInfo(name = "OldestLayDate")
    public java.util.Date oldestLayDate;

    @ColumnInfo(name = "SettingType")
    public String settingType;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

    @ColumnInfo(name = "PIPDate")
    public Double pipDate;

    @ColumnInfo(name = "TrackingOption")
    public Integer trackingOption;

    @ColumnInfo(name = "ReminderHatchDate")
    public java.util.Date reminderHatchDate;

    @ColumnInfo(name = "ReminderIncubatorSetting")
    public Integer reminderIncubatorSetting;

    @ColumnInfo(name = "ReminderIncubatorWater")
    public Integer reminderIncubatorWater;

    @ColumnInfo(name = "ReminderEggCandeling")
    public Integer reminderEggCandeling;

    @ColumnInfo(name = "ReminderEggTurning")
    public Integer reminderEggTurning;

    @ColumnInfo(name = "DesiredWeightLoss")
    public Double desiredWeightLoss;

    @ColumnInfo(name = "CommonName")
    public Integer commonName;

    public EggSetting(String eggLabel, String disposition, Long motherID, String motherName, Long fatherID, String fatherName, Long speciesID, Double eggHeight, Double eggBreadth, Date layDate, Date setDate, Long incubatorID, String location, Date hatchDate, Double lossAtPIP, Date newestLayDate, Date oldestLayDate, String settingType, Integer numberOfEggs, Double pipDate, Integer trackingOption, Date reminderHatchDate, Integer reminderIncubatorSetting, Integer reminderIncubatorWater, Integer reminderEggCandeling, Integer reminderEggTurning, Double desiredWeightLoss, Integer commonName) {
        this.eggLabel = eggLabel;
        this.disposition = disposition;
        this.motherID = motherID;
        this.motherName = motherName;
        this.fatherID = fatherID;
        this.fatherName = fatherName;
        this.speciesID = speciesID;
        this.eggHeight = eggHeight;
        this.eggBreadth = eggBreadth;
        this.layDate = layDate;
        this.setDate = setDate;
        this.incubatorID = incubatorID;
        this.location = location;
        this.hatchDate = hatchDate;
        this.lossAtPIP = lossAtPIP;
        this.newestLayDate = newestLayDate;
        this.oldestLayDate = oldestLayDate;
        this.settingType = settingType;
        this.numberOfEggs = numberOfEggs;
        this.pipDate = pipDate;
        this.trackingOption = trackingOption;
        this.reminderHatchDate = reminderHatchDate;
        this.reminderIncubatorSetting = reminderIncubatorSetting;
        this.reminderIncubatorWater = reminderIncubatorWater;
        this.reminderEggCandeling = reminderEggCandeling;
        this.reminderEggTurning = reminderEggTurning;
        this.desiredWeightLoss = desiredWeightLoss;
        this.commonName = commonName;
    }

    @Ignore
    public EggSetting(){}

    public Long getEggSettingID() {
        return eggSettingID;
    }

    public void setEggSettingID(Long eggSettingID) {
        this.eggSettingID = eggSettingID;
    }

    public String getEggLabel() {
        return eggLabel;
    }

    public void setEggLabel(String eggLabel) {
        this.eggLabel = eggLabel;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public Long getMotherID() {
        return motherID;
    }

    public void setMotherID(Long motherID) {
        this.motherID = motherID;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Long getFatherID() {
        return fatherID;
    }

    public void setFatherID(Long fatherID) {
        this.fatherID = fatherID;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Long getSpeciesID() {
        return speciesID;
    }

    public void setSpeciesID(Long speciesID) {
        this.speciesID = speciesID;
    }

    public Double getEggHeight() {
        return eggHeight;
    }

    public void setEggHeight(Double eggHeight) {
        this.eggHeight = eggHeight;
    }

    public Double getEggBreadth() {
        return eggBreadth;
    }

    public void setEggBreadth(Double eggBreadth) {
        this.eggBreadth = eggBreadth;
    }

    public Date getLayDate() {
        return layDate;
    }

    public void setLayDate(Date layDate) {
        this.layDate = layDate;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public Long getIncubatorID() {
        return incubatorID;
    }

    public void setIncubatorID(Long incubatorID) {
        this.incubatorID = incubatorID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getHatchDate() {
        return hatchDate;
    }

    public void setHatchDate(Date hatchDate) {
        this.hatchDate = hatchDate;
    }

    public Double getLossAtPIP() {
        return lossAtPIP;
    }

    public void setLossAtPIP(Double lossAtPIP) {
        this.lossAtPIP = lossAtPIP;
    }

    public Date getNewestLayDate() {
        return newestLayDate;
    }

    public void setNewestLayDate(Date newestLayDate) {
        this.newestLayDate = newestLayDate;
    }

    public Date getOldestLayDate() {
        return oldestLayDate;
    }

    public void setOldestLayDate(Date oldestLayDate) {
        this.oldestLayDate = oldestLayDate;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public Integer getNumberOfEggs() {
        return numberOfEggs;
    }

    public void setNumberOfEggs(Integer numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }

    public Double getPipDate() {
        return pipDate;
    }

    public void setPipDate(Double pipDate) {
        this.pipDate = pipDate;
    }

    public Integer getTrackingOption() {
        return trackingOption;
    }

    public void setTrackingOption(Integer trackingOption) {
        this.trackingOption = trackingOption;
    }

    public Date getReminderHatchDate() {
        return reminderHatchDate;
    }

    public void setReminderHatchDate(Date reminderHatchDate) {
        this.reminderHatchDate = reminderHatchDate;
    }

    public Integer getReminderIncubatorSetting() {
        return reminderIncubatorSetting;
    }

    public void setReminderIncubatorSetting(Integer reminderIncubatorSetting) {
        this.reminderIncubatorSetting = reminderIncubatorSetting;
    }

    public Integer getReminderIncubatorWater() {
        return reminderIncubatorWater;
    }

    public void setReminderIncubatorWater(Integer reminderIncubatorWater) {
        this.reminderIncubatorWater = reminderIncubatorWater;
    }

    public Integer getReminderEggCandeling() {
        return reminderEggCandeling;
    }

    public void setReminderEggCandeling(Integer reminderEggCandeling) {
        this.reminderEggCandeling = reminderEggCandeling;
    }

    public Integer getReminderEggTurning() {
        return reminderEggTurning;
    }

    public void setReminderEggTurning(Integer reminderEggTurning) {
        this.reminderEggTurning = reminderEggTurning;
    }

    public Double getDesiredWeightLoss() {
        return desiredWeightLoss;
    }

    public void setDesiredWeightLoss(Double desiredWeightLoss) {
        this.desiredWeightLoss = desiredWeightLoss;
    }

    public Integer getCommonName() {
        return commonName;
    }

    public void setCommonName(Integer commonName) {
        this.commonName = commonName;
    }
}
