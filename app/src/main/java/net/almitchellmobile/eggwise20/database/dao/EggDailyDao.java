package net.almitchellmobile.eggwise20.database.dao;

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
            " WHERE BatchLabel LIKE  :batchLabel ORDER BY EggLabel, ReadingDayNumber";
    @Query(sqlSelect)
    List<EggDaily> getEggDaily_BatchEggDay(String batchLabel);

    String sqlSelectSum = "SELECT SUM(EggWeight) EggWeightSum FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel " +
            " GROUP BY EggLabel, ReadingDayNumber " +
            " ORDER BY EggLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectSum)
    Double getEggDaily_BatchEggDaySum(String batchLabel);

    String sqlSelectAvg = "SELECT AVG(EggWeight) EggWeightAvg FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel " +
            " GROUP BY  EggLabel, ReadingDayNumber " +
            " ORDER BY  EggLabel ASC, ReadingDayNumber ASC ";
    @Query(sqlSelectAvg)
    Double getEggDaily_BatchEggDayAvg(String batchLabel);

    String sqlSelectAvgDay0 = "SELECT EggWeightAverageDay0 FROM "+
            Constants.TABLE_NAME_EGG_DAILY +
            " WHERE BatchLabel LIKE  :batchLabel AND ReadingDayNumber = 0 ";
    @Query(sqlSelectAvgDay0)
    Double getEggDaily_BatchEggDayAvgDay0(String batchLabel);

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
