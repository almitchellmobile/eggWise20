package net.almitchellmobile.eggwise20.database.dao;

import net.almitchellmobile.eggwise20.database.model.Options;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OptionsDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_OPTIONS)
    List<Options> getOptions();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertOptions(Options options);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateOptions(Options options);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteOptions(Options options);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteOptions(Options... options);

}
