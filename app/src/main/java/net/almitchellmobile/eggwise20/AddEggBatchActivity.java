package net.almitchellmobile.eggwise20;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.util.Common;
import net.almitchellmobile.eggwise20.util.SetDate;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class AddEggBatchActivity extends AppCompatActivity {

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    AutoCompleteTextView et_batch_label, et_number_of_eggs, et_species_name, et_common_name,
            et_incubator_name, et_set_date, et_hatch_date,
            et_location, et_incubator_settings, et_temperature, et_incubation_days,
            et_number_of_eggs_hatched, et_target_weight_loss;
    CalendarView cv_set_date, cv_hatch_date;
    Button btn_save_egg_batch, button_set_date_lookup, button_hatch_date_lookup;
    //com.google.android.material.floatingactionbutton.FloatingActionButton fab_add_save_egg_batch;

    RadioGroup rg_track_weight_loss;
    RadioButton rb_track_entire_batch;
    RadioButton rb_track_specific_eggs;

    public EggWiseDatabse eggWiseDatabse;
    MaterialTapTargetPrompt mFabPrompt;

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

    String[] incubatorSettingsValues = {"Humidity %", "Wet Bulb Reading"};
    ArrayAdapter<String> adapterIncubatorSettings;
    ArrayList<String> batchLabels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_egg_batch);
        Toolbar toolbar_add_egg_batch = findViewById(R.id.toolbar_add_egg_batch);
        setSupportActionBar(toolbar_add_egg_batch);
        getSupportActionBar().setTitle("Add Egg Batch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddEggBatchActivity.this);

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "Ounces");
        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
        Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));

        rl_egg_batch  = findViewById(R.id.rl_egg_batch);

        btn_save_egg_batch = findViewById(R.id.btn_save_egg_batch);
        btn_save_egg_batch.setOnClickListener(new View.OnClickListener() {
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

        DatePickerDialog.OnDateSetListener getSetDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateSetDate();
                    }
                };
        button_set_date_lookup  = (Button) findViewById(R.id.button_set_date_lookup);
        button_set_date_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEggBatchActivity.this, getSetDatePickerDialog, myCalendar
                        .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                        myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener getHatchDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateHatchDate();
                    }
                };
        button_hatch_date_lookup  = (Button) findViewById(R.id.button_hatch_date_lookup);
        button_hatch_date_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEggBatchActivity.this, getHatchDatePickerDialog, myCalendar
                        .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                        myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();
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

        et_batch_label.setInputType(InputType.TYPE_CLASS_TEXT);
        et_species_name.setInputType(InputType.TYPE_CLASS_TEXT);
        et_common_name.setInputType(InputType.TYPE_CLASS_TEXT);
        et_incubator_name.setInputType(InputType.TYPE_CLASS_TEXT);
        et_number_of_eggs.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_set_date.setInputType(InputType.TYPE_CLASS_TEXT);
        et_hatch_date.setInputType(InputType.TYPE_CLASS_TEXT);
        et_location.setInputType(InputType.TYPE_CLASS_TEXT);

        rg_track_weight_loss = findViewById(R.id.rg_track_weight_loss);
        rg_track_weight_loss.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_track_entire_batch:
                        trackingOption = 1; //Track entire batch
                        break;
                    case R.id.rb_track_specific_eggs:
                        trackingOption = 2; //Track specific eggs
                        break;
                }
            }
        });
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
        et_incubator_settings.setTextColor(Color.BLACK);
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

        btn_save_egg_batch = findViewById(R.id.btn_save_egg_batch);
        btn_save_egg_batch.setOnClickListener(new View.OnClickListener() {
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
        //fab_add_save_egg_batch.setVisibility(View.GONE);

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







        /*rg_track_weight_loss.setOnClickListener(new View.OnClickListener() {
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

        });*/


        if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_ADD_BATCH_1", false)) {
                // The user hasn't seen the OnboardingSupportFragment yet, so show it
            //showFabPrompt();
            showSequenceManual();
            editor.putBoolean("COMPLETED_ONBOARDING_PREF_ADD_BATCH_1", true);
            editor.commit();
        }

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private SpannableStringBuilder setSecondarytText(String secondaryText, Integer textStart, Integer textEnd) {
        ColorStateList oldColors =    et_hatch_date.getTextColors(); //save original colors
        SpannableStringBuilder secondaryText1 = new SpannableStringBuilder(
                secondaryText);
        ForegroundColorSpan foregroundColour1 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText1.setSpan(oldColors, textStart, textEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return secondaryText1;
    }

    public void showSequenceManual() {

        /*SpannableStringBuilder secondaryText = new SpannableStringBuilder(
                "Begin here by entering your egg batch details. Tap here to continue.");
        ForegroundColorSpan foregroundColour = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText.setSpan(foregroundColour, 51, 56, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText2 = new SpannableStringBuilder(
                "After entering your egg batch details, tap the save button to save your batch. Tap here to continue.");
        ForegroundColorSpan foregroundColour2 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText2.setSpan(foregroundColour2, 83, 87, Spanned.SPAN_INCLUSIVE_INCLUSIVE);*/


        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddEggBatchActivity.this)
                        .setTarget(findViewById(R.id.et_batch_label))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPrimaryText("Step 1 - Add Egg Batch")
                        .setSecondaryText(setSecondarytText("Begin here by entering your egg batch details. Tap here to continue.", 51, 56))
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddEggBatchActivity.this)
                        .setTarget(findViewById(R.id.btn_save_egg_batch))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Step 2 - Save Egg Batch")
                        .setSecondaryText(setSecondarytText("After entering your egg batch details, tap the save button to save your batch. Tap here to continue.", 83, 87))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .show();
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
            case android.R.id.home:
                Intent intent = new Intent(AddEggBatchActivity.this, EggBatchListActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.send_feedback:
                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, Common.getExtraText(this));
                startActivity(Intent.createChooser(_Intent, "Send feedback"));
                return true;

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
        speciesName = Common.blankIfNullString(et_species_name.getText().toString());
        commonName = Common.blankIfNullString(et_common_name.getText().toString());
        hatchDate = Common.blankIfNullString(et_hatch_date.getText().toString());
        incubatorName = Common.blankIfNullString(et_incubator_name.getText().toString());
        location = Common.blankIfNullString(et_location.getText().toString());
        //numberOfEggs = Common.zeroInteger_IfNullString(et_number_of_eggs.getText().toString());
        incubatorSettings = Common.blankIfNullString(et_incubator_settings.getText().toString());
        temperature = Common.zeroDouble_IfNullString(et_temperature.getText().toString());
        //incubationDays = Integer.parseInt(et_incubation_days.getText().toString());
        numberOfEggsHatched = Common.zeroInteger_IfNullString(et_number_of_eggs_hatched.getText().toString());
        //targetWeightLoss = Integer.parseInt(et_target_weight_loss.getText().toString());
    }

    private void updateSetDate() {

        myFormat = "MM/dd/yyyy"; //In which you need put here
        sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);
        //try {
            setDate = sdf.format(myCalendar.getTime());
            et_set_date.setText(sdf.format(myCalendar.getTime()));

            //readingDayNumber = Common.computeReadingDateNumber(setDate,et_reading_date.getText().toString());
            //et_reading_day_number.setText(readingDayNumber.toString());

        //} catch (ParseException e) {
        //    e.printStackTrace();
        //}
    }

    private void updateHatchDate() {

        myFormat = "MM/dd/yyyy"; //In which you need put here
        sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);
            hatchDate = sdf.format(myCalendar.getTime());
            et_hatch_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateInsertEggBatch() {
        try {
            if (update){
                eggBatch.setBatchLabel(batchLabel);
                eggBatch.setSpeciesName(speciesName);
                eggBatch.setCommonName(commonName);
                eggBatch.setSpeciesID(0L);
                eggBatch.setLayDate(layDate);
                eggBatch.setSetDate(setDate);
                eggBatch.setHatchDate(hatchDate);
                eggBatch.setIncubatorID(0L);
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

    /*private boolean isEmptyField (AutoCompleteTextView autoCompleteTextView){
        boolean result = autoCompleteTextView.getText().toString().length() <= 0;
        com.google.android.material.textfield.TextInputLayout parent = (com.google.android.material.textfield.TextInputLayout) autoCompleteTextView.getParent();
        String hint = parent.getHint().toString();
        if (result)
            Toast.makeText(this, "Field " + hint + " is empty!", Toast.LENGTH_SHORT).show();
        return result;
    }*/


    Boolean validateRequiredFields () throws ParseException {

        if (!(et_batch_label.getText().toString().length() == 0)) {
            batchLabel = et_batch_label.getText().toString();
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
