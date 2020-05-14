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
    String sqlSelectOrderBy1 = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID ORDER BY ReadingDayNumber ASC, LENGTH(EggLabel) ASC, EggLabel ASC";
    @Query(sqlSelectOrderBy1)
    List<EggDaily> getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(Long eggBatchID);

    String sqlSelectOrderBy2 = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID  ORDER BY ReadingDayNumber DESC, LENGTH(EggLabel) ASC, EggLabel ASC";
    @Query(sqlSelectOrderBy2)
    List<EggDaily> getEggDaily_BatchEggDay_Day_DESC_Label_ASC(Long eggBatchID);

    String sqlSelectOrderBy3 = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID  ORDER BY ReadingDayNumber ASC, LENGTH(EggLabel) DESC, EggLabel DESC";
    @Query(sqlSelectOrderBy3)
    List<EggDaily> getEggDaily_BatchEggDay_Day_ASC_Label_DESC(Long eggBatchID);

    String sqlSelectOrderBy4 = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID  ORDER BY ReadingDayNumber DESC, LENGTH(EggLabel) DESC, EggLabel DESC";
    @Query(sqlSelectOrderBy4)
    List<EggDaily> getEggDaily_BatchEggDay_Day_DESC_Label_DESC(Long eggBatchID);

    String sqlSelectAllBatchLabels = "SELECT DISTINCT(BatchLabel) FROM "+ Constants.TABLE_NAME_EGG_DAILY +
            " GROUP BY BatchLabel ORDER BY BatchLabel ASC ";
    @Query(sqlSelectAllBatchLabels)
    List<String> getEggDaily_AllBatchLabels();

    String sqlRejectedEggCount = "SELECT Count(DISTINCT EggLabel) RejectedEggCount FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND RejectedEgg = 1 " +
            " GROUP BY EggBatchID, BatchLabel ";
    @Query(sqlRejectedEggCount)
    Integer getEggDaily_CountRejectedEggs(Long eggBatchID);

    String sqlSelectSumSingle = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = :readingDayNumber " +
            " AND EggDailyID = :eggDailyID " +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber , EggLabel  " +
            " ORDER BY ReadingDayNumber ASC, EggLabel ASC ";
    @Query(sqlSelectSumSingle)
    Double getEggDaily_ComputeSum_GroupBy_Day_EggLabel_Single_Tracking(Long eggBatchID, Integer readingDayNumber, Long eggDailyID);

    String sqlSelectAvgSingle = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = :readingDayNumber " +
            " AND EggDailyID = :eggDailyID " +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber, EggLabel  " +
            " ORDER BY ReadingDayNumber ASC, EggLabel ASC ";
    @Query(sqlSelectAvgSingle)
    Double getEggDaily_Compute_Avg_GroupBy_Day_Egglabel_Single_Tracking(Long eggBatchID, Integer readingDayNumber, Long eggDailyID);

    String sqlSelectSumBatch = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = :readingDayNumber  " +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectSumBatch)
    Double getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(Long eggBatchID, Integer readingDayNumber);

    String sqlSelectAvgBatch = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = :readingDayNumber" +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectAvgBatch)
    Double getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(Long eggBatchID, Integer readingDayNumber);

    String sqlSelectAvgBatchGroupByDay = "SELECT ReadingDayNumber, AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber  " +
            " ORDER BY ReadingDayNumber ASC ";
    @Query(sqlSelectAvgBatchGroupByDay)
    Cursor getEggDaily_Get_ReadingDay_AvgEggWeight_For_Batch_GroupBy_Day(Long eggBatchID);

    String sqlComputeAvgDay0Batch = "SELECT AVG(EggWeight) EggWeightAvg0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = 0 " +
            " AND RejectedEgg = 0 " +
            " GROUP BY ReadingDayNumber  ";
    @Query(sqlComputeAvgDay0Batch)
    Double getEggDaily_ComputeAvg_Day0_Batch_Tracking(Long eggBatchID);

    String sqlComputeAvgDay0Single = "SELECT AVG(EggWeight) EggWeightAvg0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " AND ReadingDayNumber = 0  " +
            " AND EggDailyID = :eggDailyID" +
            " GROUP BY ReadingDayNumber, EggLabel  ";
    @Query(sqlComputeAvgDay0Single)
    Double getEggDaily_ComputeAvg_Day0_Single_Tracking(Long eggBatchID, Long eggDailyID);

    String sqlSelectAvgDay0 = "SELECT EggWeightAverageDay0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = 0";
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

    String sqlUpdateEggDaily_RejectedEgg = "Update " + Constants.TABLE_NAME_EGG_DAILY + "  " +
            "SET RejectedEgg = :rejectedEgg " +
            "WHERE EggBatchID = :eggBatchID AND EggLabel == (:eggLabel)  " ;
    @Query(sqlUpdateEggDaily_RejectedEgg)
    void updateEggDaily_RejectedEgg(Integer rejectedEgg, Long eggBatchID, String eggLabel);

    String sqlUpdateEggDaily_RestoreAllRejectedEggs = "Update " + Constants.TABLE_NAME_EGG_DAILY + "  " +
            "SET RejectedEgg = 0 " +
            "WHERE EggBatchID = :eggBatchID " ;
    @Query(sqlUpdateEggDaily_RestoreAllRejectedEggs)
    void updateEggDaily_RejectedEgg(Long eggBatchID);


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
