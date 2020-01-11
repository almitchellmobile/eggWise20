package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggSetting;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEggBatchActivity extends AppCompatActivity {

    TextView et_egg_label, et_number_of_eggs, et_species_name, et_common_name,
            et_incubator_name, et_set_date, et_hatch_date;
    CalendarView cv_set_date, cv_hatch_date;
    Button button_save_add_egg_batch;

    RadioGroup rg_track_weight_loss;

    public EggWiseDatabse eggWiseDatabse;

    private EggSetting eggSetting;
    String eggLabel = "";
    String disposition = "";
    Long motherID = 0L;
    String motherName = "";
    Long fatherID = 0L;
    String fatherName = "";
    Long speciesID = 0L;
    String speciesName = "";
    Double eggHeight = 0.0D;
    Double eggBreadth = 0.0D;
    String layDate = "";
    String setDate = "";
    Long incubatorID = 0L;
    String location = "";
    String hatchDate = "";
    Double lossAtPIP = 0.0D;
    String newestLayDate = "";
    String oldestLayDate = "";
    String settingType = "";
    Integer numberOfEggs = 0;
    Double pipDate = 0.0D;
    Integer trackingOption = 0;
    String reminderHatchDate = "";
    Integer reminderIncubatorSetting = 0;
    Integer reminderIncubatorWater = 0;
    Integer reminderEggCandeling = 0;
    Integer reminderEggTurning = 0;
    Double desiredWeightLoss = 0.0D;
    String commonName = "";

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_egg_batch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        et_egg_label = findViewById(R.id.et_egg_label);
        et_species_name = findViewById(R.id.et_species_name);
        et_common_name = findViewById(R.id.et_common_name);
        et_incubator_name = findViewById(R.id.et_incubator_name);
        et_number_of_eggs = findViewById(R.id.et_number_of_eggs);
        et_hatch_date = findViewById(R.id.et_hatch_date);
        et_set_date = findViewById(R.id.et_set_date);

        cv_set_date = findViewById(R.id.cv_set_date);
        cv_set_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                setDate = day + "/" + month + "/" + year;
                et_set_date.setText(setDate);
                Toast.makeText(getApplicationContext(), setDate, Toast.LENGTH_LONG).show();
            }
        });

        cv_hatch_date = findViewById(R.id.cv_hatch_date);;
        cv_hatch_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                hatchDate = day + "/" + month + "/" + year;
                et_hatch_date.setText(hatchDate);
                Toast.makeText(getApplicationContext(), hatchDate, Toast.LENGTH_LONG).show();
            }
        });

        rg_track_weight_loss = findViewById(R.id.rg_track_weight_loss);
        rg_track_weight_loss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = rg_track_weight_loss.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton trackWeightLoss = (RadioButton) findViewById(selectedId);
                String trackWeightLossValue = trackWeightLoss.getText().toString();
                if (trackWeightLossValue.toUpperCase().contains("BATCH")) {
                    trackingOption = 1; //Track entire batch
                } else {
                    trackingOption = 2; //Track specific eggs
                }

                Toast.makeText(AddEggBatchActivity.this,
                        trackWeightLossValue, Toast.LENGTH_SHORT).show();
            }

        });

        button_save_add_egg_batch = findViewById(R.id.button_save_add_egg_batch);
        button_save_add_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){

                    eggSetting.setEggLabel(et_egg_label.getText().toString());
                    eggSetting.setDisposition("");
                    eggSetting.setMotherID(null);
                    eggSetting.setSpeciesName(et_species_name.getText().toString());
                    eggSetting.setFatherID(null);
                    eggSetting.setFatherName("");
                    eggSetting.setSpeciesID(null);
                    eggSetting.setSpeciesName("");
                    eggSetting.setEggHeight(null);
                    eggSetting.setEggBreadth(null);
                    eggSetting.setLayDate("");
                    eggSetting.setSetDate(setDate);
                    eggSetting.setIncubatorID(null);
                    eggSetting.setIncubatorName(et_incubator_name.getText().toString());
                    eggSetting.setLocation("");
                    eggSetting.setHatchDate(hatchDate);
                    eggSetting.setLossAtPIP(null);
                    eggSetting.setNewestLayDate("");
                    eggSetting.setOldestLayDate("");
                    eggSetting.setSettingType("");
                    eggSetting.setNumberOfEggs(Integer.parseInt(et_number_of_eggs.getText().toString()));
                    eggSetting.setPipDate(null);
                    eggSetting.setTrackingOption(0);
                    eggSetting.setReminderHatchDate(null);
                    eggSetting.setReminderIncubatorSetting(0);
                    eggSetting.setReminderIncubatorWater(0);
                    eggSetting.setReminderEggCandeling(0);
                    eggSetting.setReminderEggTurning(0);
                    eggSetting.setDesiredWeightLoss(null);
                    eggSetting.setCommonName(et_common_name.getText().toString());

                    eggWiseDatabse.getEggSettingDao().updateEggSetting((eggSetting));
                    setResult(eggSetting,29);
                }else {
                    eggSetting = new EggSetting(et_egg_label.getText().toString(),
                            disposition,
                            motherID,
                            motherName,
                            fatherID,
                            fatherName,
                            speciesID,
                            et_species_name.getText().toString(),
                            eggHeight,
                            eggBreadth,
                            layDate,
                            setDate,
                            incubatorID,
                            et_incubator_name.getText().toString(),
                            location,
                            hatchDate,
                            lossAtPIP,
                            newestLayDate,
                            oldestLayDate,
                            settingType,
                            Integer.parseInt(et_number_of_eggs.getText().toString()),
                            pipDate,
                            trackingOption,
                            reminderHatchDate,
                            reminderIncubatorSetting,
                            reminderIncubatorWater,
                            reminderEggCandeling,
                            reminderEggTurning,
                            desiredWeightLoss,
                            et_common_name.getText().toString());


                    new InsertTask(AddEggBatchActivity.this,eggSetting).execute();
                }
            }
        });

        eggWiseDatabse = EggWiseDatabse.getInstance(AddEggBatchActivity.this);

    }

    public void setResult(EggSetting eggSetting, int flag){
        setResult(flag,new Intent().putExtra("eggSetting",eggSetting));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddEggBatchActivity> activityReference;
        private EggSetting eggSetting;



        // only retain a weak reference to the activity
        InsertTask(AddEggBatchActivity context, EggSetting eggSetting) {
            activityReference = new WeakReference<>(context);
            this.eggSetting = eggSetting;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eggWiseDatabse.getEggSettingDao().insertEggSetting(eggSetting);
            eggSetting.setEggSettingID(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(eggSetting,1);
                activityReference.get().finish();
            }
        }
    }

}
