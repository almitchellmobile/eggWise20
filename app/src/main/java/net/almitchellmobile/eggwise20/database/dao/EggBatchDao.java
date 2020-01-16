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

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_EGG_BATCH)
    List<EggBatch> getEggBatch();

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
