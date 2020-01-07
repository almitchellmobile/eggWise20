package net.almitchellmobile.eggwise20;


import net.almitchellmobile.eggwise20.util.Constants;

import java.io.Serializable;
import java.sql.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME_EGGDAILY)
public class EggDaily implements Serializable {


    @PrimaryKey(autoGenerate = true)
    public Long eggDailyID;

    @ColumnInfo(name = "SettingID")
    public Long settingID;

    @ColumnInfo(name = "BatchLabel")
    public String batchLabel;

    @ColumnInfo(name = "EggLabel")
    public String eggLabel;

    @ColumnInfo(name = "ReadingDate")
    public Date readingDate;

    @ColumnInfo(name = "SetDate")
    public Date setDate;

    @ColumnInfo(name = "ReadingDayNumber")
    public Integer readingDayNumber;

    @ColumnInfo(name = "EggWeight")
    public Double eggWeight;

    @ColumnInfo(name = "EggWeightAverage")
    public double eggWeightAverage;

    @ColumnInfo(name = "EggDailyComment")
    public String eggDailyComment;

    @ColumnInfo(name = "IncubatorName")
    public String incubatorName;

    @ColumnInfo(name = "NumberOfEggs")
    public Integer numberOfEggs;

}
