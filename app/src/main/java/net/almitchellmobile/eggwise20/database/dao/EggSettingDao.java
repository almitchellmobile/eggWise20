package net.almitchellmobile.eggwise20.database.dao;

import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggSetting;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EggSettingDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_EGG_SETTING)
    List<EggSetting> getEggSetting();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertEggSetting(EggSetting eggSetting);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateEggSetting(EggBatch eggSetting);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteEggSetting(EggSetting eggSetting);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteEggSetting(EggSetting... eggSetting);
}
