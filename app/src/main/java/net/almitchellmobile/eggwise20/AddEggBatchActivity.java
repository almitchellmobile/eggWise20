package net.almitchellmobile.eggwise20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggSetting;
import net.almitchellmobile.eggwise20.util.SetDate;

import java.lang.ref.WeakReference;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEggBatchActivity extends AppCompatActivity {

    TextInputEditText et_egg_label, et_number_of_eggs, et_species_name, et_common_name,
            et_incubator_name, et_set_date, et_hatch_date, et_desired_weight_loss,
            et_location;
    CalendarView cv_set_date, cv_hatch_date;
    Button button_save_add_egg_batch;

    RadioGroup rg_track_weight_loss;
    RadioButton rb_track_entire_batch;
    RadioButton rb_track_specific_eggs;

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

    SetDate fromDateSetDate;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_egg_batch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddEggBatchActivity.this);

        et_egg_label = findViewById(R.id.et_egg_label);
        et_species_name = findViewById(R.id.et_species_name);
        et_common_name = findViewById(R.id.et_common_name);
        et_incubator_name = findViewById(R.id.et_incubator_name);
        et_number_of_eggs = findViewById(R.id.et_number_of_eggs);
        et_desired_weight_loss = findViewById(R.id.et_desired_weight_loss);

        et_set_date = findViewById(R.id.et_set_date);
        rg_track_weight_loss = findViewById(R.id.rg_track_weight_loss);
        rb_track_entire_batch = findViewById(R.id.rb_track_entire_batch);
        rb_track_specific_eggs = findViewById(R.id.rb_track_specific_eggs);


        button_save_add_egg_batch = findViewById(R.id.button_save_add_egg_batch);
        if ( (eggSetting = (EggSetting) getIntent().getSerializableExtra("eggSetting"))!=null ){
            getSupportActionBar().setTitle("Update Egg Batch");
            update = true;
            button_save_add_egg_batch.setText("Update");
            //et_title.setText(note.getTitle());
            //et_content.setText(note.getContent());

            et_egg_label.setText(eggSetting.getEggLabel());
            et_species_name.setText(eggSetting.getSpeciesName());
            et_common_name.setText(eggSetting.getCommonName());
            et_set_date.setText(eggSetting.getSetDate());
            et_hatch_date.setText(eggSetting.getHatchDate());
            et_incubator_name.setText(eggSetting.getIncubatorName());
            et_number_of_eggs.setText(eggSetting.getNumberOfEggs().toString());
            et_location.setText(eggSetting.getLocation());
            if (eggSetting.getTrackingOption() == 1) {
                rb_track_entire_batch.setChecked(true  );
            } else {
                rb_track_specific_eggs.setChecked(true);
            }
            et_desired_weight_loss.setText(eggSetting.getDesiredWeightLoss().toString());
        }
        button_save_add_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    /*note.setContent(et_content.getText().toString());
                    note.setTitle(et_title.getText().toString());
                    noteDatabase.getNoteDao().updateNote(note);
                    setResult(note,2);*/

                    eggSetting.setEggLabel(et_egg_label.getText().toString());
                    eggSetting.setDisposition("");
                    eggSetting.setMotherID(null);
                    eggSetting.setSpeciesName(et_species_name.getText().toString());
                    eggSetting.setCommonName(et_common_name.getText().toString());
                    eggSetting.setFatherID(null);
                    eggSetting.setFatherName("");
                    eggSetting.setSpeciesID(null);
                    eggSetting.setSpeciesName("");
                    eggSetting.setEggHeight(null);
                    eggSetting.setEggBreadth(null);
                    eggSetting.setLayDate("");
                    eggSetting.setSetDate(setDate);
                    eggSetting.setHatchDate(hatchDate);
                    eggSetting.setIncubatorID(null);
                    eggSetting.setIncubatorName(et_incubator_name.getText().toString());
                    eggSetting.setLocation(et_location.getText().toString());
                    eggSetting.setNumberOfEggs(Integer.parseInt(et_number_of_eggs.getText().toString()));
                    eggSetting.setLossAtPIP(null);
                    eggSetting.setNewestLayDate("");
                    eggSetting.setOldestLayDate("");
                    eggSetting.setSettingType("");
                    eggSetting.setPipDate(null);
                    eggSetting.setTrackingOption(0);
                    eggSetting.setReminderHatchDate(null);
                    eggSetting.setReminderIncubatorSetting(0);
                    eggSetting.setReminderIncubatorWater(0);
                    eggSetting.setReminderEggCandeling(0);
                    eggSetting.setReminderEggTurning(0);
                    eggSetting.setDesiredWeightLoss(null);


                    eggWiseDatabse.getEggSettingDao().updateEggSetting((eggSetting));
                    setResult(eggSetting,29);


                }else {
                    /*note = new Note(et_content.getText().toString(), et_title.getText().toString());
                    new InsertTask(AddNoteActivity.this,note).execute();*/

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

        //et_set_date.setFocusable(false);
        //fromDateSetDate = new SetDate((EditText) et_set_date, this);
        DatePickerDialog.OnDateSetListener setDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelSetDate();
                        //et_hatch_date.requestFocus();
                    }

                };
        et_set_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(AddEggBatchActivity.this, setDateDatePickerDialog, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        et_hatch_date = findViewById(R.id.et_hatch_date);
        //et_hatch_date.setFocusable(false);
        DatePickerDialog.OnDateSetListener hatchDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelHatchDate();
                        //rg_track_weight_loss.requestFocus();
                    }

                };
        et_hatch_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(AddEggBatchActivity.this, hatchDateDatePickerDialog, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });


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


        /*button_save_add_egg_batch = findViewById(R.id.button_save_add_egg_batch);
        button_save_add_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){

                    eggSetting.setEggLabel(et_egg_label.getText().toString());
                    eggSetting.setDisposition("");
                    eggSetting.setMotherID(null);
                    eggSetting.setSpeciesName(et_species_name.getText().toString());
                    eggSetting.setCommonName(et_common_name.getText().toString());
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
                    eggSetting.setTrackingOption(trackingOption);
                    eggSetting.setReminderHatchDate(null);
                    eggSetting.setReminderIncubatorSetting(0);
                    eggSetting.setReminderIncubatorWater(0);
                    eggSetting.setReminderEggCandeling(0);
                    eggSetting.setReminderEggTurning(0);
                    eggSetting.setDesiredWeightLoss(desiredWeightLoss);

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
        });*/



    }

    private void updateLabelSetDate() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDate = sdf.format(myCalendar.getTime());
        et_set_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelHatchDate() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        hatchDate = sdf.format(myCalendar.getTime());
        et_set_date.setText(sdf.format(myCalendar.getTime()));
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
