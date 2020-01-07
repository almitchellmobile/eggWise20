package net.almitchellmobile.eggwise20.database;

import android.content.Context;

import net.almitchellmobile.eggwise20.database.model.Incubator;
import net.almitchellmobile.eggwise20.database.dao.IncubatorDao;
import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.DateRoomConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = { Incubator.class }, version = 1)
@TypeConverters({DateRoomConverter.class})
public abstract class EggWiseDatabse extends RoomDatabase {

    public abstract IncubatorDao getIncubatorDao();

    private static EggWiseDatabse eggWiseDB;

    // synchronized is use to avoid concurrent access in multithred environment
    public static /*synchronized*/ EggWiseDatabse getInstance(Context context) {
        if (null == eggWiseDB) {
            eggWiseDB  = buildDatabaseInstance(context);
        }
        return eggWiseDB;
    }

    private static EggWiseDatabse buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                EggWiseDatabse.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        eggWiseDB = null;
    }


}
