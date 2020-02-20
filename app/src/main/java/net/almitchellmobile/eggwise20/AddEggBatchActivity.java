package net.almitchellmobile.eggwise20;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.util.Common;
import net.almitchellmobile.eggwise20.util.SetDate;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEggBatchActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
   /* public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 0;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 0.0F;*/

    public static final String mypreference = "mypref";

    AutoCompleteTextView et_batch_label, et_number_of_eggs, et_species_name, et_common_name,
            et_incubator_name, et_set_date, et_hatch_date,
            et_location, et_incubator_settings, et_temperature, et_incubation_days,
            et_number_of_eggs_hatched, et_target_weight_loss;
    CalendarView cv_set_date, cv_hatch_date;
    Button button_save_add_egg_batch;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab_add_save_egg_batch;

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
    Integer targetWeightLoss = 0;
    String hintStringValue = "";

    private boolean update;

    SetDate fromDateSetDate;
    Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    RelativeLayout rl_egg_batch;

    String[] incubatorSettingsValues = {"Relative Humidity Percentage", "Wet Bulb Readings"};
    ArrayAdapter<String> adapterIncubatorSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_egg_batch);
        Toolbar toolbar_add_egg_batch = findViewById(R.id.toolbar_add_egg_batch);
        setSupportActionBar(toolbar_add_egg_batch);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddEggBatchActivity.this);

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "");
        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        } else {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 0;
        }
        if (sharedpreferences.contains("default_weight_loss_percentage")) {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F);
            //double data = PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE;
            //int value = (int)data;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        } else {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 13.0F;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 13;
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F);
        } else {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
        }

        rl_egg_batch  = findViewById(R.id.rl_egg_batch);

        fab_add_save_egg_batch = findViewById(R.id.fab_add_save_egg_batch);
        fab_add_save_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggBatch();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        et_batch_label = findViewById(R.id.et_batch_label);
        et_batch_label.setSelectAllOnFocus(true);

        et_species_name = findViewById(R.id.et_species_name);
        et_species_name.setSelectAllOnFocus(true);

        et_common_name = findViewById(R.id.et_common_name);
        et_common_name.setSelectAllOnFocus(true);

        et_incubator_name = findViewById(R.id.et_incubator_name);
        et_incubator_name.setSelectAllOnFocus(true);

        et_number_of_eggs = findViewById(R.id.et_number_of_eggs);
        et_number_of_eggs.setSelectAllOnFocus(true);

        et_set_date = findViewById(R.id.et_set_date);
        et_set_date.setSelectAllOnFocus(true);

        et_hatch_date = findViewById(R.id.et_hatch_date);
        et_hatch_date.setSelectAllOnFocus(true);

        et_location = findViewById(R.id.et_location);
        et_location.setSelectAllOnFocus(true);

        rg_track_weight_loss = findViewById(R.id.rg_track_weight_loss);
        rb_track_entire_batch = findViewById(R.id.rb_track_entire_batch);
        // Batch tracking is default
        rb_track_entire_batch.setChecked(true);
        trackingOption = 1;
        rb_track_specific_eggs = findViewById(R.id.rb_track_specific_eggs);

        et_incubator_settings = findViewById(R.id.et_incubator_settings);
        et_incubator_settings.setSelectAllOnFocus(true);
        adapterIncubatorSettings = new ArrayAdapter<String>
                (AddEggBatchActivity.this, android.R.layout.select_dialog_item, incubatorSettingsValues);
        et_incubator_settings = (AutoCompleteTextView) findViewById(R.id.et_incubator_settings);
        et_incubator_settings.setThreshold(1);//will start working from first character
        et_incubator_settings.setAdapter(adapterIncubatorSettings);//setting the adapter data into the AutoCompleteTextView
        et_incubator_settings.setTextColor(Color.RED);
        if (Common.PREF_HUMIDITY_MEASURED_WITH.trim().length() != 0) {
            hintStringValue = "Humidity (" + Common.PREF_HUMIDITY_MEASURED_WITH + ")";
            et_incubator_settings.setHint(hintStringValue);
        }
        et_temperature = findViewById(R.id.et_temperature);
        et_temperature.setSelectAllOnFocus(true);
        if (Common.PREF_TEMPERATURE_ENTERED_IN.trim().length() != 0) {
            hintStringValue = "Temperature (" + Common.PREF_TEMPERATURE_ENTERED_IN + ")";
            et_temperature.setHint(hintStringValue);
        }
        et_incubation_days = findViewById(R.id.et_incubation_days);
        et_incubation_days.setSelectAllOnFocus(true);

        et_number_of_eggs_hatched = findViewById(R.id.et_number_of_eggs_hatched_rl);
        et_number_of_eggs_hatched.setSelectAllOnFocus(true);

        et_target_weight_loss = findViewById(R.id.et_target_weight_loss);
        et_target_weight_loss.setSelectAllOnFocus(true);
        if (Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER.toString().trim().length() != 0) {
            hintStringValue = "Target Weight Loss % (" + Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER + ")";
            et_target_weight_loss.setHint(hintStringValue);
        }

        button_save_add_egg_batch = findViewById(R.id.button_save_add_egg_batch);
        button_save_add_egg_batch.setVisibility(View.GONE);

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("UpdateEggBatch"))!=null ){
            getSupportActionBar().setTitle("Update Egg Batch");
            update = true;
            //button_save_add_egg_batch.setText("Update");

            et_batch_label.setText(eggBatch.getBatchLabel());
            et_species_name.setText(eggBatch.getSpeciesName());
            et_common_name.setText(eggBatch.getCommonName());
            et_set_date.setText(eggBatch.getSetDate());
            et_hatch_date.setText(eggBatch.getHatchDate());
            et_incubator_name.setText(eggBatch.getIncubatorName());
            et_number_of_eggs.setText(eggBatch.getNumberOfEggs().toString());
            et_location.setText(eggBatch.getLocation());
            if (eggBatch.getTrackingOption() == 1) {
                rb_track_entire_batch.setChecked(true);
                trackingOption = 1;
            } else if (eggBatch.getTrackingOption() == 2) {
                rb_track_specific_eggs.setChecked(true);
                trackingOption = 2;
            } else {
                trackingOption = 1; //Default
                rb_track_entire_batch.setChecked(true);
            }
            et_incubator_settings.setText(eggBatch.getIncubatorSettings().toString());
            et_temperature.setText(eggBatch.getTemperature().toString());
            et_incubation_days.setText(eggBatch.getIncubationDays().toString());
            et_number_of_eggs_hatched.setText(eggBatch.getNumberOfEggsHatched().toString());
            et_target_weight_loss.setText(eggBatch.getTargetWeightLoss().toString());
        } else {
            //et_batch_label.setText(eggBatch.getBatchLabel());
            //et_species_name.setText(eggBatch.getSpeciesName());
           // et_common_name.setText(eggBatch.getCommonName());
            //et_set_date.setText(eggBatch.getSetDate());
            //et_hatch_date.setText(eggBatch.getHatchDate());
            //et_incubator_name.setText(eggBatch.getIncubatorName());
            //et_number_of_eggs.setText(eggBatch.getNumberOfEggs().toString());
            //et_location.setText(eggBatch.getLocation());


           /* if (eggBatch.getTrackingOption() == 1) {
                rb_track_entire_batch.setChecked(true);
                trackingOption = 1;
            } else if (eggBatch.getTrackingOption() == 2) {
                rb_track_specific_eggs.setChecked(true);
                trackingOption = 2;
            } else {
                trackingOption = 1; //Default
                rb_track_entire_batch.setChecked(true);
            }*/


            //et_incubator_settings.setText(PREF_HUMIDITY_MEASURED_WITH);
            //et_temperature.setText(PREF_TEMPERATURE_ENTERED_IN);
            //et_incubation_days.setText(eggBatch.getIncubationDays().toString());
            //et_number_of_eggs_hatched.setText(eggBatch.getNumberOfEggsHatched().toString());

            //et_target_weight_loss.setText(PREF_DEFAULT_WEIGHT_LOSS_INTEGER.toString());

        }
        button_save_add_egg_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggBatch();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        et_set_date.setFocusable(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            default:
                try {
                    Common common = new Common();
                    common.menuOptions(item, getApplicationContext(), this);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
        }
    }

    private void checkIfEmptyField() {
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
            targetWeightLoss = Integer.parseInt(et_target_weight_loss.getText().toString());
        }
    }

    private void updateInsertEggBatch() {
        try {
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

                try {
                    eggWiseDatabse.getEggSettingDao().updateEggSetting((eggBatch));
                    setResult(eggBatch,13);
                } catch (Exception e) {
                    e.printStackTrace();
                }


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

                try {
                    new InsertTask(AddEggBatchActivity.this,eggBatch).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(this, "Field " + editText.getHint() + " is empty!", Toast.LENGTH_SHORT).show();
        return result;
    }


    Boolean validateRequiredFields () throws ParseException {

        if (!(et_batch_label.getText().toString().length() == 0)) {
            eggLabel = et_batch_label.getText().toString();
        } else {
            et_batch_label.setHint("Please enter Batch Label.");
            et_batch_label.setError("Batch Label is required.");
            et_batch_label.requestFocus();
            return false;
        }
        if (!(et_number_of_eggs.getText().toString().length() == 0)) {
            numberOfEggs = Integer.parseInt(et_number_of_eggs.getText().toString());
        } else {
            et_number_of_eggs.setHint("Please enter Number Of Eggs.");
            et_number_of_eggs.setError("Number Of Eggs is required.");
            et_number_of_eggs.requestFocus();
            return false;
        }
        if (!(et_target_weight_loss.getText().toString().length() == 0)) {
            targetWeightLoss = Integer.parseInt(et_target_weight_loss.getText().toString());
        } else {
            et_target_weight_loss.setHint("Please enter Target Weight Loss.");
            et_target_weight_loss.setError(" Target Weight Loss is required.");
            et_target_weight_loss.requestFocus();
            return false;
        }
        if (!(et_set_date.getText().toString().length() == 0)) {
            setDate = et_set_date.getText().toString();
        } else {
            et_set_date.setHint("Please enter Set Date.");
            et_set_date.setError("Set Date is required.");
            et_set_date.requestFocus();
            return false;
        }
        if (!(et_incubator_name.getText().toString().length() == 0)) {
            incubatorName = et_incubator_name.getText().toString();
        } else {
            et_incubator_name.setHint("Please enter Incubator Name.");
            et_incubator_name.setError("Incubator Name is required.");
            et_incubator_name.requestFocus();
            return false;
        }
        if (!(et_incubation_days.getText().toString().length() == 0)) {
            incubationDays = Integer.parseInt(et_incubation_days.getText().toString());
        } else {
            et_incubation_days.setHint("Please enter Incubation Days.");
            et_incubation_days.setError("Incubation Days is required.");
            et_incubation_days.requestFocus();
            return false;
        }

        checkIfEmptyField();

       /* hatchDate =  Common.blankIfNullString(et_hatch_date.getText().toString());
        speciesName =  Common.blankIfNullString(et_species_name.getText().toString());
        commonName =  Common.blankIfNullString(et_common_name.getText().toString());
        location =  Common.blankIfNullString(et_location.getText().toString());
        incubatorSettings =  Common.blankIfNullString(et_incubator_settings.getText().toString());
        numberOfEggsHatched =  Integer.parseInt(et_number_of_eggs_hatched.getText().toString());
        incubatorSettings =  et_incubator_settings.getText().toString();
        temperature =  Double.parseDouble(et_temperature.getText().toString());*/

        return true;
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
        //setResult(flag,new Intent().putExtra("eggBatch",eggBatch));
        //finish();
        Intent intent1 = new Intent(AddEggBatchActivity.this, EggBatchListActivity.class);
        intent1.putExtra("eggBatch", eggBatch);
        startActivity(intent1);
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
