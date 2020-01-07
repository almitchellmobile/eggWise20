package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_OPTIONS)
public class Options implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long optionID;

    @ColumnInfo(name = "OptionName")
    public String optionName;

    @ColumnInfo(name = "OptionValue")
    public String optionValue;

    public Options(String optionName, String optionValue) {
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    @Ignore
    public Options(){}

    public Long getOptionID() {
        return optionID;
    }

    public void setOptionID(Long optionID) {
        this.optionID = optionID;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
