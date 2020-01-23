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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.util.SetDate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEggBatchActivity extends AppCompatActivity {

    TextInputEditText et_batch_label, et_number_of_eggs, et_species_name, et_common_name,
            et_incubator_name, et_set_date, et_hatch_date, et_desired_weight_loss,
            et_location, et_incubator_settings, et_temperature, et_incubation_days,
            et_number_of_eggs_hatched, et_target_weight_loss;
    CalendarView cv_set_date, cv_hatch_date;
    Button button_save_add_egg_batch;

    RadioGroup rg_track_weight_loss;
    RadioButton rb_track_entire_batch;
    RadioButton rb_track_specific_eggs;

    public EggWiseDatabse eggWiseDatabse;

    private EggBatch eggBatch;
    String batchLabel = "";
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
    String incubatorName = "";
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

    String commonName = "";
    String incubatorSettings = "";
    Double temperature = 0.0D;
    Integer incubationDays = 0;
    Integer numberOfEggsHatched = 0;
    Double targetWeightLoss = 0.0D;

    private boolean update;

    SetDate fromDateSetDate;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    List<EditText> validationList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_egg_batch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddEggBatchActivity.this);

        et_batch_label = findViewById(R.id.et_batch_label);
        et_species_name = findViewById(R.id.et_species_name);
        et_common_name = findViewById(R.id.et_common_name);
        et_incubator_name = findViewById(R.id.et_incubator_name);
        et_number_of_eggs = findViewById(R.id.et_number_of_eggs);
       et_set_date = findViewById(R.id.et_set_date);
        et_hatch_date = findViewById(R.id.et_hatch_date);
        et_location = findViewById(R.id.et_location);
        rg_track_weight_loss = findViewById(R.id.rg_track_weight_loss);
        rb_track_entire_batch = findViewById(R.id.rb_track_entire_batch);
        rb_track_specific_eggs = findViewById(R.id.rb_track_specific_eggs);
        et_incubator_settings = findViewById(R.id.et_incubator_settings);
        et_temperature = findViewById(R.id.et_temperature);
        et_incubation_days = findViewById(R.id.et_incubation_days);
        et_number_of_eggs_hatched = findViewById(R.id.et_number_of_eggs_hatched);
        et_target_weight_loss = findViewById(R.id.et_target_weight_loss);

        button_save_add_egg_batch = findViewById(R.id.button_save_add_egg_batch);
        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("UpdateEggBatch"))!=null ){
            getSupportActionBar().setTitle("Update Egg Batch");
            update = true;
            button_save_add_egg_batch.setText("Update");

            et_batch_label.setText(eggBatch.getBatchLabel());
            et_species_name.setText(eggBatch.getSpeciesName());
            et_common_name.setText(eggBatch.getCommonName());
            et_set_date.setText(eggBatch.getSetDate());
            et_hatch_date.setText(eggBatch.getHatchDate());
            et_incubator_name.setText(eggBatch.getIncubatorName());
            et_number_of_eggs.setText(eggBatch.getNumberOfEggs().toString());
            et_location.setText(eggBatch.getLocation());
            if (eggBatch.getTrackingOption() == 1) {
                rb_track_entire_batch.setChecked(true  );
            } else {
                rb_track_specific_eggs.setChecked(true);
            }
            et_incubator_settings.setText(eggBatch.getIncubatorSettings().toString());
            et_temperature.setText(eggBatch.getTemperature().toString());
            et_incubation_days.setText(eggBatch.getIncubationDays().toString());
            et_number_of_eggs_hatched.setText(eggBatch.getNumberOfEggsHatched().toString());
            et_target_weight_loss.setText(eggBatch.getTargetWeightLoss().toString());
        }
        button_save_add_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(isEmptyField(et_batch_label))) {
                    batchLabel = et_batch_label.getText().toString();
                }
                if (!(isEmptyField(et_species_name))) {
                    speciesName = et_species_name.getText().toString();
                }
                if (!(isEmptyField(et_common_name))) {
                    commonName = et_common_name.getText().toString();
                }
                if (isEmptyField(et_set_date)) {
                    setDate = "";
                }
                if (isEmptyField(et_hatch_date)) {
                    hatchDate = "";
                }
                if (!(isEmptyField(et_incubator_name))) {
                    incubatorName = et_incubator_name.getText().toString();
                }
                if (!(isEmptyField(et_location))) {
                    location = et_location.getText().toString();
                }
                if (!(isEmptyField(et_number_of_eggs))) {
                    numberOfEggs = Integer.parseInt(et_number_of_eggs.getText().toString());
                }

                if (!(isEmptyField(et_incubator_settings))) {
                    incubatorSettings = et_incubator_settings.getText().toString();
                }
                if (!(isEmptyField(et_temperature))) {
                    temperature = Double.parseDouble(et_temperature.getText().toString());
                }
                if (!(isEmptyField(et_incubation_days))) {
                    incubationDays = Integer.parseInt(et_incubation_days.getText().toString());
                }
                if (!(isEmptyField(et_number_of_eggs_hatched))) {
                    numberOfEggsHatched = Integer.parseInt(et_number_of_eggs_hatched.getText().toString());
                }
                if (!(isEmptyField(et_target_weight_loss))) {
                    targetWeightLoss = Double.parseDouble(et_target_weight_loss.getText().toString());
                }


                if (update){
                    eggBatch.setBatchLabel(batchLabel);
                    eggBatch.setSpeciesName(speciesName);
                    eggBatch.setCommonName(commonName);
                    eggBatch.setSpeciesID(null);
                    eggBatch.setLayDate(layDate);
                    eggBatch.setSetDate(setDate);
                    eggBatch.setHatchDate(hatchDate);
                    eggBatch.setIncubatorID(null);
                    eggBatch.setIncubatorName(incubatorName);
                    eggBatch.setLocation(location);
                    eggBatch.setNumberOfEggs(numberOfEggs);
                    eggBatch.setTrackingOption(trackingOption);

                    eggBatch.setIncubatorSettings(incubatorSettings);
                    eggBatch.setTemperature(temperature);
                    eggBatch.setIncubationDays(incubationDays);
                    eggBatch.setNumberOfEggsHatched(numberOfEggsHatched);
                    eggBatch.setTargetWeightLoss(targetWeightLoss);

                    eggWiseDatabse.getEggSettingDao().updateEggSetting((eggBatch));
                    setResult(eggBatch,13);


                }else {
                    eggBatch = new EggBatch(batchLabel,
                            numberOfEggs,
                            speciesID,
                            speciesName,
                            commonName,
                            layDate,
                            setDate,
                            hatchDate,
                            incubatorID,
                            incubatorName,
                            location,
                            trackingOption,
                            incubatorSettings,
                            temperature,
                            incubationDays,
                            numberOfEggsHatched,
                            targetWeightLoss
                            );

                    new InsertTask(AddEggBatchActivity.this,eggBatch).execute();
                }
            }
        });

        et_set_date.setFocusable(true);
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

        //et_hatch_date = findViewById(R.id.et_hatch_date);
        et_hatch_date.setFocusable(true);
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
                } else if (trackWeightLossValue.toUpperCase().contains("EGGS")) {
                    trackingOption = 2; //Track specific eggs
                } else {
                    trackingOption = 0;
                }

                Toast.makeText(AddEggBatchActivity.this,
                        trackWeightLossValue, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(this, "Field " + editText.getHint() + " is empty!", Toast.LENGTH_SHORT).show();
        return result;
    }


    void initValidationList() {
        validationList.add(et_batch_label);
        validationList.add(et_species_name);
        validationList.add(et_common_name);
        validationList.add(et_incubator_name);
        validationList.add(et_number_of_eggs);
        validationList.add(et_desired_weight_loss);
        validationList.add(et_set_date);
        validationList.add(et_hatch_date);
    }

    private void updateLabelSetDate() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        setDate = sdf.format(myCalendar.getTime());
        et_set_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelHatchDate() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        hatchDate = sdf.format(myCalendar.getTime());
        et_hatch_date.setText(sdf.format(myCalendar.getTime()));
    }

    public void setResult(EggBatch eggBatch, int flag){
        setResult(flag,new Intent().putExtra("eggBatch",eggBatch));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddEggBatchActivity> activityReference;
        private EggBatch eggBatch;


        // only retain a weak reference to the activity
        InsertTask(AddEggBatchActivity context, EggBatch eggBatch) {
            activityReference = new WeakReference<>(context);
            this.eggBatch = eggBatch;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eggWiseDatabse.getEggBatchDao().insertEggBatch(eggBatch);
            eggBatch.setEggBatchID(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(eggBatch,1);
                activityReference.get().finish();
            }
        }
    }

}
