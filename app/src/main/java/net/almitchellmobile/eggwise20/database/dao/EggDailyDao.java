package net.almitchellmobile.eggwise20.database.dao;

import android.database.Cursor;

import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EggDailyDao {
    String sqlSelect = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID ORDER BY ReadingDayNumber ASC, EggLabel ASC";
    @Query(sqlSelect)
    List<EggDaily> getEggDaily_BatchEggDay(Long eggBatchID);

    String sqlSelectSumSingle = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = :readingDayNumber AND EggDailyID = :eggDailyID " +
            " GROUP BY ReadingDayNumber , EggLabel  " +
            " ORDER BY ReadingDayNumber ASC, EggLabel ASC ";
    @Query(sqlSelectSumSingle)
    Double getEggDaily_ComputeSum_GroupBy_Day_EggLabel_Single_Tracking(Long eggBatchID, Integer readingDayNumber, Long eggDailyID);

    String sqlSelectAvgSingle = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = :readingDayNumber AND EggDailyID = :eggDailyID " +
            " GROUP BY ReadingDayNumber, EggLabel  " +
            " ORDER BY ReadingDayNumber ASC, EggLabel ASC ";
    @Query(sqlSelectAvgSingle)
    Double getEggDaily_Compute_Avg_GroupBy_Day_Egglabel_Single_Tracking(Long eggBatchID, Integer readingDayNumber, Long eggDailyID);

    String sqlSelectSumBatch = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = :readingDayNumber  " +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectSumBatch)
    Double getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(Long eggBatchID, Integer readingDayNumber);

    String sqlSelectAvgBatch = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = :readingDayNumber" +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectAvgBatch)
    Double getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(Long eggBatchID, Integer readingDayNumber);

    String sqlSelectAvgBatchGroupByDay = "SELECT ReadingDayNumber, AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectAvgBatchGroupByDay)
    Cursor getEggDaily_Get_ReadingDay_AvgEggWeight_For_Batch_GroupBy_Day(Long eggBatchID);

    String sqlComputeAvgDay0Batch = "SELECT AVG(EggWeight) EggWeightAvg0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = 0 " +
            " GROUP BY ReadingDayNumber  ";
    @Query(sqlComputeAvgDay0Batch)
    Double getEggDaily_ComputeAvg_Day0_Batch_Tracking(Long eggBatchID);

    String sqlComputeAvgDay0Single = "SELECT AVG(EggWeight) EggWeightAvg0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = 0  AND EggDailyID = :eggDailyID" +
            " GROUP BY ReadingDayNumber, EggLabel  ";
    @Query(sqlComputeAvgDay0Single)
    Double getEggDaily_ComputeAvg_Day0_Single_Tracking(Long eggBatchID, Long eggDailyID);

    String sqlSelectAvgDay0 = "SELECT EggWeightAverageDay0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = 0 ";
    @Query(sqlSelectAvgDay0)
    Double getEggDaily_BatchEggDayAvgDay0(Long eggBatchID);



    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertEggDaily(EggDaily eggDaily);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateEggDaily(EggDaily eggDaily);

    String sqlUpdateEggDaily = "Update " + Constants.TABLE_NAME_EGG_DAILY + "  " +
            "SET EggWeightSum = :eggWeightSum, " +
            "   EggWeightAverageCurrent = :eggWeightAverageCurrent, " +
            "   EggWeightAverageDay0 = :eggWeightAverageDay0, " +
            "   ActualWeightLossPercent = :actualWeightLossPercent, " +
            "   TargetWeightLossPercent = :targetWeightLossPercent, " +
            "   WeightLossDeviation = :weightLossDeviation " +
            "WHERE EggDailyID =  :eggDailyID ";
    @Query(sqlUpdateEggDaily)
    void updateEggDaily_Sum_Averages_Percents(Double eggWeightSum, Double eggWeightAverageCurrent,
                                    Double eggWeightAverageDay0, Double actualWeightLossPercent,
                                    Double targetWeightLossPercent, Double weightLossDeviation,
                                              Long eggDailyID);






    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteEggDaily(EggDaily eggDaily);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteEggDaily(EggDaily... eggDaily);
}
