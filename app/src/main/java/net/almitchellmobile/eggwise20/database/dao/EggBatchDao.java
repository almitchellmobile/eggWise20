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

    String sqlSelectAll = "SELECT * FROM "+ Constants.TABLE_NAME_EGG_BATCH + " ORDER BY SetDate DESC, BatchLabel ASC";
    @Query(sqlSelectAll)
    List<EggBatch> getAllEggBatch();

    String sqlSelectSum = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel " +
            " GROUP BY ReadingDayNumber " +
            " ORDER BY BatchLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectSum)
    Double getEggDaily_BatchDaySum(String batchLabel);

    String sqlSelectAvg = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel " +
            " GROUP BY  BatchLabel, ReadingDayNumber " +
            " ORDER BY  BatchLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectAvg)
    Double getEggDaily_BatchDayAvg(String batchLabel);

    String sqlSelectAvgDay0 = "SELECT EggWeightAverageDay0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel AND ReadingDayNumber = 0 ";
    @Query(sqlSelectAvgDay0)
    Double getEggDaily_BatchDayAvgDay0(String batchLabel);

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
