package net.almitchellmobile.eggwise20.database;

import android.content.Context;

import net.almitchellmobile.eggwise20.database.dao.BatchLossDao;
import net.almitchellmobile.eggwise20.database.dao.BreedersDao;
import net.almitchellmobile.eggwise20.database.dao.EggDailyDao;
import net.almitchellmobile.eggwise20.database.dao.EggSettingDao;
import net.almitchellmobile.eggwise20.database.dao.EggBatchDao;
import net.almitchellmobile.eggwise20.database.dao.IncubatorDailyDao;
import net.almitchellmobile.eggwise20.database.dao.IncubatorDao;
import net.almitchellmobile.eggwise20.database.dao.OptionsDao;
import net.almitchellmobile.eggwise20.database.dao.TaxonDao;
import net.almitchellmobile.eggwise20.database.model.BatchLoss;
import net.almitchellmobile.eggwise20.database.model.Breeders;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.database.model.EggSetting;
import net.almitchellmobile.eggwise20.database.model.Incubator;
import net.almitchellmobile.eggwise20.database.model.IncubatorDaily;
import net.almitchellmobile.eggwise20.database.model.Options;
import net.almitchellmobile.eggwise20.database.model.Taxon;
import net.almitchellmobile.eggwise20.util.Constants;
import net.almitchellmobile.eggwise20.util.DateRoomConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = { Incubator.class, BatchLoss.class, Breeders.class, EggDaily.class,
        EggSetting.class, EggBatch.class, IncubatorDaily.class, Options.class, Taxon.class},
        version = 18, exportSchema = true)

@TypeConverters({DateRoomConverter.class})
public abstract class EggWiseDatabse extends RoomDatabase {

    public abstract IncubatorDao getIncubatorDao();
    public abstract EggSettingDao getEggSettingDao();
    public abstract EggBatchDao getEggBatchDao();
    public abstract BatchLossDao getBacthLossDao();
    public abstract BreedersDao getBreedersDao();
    public abstract EggDailyDao getEggDailyDao();
    public abstract IncubatorDailyDao getIncubatorDailyDao();
    public abstract OptionsDao getOptionsDao();
    public abstract TaxonDao getTaxonDao();

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
                Constants.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public  void cleanUp(){
        eggWiseDB = null;
    }


}
