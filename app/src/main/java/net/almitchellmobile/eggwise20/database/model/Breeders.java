package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_BREEDERS)
public class Breeders implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long breederID;

    @ColumnInfo(name = "BirdsName")
    public String birdsName;

    @ColumnInfo(name = "SpeciesID")
    public Long speciesID;

    @ColumnInfo(name = "Gender")
    public String gender;

    @ColumnInfo(name = "AcquiredFrom")
    public String acquiredFrom;

    @ColumnInfo(name = "HatchDate")
    public java.util.Date hatchDate;

    @ColumnInfo(name = "CaptiveBreed")
    public Long captiveBreed;

    @ColumnInfo(name = "BuildingLocation")
    public String buildingLocation;

    @ColumnInfo(name = "PenLocation")
    public String penLocation;

    @ColumnInfo(name = "NaturalMate")
    public String naturalMate;

    @ColumnInfo(name = "RearedBy")
    public String rearedBy;

    @ColumnInfo(name = "LeftLegBand")
    public String leftLegBand;

    @ColumnInfo(name = "RightLegBand")
    public String rightLegBand;

    @ColumnInfo(name = "ISISNumber")
    public String isisNumber;

    @ColumnInfo(name = "StudBookNumber")
    public String studBookNumber;

    @ColumnInfo(name = "MicroChipNumber")
    public String microChipNumber;

    @ColumnInfo(name = "ReaderType")
    public String readerType;

    @ColumnInfo(name = "UserDefined1")
    public String userDefined1;

    @ColumnInfo(name = "UserDefined2")
    public String userDefined2;

    @ColumnInfo(name = "AquiredOn")
    public java.util.Date aquiredOn;

    @ColumnInfo(name = "PublishedOn")
    public java.util.Date publishedOn;

    @ColumnInfo(name = "CommonName")
    public String commonName;


    public Breeders(String birdsName, Long speciesID, String gender,
                    String acquiredFrom, java.util.Date hatchDate, Long captiveBreed,
                    String buildingLocation, String penLocation, String naturalMate,
                    String rearedBy, String leftLegBand, String rightLegBand, String isisNumber,
                    String studBookNumber, String microChipNumber, String readerType,
                    String userDefined1, String userDefined2, java.util.Date aquiredOn, java.util.Date publishedOn,
                    String commonName) {

        this.birdsName = birdsName;
        this.speciesID = speciesID;
        this.gender = gender;
        this.acquiredFrom = acquiredFrom;
        this.hatchDate = hatchDate;
        this.captiveBreed = captiveBreed;
        this.buildingLocation = buildingLocation;
        this.penLocation = penLocation;
        this.naturalMate = naturalMate;
        this.rearedBy = rearedBy;
        this.leftLegBand = leftLegBand;
        this.rightLegBand = rightLegBand;
        this.isisNumber = isisNumber;
        this.studBookNumber = studBookNumber;
        this.microChipNumber = microChipNumber;
        this.readerType = readerType;
        this.userDefined1 = userDefined1;
        this.userDefined2 = userDefined2;
        this.aquiredOn = aquiredOn;
        this.publishedOn = publishedOn;
        this.commonName = commonName;
    }

    @Ignore
    public Breeders(){}

    public Long getBreederID() {
        return breederID;
    }

    public void setBreederID(Long breederID) {
        this.breederID = breederID;
    }

    public String getBirdsName() {
        return birdsName;
    }

    public void setBirdsName(String birdsName) {
        this.birdsName = birdsName;
    }

    public Long getSpeciesID() {
        return speciesID;
    }

    public void setSpeciesID(Long speciesID) {
        this.speciesID = speciesID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAcquiredFrom() {
        return acquiredFrom;
    }

    public void setAcquiredFrom(String acquiredFrom) {
        this.acquiredFrom = acquiredFrom;
    }

    public java.util.Date getHatchDate() {
        return hatchDate;
    }

    public void setHatchDate(java.util.Date hatchDate) {
        this.hatchDate = hatchDate;
    }

    public Long getCaptiveBreed() {
        return captiveBreed;
    }

    public void setCaptiveBreed(Long captiveBreed) {
        this.captiveBreed = captiveBreed;
    }

    public String getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(String buildingLocation) {
        this.buildingLocation = buildingLocation;
    }

    public String getPenLocation() {
        return penLocation;
    }

    public void setPenLocation(String penLocation) {
        this.penLocation = penLocation;
    }

    public String getNaturalMate() {
        return naturalMate;
    }

    public void setNaturalMate(String naturalMate) {
        this.naturalMate = naturalMate;
    }

    public String getRearedBy() {
        return rearedBy;
    }

    public void setRearedBy(String rearedBy) {
        this.rearedBy = rearedBy;
    }

    public String getLeftLegBand() {
        return leftLegBand;
    }

    public void setLeftLegBand(String leftLegBand) {
        this.leftLegBand = leftLegBand;
    }

    public String getRightLegBand() {
        return rightLegBand;
    }

    public void setRightLegBand(String rightLegBand) {
        this.rightLegBand = rightLegBand;
    }

    public String getIsisNumber() {
        return isisNumber;
    }

    public void setIsisNumber(String isisNumber) {
        this.isisNumber = isisNumber;
    }

    public String getStudBookNumber() {
        return studBookNumber;
    }

    public void setStudBookNumber(String studBookNumber) {
        this.studBookNumber = studBookNumber;
    }

    public String getMicroChipNumber() {
        return microChipNumber;
    }

    public void setMicroChipNumber(String microChipNumber) {
        this.microChipNumber = microChipNumber;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(String userDefined1) {
        this.userDefined1 = userDefined1;
    }

    public String getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(String userDefined2) {
        this.userDefined2 = userDefined2;
    }

    public java.util.Date getAquiredOn() {
        return aquiredOn;
    }

    public void setAquiredOn(java.util.Date aquiredOn) {
        this.aquiredOn = aquiredOn;
    }

    public java.util.Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(java.util.Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
