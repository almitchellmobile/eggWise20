package net.almitchellmobile.eggwise20.database.model;

import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.TimestampConverter;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = Constants.TABLE_NAME_EGG_WEIGHT_LOSS)
public class EggWeightLoss implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long eggWeightLossID;

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

}
