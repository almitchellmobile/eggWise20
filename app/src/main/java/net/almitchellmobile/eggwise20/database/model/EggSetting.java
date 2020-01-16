package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.TimestampConverter;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = Constants.TABLE_NAME_EGG_SETTING)
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

    @ColumnInfo(name = "SpeciesName")
    public String speciesName;

    @ColumnInfo(name = "EggHeight")
    public Double eggHeight;

    @ColumnInfo(name = "EggBreadth")
    public Double eggBreadth;

    @ColumnInfo(name = "LayDate")
    @TypeConverters({TimestampConverter.class})
    public String layDate;

    @ColumnInfo(name = "SetDate")
    @TypeConverters({TimestampConverter.class})
    public String setDate;

    @ColumnInfo(name = "IncubatorID")
    public Long incubatorID;


    @ColumnInfo(name = "IncubatorName")
    public String incubatorName;


    @ColumnInfo(name = "Location")
    public String location;

    @ColumnInfo(name = "HatchDate")
    @TypeConverters({TimestampConverter.class})
    public String hatchDate;

    @ColumnInfo(name = "LossAtPIP")
    public Double lossAtPIP;

    @ColumnInfo(name = "NewestLayDate")
    @TypeConverters({TimestampConverter.class})
    public String newestLayDate;

    @ColumnInfo(name = "OldestLayDate")
    @TypeConverters({TimestampConverter.class})
    public String oldestLayDate;

    @ColumnInfo(name = "SettingType")
    public String settingType;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

    @ColumnInfo(name = "PIPDate")
    public Double pipDate;

    @ColumnInfo(name = "TrackingOption")
    public Integer trackingOption;

    @ColumnInfo(name = "ReminderHatchDate")
    @TypeConverters({TimestampConverter.class})
    public String reminderHatchDate;

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
    public String commonName;

    public EggSetting(String eggLabel, String disposition, Long motherID, String motherName, Long fatherID, String fatherName, Long speciesID, String speciesName, Double eggHeight, Double eggBreadth, String layDate, String setDate, Long incubatorID, String incubatorName, String location, String hatchDate, Double lossAtPIP, String newestLayDate, String oldestLayDate, String settingType, Integer numberOfEggs, Double pipDate, Integer trackingOption, String reminderHatchDate, Integer reminderIncubatorSetting, Integer reminderIncubatorWater, Integer reminderEggCandeling, Integer reminderEggTurning, Double desiredWeightLoss, String commonName) {
        this.eggLabel = eggLabel;
        this.disposition = disposition;
        this.motherID = motherID;
        this.motherName = motherName;
        this.fatherID = fatherID;
        this.fatherName = fatherName;
        this.speciesID = speciesID;
        this.speciesName = speciesName;
        this.eggHeight = eggHeight;
        this.eggBreadth = eggBreadth;
        this.layDate = layDate;
        this.setDate = setDate;
        this.incubatorID = incubatorID;
        this.incubatorName = incubatorName;
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

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
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

    public String getHatchDate() {
        return hatchDate;
    }

    public void setHatchDate(String hatchDate) {
        this.hatchDate = hatchDate;
    }

    public Double getLossAtPIP() {
        return lossAtPIP;
    }

    public void setLossAtPIP(Double lossAtPIP) {
        this.lossAtPIP = lossAtPIP;
    }

    public String getNewestLayDate() {
        return newestLayDate;
    }

    public void setNewestLayDate(String newestLayDate) {
        this.newestLayDate = newestLayDate;
    }

    public String getOldestLayDate() {
        return oldestLayDate;
    }

    public void setOldestLayDate(String oldestLayDate) {
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

    public String getReminderHatchDate() {
        return reminderHatchDate;
    }

    public void setReminderHatchDate(String reminderHatchDate) {
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
