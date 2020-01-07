package net.almitchellmobile.eggwise20.database.dao;

import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface EggDailyDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_EGGDAILY)
    List<EggDaily> getEggDaily();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertIncubator(EggDaily eggDaily);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateIncubator(EggDaily eggDaily);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteIncubator(EggDaily eggDaily);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteIncubator(EggDaily... eggDaily);
}
