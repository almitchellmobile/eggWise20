package net.almitchellmobile.eggwise20.database.dao;

import net.almitchellmobile.eggwise20.database.model.Breeders;
import net.almitchellmobile.eggwise20.util.Constants;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BreedersDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_BREEDERS)
    List<Breeders> getBreeders();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert
    long insertBreeders(Breeders breeders);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void updateBreeders(Breeders breeders);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void deleteBreeders(Breeders breeders);

    // Note... is varargs, here note is an array
    /*
     * delete list of objects from database
     * @param note, array of oject to be deleted
     */
    @Delete
    void deleteBreeders(Breeders... breeders);
}
