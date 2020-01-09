package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_TAXON)
public class Taxon implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long speciesID;

    @ColumnInfo(name = "SpeciesName")
    public String speciesName;

    @ColumnInfo(name = "UserName")
    public String userName;

    @ColumnInfo(name = "CommonName")
    public String commonName;

    @ColumnInfo(name = "IncubationDays")
    public String incubationDays;

    @ColumnInfo(name = "EggSize")
    public Double eggSize;

    @ColumnInfo(name = "PublishedOn")
    public java.util.Date publishedOn;


    public Taxon(String speciesName, String userName, String commonName, String incubationDays, Double eggSize, java.util.Date publishedOn) {
        this.speciesName = speciesName;
        this.userName = userName;
        this.commonName = commonName;
        this.incubationDays = incubationDays;
        this.eggSize = eggSize;
        this.publishedOn = publishedOn;
    }

    @Ignore
    public Taxon(){}

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getIncubationDays() {
        return incubationDays;
    }

    public void setIncubationDays(String incubationDays) {
        this.incubationDays = incubationDays;
    }

    public Double getEggSize() {
        return eggSize;
    }

    public void setEggSize(Double eggSize) {
        this.eggSize = eggSize;
    }

    public java.util.Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(java.util.Date publishedOn) {
        this.publishedOn = publishedOn;
    }
}
