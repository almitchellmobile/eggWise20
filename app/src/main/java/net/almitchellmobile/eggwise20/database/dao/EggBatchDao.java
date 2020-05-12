package net.almitchellmobile.eggwise20.database.dao;

import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EggBatchDao {

    String sqlSelectAll = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_BATCH + " ORDER BY SetDate DESC, LENGTH(BatchLabel) ASC, BatchLabel ASC ";
    @Query(sqlSelectAll)
    List<EggBatch> getAllEggBatch();

    String sqlSelectSum = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " GROUP BY ReadingDayNumber " +
            " ORDER BY LENGTH(BatchLabel) ASC, BatchLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectSum)
    Double getEggDaily_BatchDaySum(Long eggBatchID);

    String sqlSelectAvg = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID " +
            " GROUP BY  BatchLabel, ReadingDayNumber " +
            " ORDER BY  LENGTH(BatchLabel) ASC, BatchLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectAvg)
    Double getEggDaily_BatchDayAvg(Long eggBatchID);

    String sqlSelectAvgDay0 = "SELECT EggWeightAverageDay0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE EggBatchID = :eggBatchID AND ReadingDayNumber = 0 ";
    @Query(sqlSelectAvgDay0)
    Double getEggDaily_BatchDayAvgDay0(Long eggBatchID);

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertEggBatch(EggBatch eggBatch);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updatEggBatch(EggBatch eggBatch);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteEggBatch(EggBatch eggBatch);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteEggBatch(EggBatch... eggBatch);
}
