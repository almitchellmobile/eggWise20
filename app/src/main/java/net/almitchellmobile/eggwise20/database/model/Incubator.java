package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_INCUBATORS)
public class Incubator implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long incubatorId;

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName;

    @ColumnInfo(name = "MFGModel")
    public String mfgModel;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

    public Incubator(String incubatorName, String mfgModel, Integer numberOfEggs) {
        this.incubatorName = incubatorName;
        this.mfgModel = mfgModel;
        this.numberOfEggs = numberOfEggs;
    }

    @Ignore
    public Incubator(){}

    public Long getIncubatorId() {
        return incubatorId;
    }

    public void setIncubatorId(Long incubatorId) {
        this.incubatorId = incubatorId;
    }

    public String getIncubatorName() {
        return incubatorName;
    }
    public void setIncubatorName(String incubatorName) {
        this.incubatorName = incubatorName;
    }

    public String getMFGModel() {
        return mfgModel;
    }
    public void setMFGModel(String mfgModel) {
        this.mfgModel = mfgModel;
    }

    public Integer getNumberOfEggs() {
        return numberOfEggs;
    }
    public void setNumberOfEggs(Integer numberOfEggs) {
        this.numberOfEggs = numberOfEggs;
    }


}
