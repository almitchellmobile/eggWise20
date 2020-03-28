package net.almitchellmobile.eggwise20;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static android.util.TypedValue.TYPE_NULL;

public class AddWeightLossActivity extends AppCompatActivity {

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";


    private TextView text_number_of_eggs_remaining,
            text_incubator_name,
            text_set_date, text_batch_label;
    private AutoCompleteTextView et_egg_label,
            et_reading_date,
            et_reading_day_number,
            et_egg_weight,
            et_weight_loss_comment, et_batch_label_add_egg_weight;
    private Button button_save_add_weight_loss,
            button_save_list_weight_loss,
            button_cancel_list_batch,
            button_cancel_list_eggs,
            button_reading_date_lookup;

    ArrayAdapter<String> adapterBatchLabel;
    ArrayList<String> arrayListBatchLabel = new ArrayList<>();

    com.google.android.material.floatingactionbutton.FloatingActionButton fab_add_save_weight_loss,
            fab_reading_date_lookup;

    MaterialTapTargetPrompt mFabPrompt;
    private EggWiseDatabse eggWiseDatabse;
    private EggDaily eggDaily;
    private EggBatch eggBatch;
    private boolean update;
    private String hintStringValue = "";

    SpannableStringBuilder secondaryText;
    ForegroundColorSpan foregroundColorSpan;

    Long eggDailyID = 0L;
    Long settingID = 0L;
    String batchLabel = "";
    Long batchID = 0L;
    Long eggBatchID = 0L;
    String eggLabel = "";
    String readingDate = "";
    String setDate = "";
    Integer readingDayNumber = 0;
    Double eggWeight = 0.0D;
    Double eggWeightAverage = 0.0D;
    String eggDailyComment = "";
    String incubatorName = "";
    Integer numberOfEggsRemaining = 0;
    Boolean insertAddNext = true;;
    Double eggWeightSum = 0D;
    Double actualWeightLossPercent = 0.0D;
    Double targetWeightLossPercent = 0.0D;
    Double weightLossDeviation = 0.0D;
    Integer incubationDays = 0;
    Double eggWeightAverageCurrent = 0.0D;
    Double eggWeightAverageDay0 = 0.0D;
    Integer targetWeightLossInteger = 0;
    Integer rejectedEgg = 0;  // 0= false, 1 = true

    Common common = new Common();

    android.icu.util.Calendar myCalendar;
    String myFormat;
    android.icu.text.SimpleDateFormat sdf;;

    DatePickerDialog.OnDateSetListener readingDateDatePickerDialog;
    LinearLayout ll_egg_weight_buttons;
    LinearLayout ll_add_egg_weight;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight_loss);
        Toolbar toolbar_add_weight_loss = findViewById(R.id.toolbar_add_weight_loss);
        setSupportActionBar(toolbar_add_weight_loss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddWeightLossActivity.this);
        myCalendar = Calendar.getInstance();

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

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

        /*ll_add_egg_weight = (LinearLayout) findViewById(R.id.ll_add_egg_weight);

        ll_egg_weight_buttons =  (LinearLayout) findViewById(R.id.ll_egg_weight_buttons);
        if (isTablet(AddWeightLossActivity.this)) {
            ll_egg_weight_buttons.setOrientation(LinearLayout.VERTICAL);
        } else {
            ll_egg_weight_buttons.setOrientation(LinearLayout.HORIZONTAL);
        }*/


        text_number_of_eggs_remaining = findViewById(R.id.text_number_of_eggs_remaining);
        text_incubator_name = findViewById(R.id.text_incubator_name);
        text_set_date = findViewById(R.id.text_set_date);
        text_batch_label = findViewById(R.id.text_batch_label);
        et_egg_label = findViewById(R.id.et_egg_label);
        et_egg_weight = findViewById(R.id.et_egg_weight);
        if (Common.PREF_WEIGHT_ENTERED_IN.toString().trim().length() != 0) {
            hintStringValue = "Egg Weight (" + Common.PREF_WEIGHT_ENTERED_IN + ")";
            et_egg_weight.setHint(hintStringValue);
        }



        button_reading_date_lookup = findViewById(R.id.button_reading_date_lookup);
        button_reading_date_lookup.setText("Set Reading Date");

        et_reading_day_number = findViewById(R.id.et_reading_day_number);
        et_reading_date = findViewById(R.id.et_reading_date);
        et_weight_loss_comment = findViewById(R.id.et_weight_loss_comment);

        /*et_weight_loss_comment.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", false)) {
                        // The user hasn't seen the OnboardingSupportFragment yet, so show it
                        showFabPrompt();
                        editor.putBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", true);
                        editor.commit();
                    }
                }
                return false;
            }
        });*/

        et_egg_label.setSelectAllOnFocus(true);
        et_reading_date.setSelectAllOnFocus(true);
        et_reading_day_number.setSelectAllOnFocus(true);
        et_egg_weight.setSelectAllOnFocus(true);
        et_weight_loss_comment.setSelectAllOnFocus(true);

        et_egg_label.setInputType(InputType.TYPE_CLASS_TEXT);
        et_reading_day_number.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_reading_date.setInputType(InputType.TYPE_CLASS_TEXT);
        et_egg_weight.setInputType(InputType.TYPE_CLASS_TEXT);
        et_weight_loss_comment.setInputType(InputType.TYPE_CLASS_TEXT);


        fab_add_save_weight_loss = findViewById(R.id.fab_add_save_weight_loss);
        fab_add_save_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggDaily();
                        setResult(eggDaily,"insert_add_next");

                        /*Intent intent1 = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
                        intent1.putExtra("eggBatch", eggBatch);
                        intent1.putExtra("eggDaily", eggDaily);
                        startActivity(intent1);*/
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        fab_add_save_weight_loss.setVisibility(View.GONE);


        DatePickerDialog.OnDateSetListener getDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelReadingDate_ReadingDayNumber();
                    }
                };

        button_reading_date_lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddWeightLossActivity.this, getDateDatePickerDialog, myCalendar
                        .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                        myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();            }
        });

        et_reading_day_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    //Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    if (!(et_reading_day_number.getText().toString().length() == 0)) {
                        //Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                        try {
                            readingDayNumber = Integer.parseInt(et_reading_day_number.getText().toString());

                            String myFormat = "MM/dd/yyyy";
                            sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);

                            Calendar calendar = Calendar.getInstance();

                            try {
                                calendar.setTime(sdf.parse(setDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            calendar.add(Calendar.DATE, Integer.parseInt(et_reading_day_number.getText().toString()));

                            SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat);
                            readingDate = sdf1.format(calendar.getTime());
                            et_reading_date.setText(readingDate);

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }


        });

        DatePickerDialog.OnDateSetListener readingDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelReadingDate_ReadingDayNumber();
                    }
                };
        et_reading_date.setFocusable(true);
        et_reading_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(AddWeightLossActivity.this, readingDateDatePickerDialog, myCalendar
                            .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                            myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        button_save_list_weight_loss = findViewById(R.id.button_save_list_weight_loss);
        button_save_list_weight_loss.setText("Save/List Eggs");
        button_save_list_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggDaily();
                        setResult( eggDaily, "insert_or_update_list");
                        //Toast.makeText(AddWeightLossActivity.this, "Egg Label:  " + et_egg_label.getText().toString()  + " Saved!", Toast.LENGTH_SHORT).show();

                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        button_save_add_weight_loss = findViewById(R.id.button_save_add_weight_loss);
        button_save_add_weight_loss.setText("Save/Next");
        button_save_add_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggDaily();
                        setResult(eggDaily,"insert_add_next");

                   /* Intent intent1 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
                    intent1.putExtra("eggBatch", eggBatch);
                    intent1.putExtra("eggDailyAdd", eggDaily);
                    startActivity(intent1);*/
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //button_save_add_weight_loss.setVisibility(View.GONE);

        button_cancel_list_batch = findViewById(R.id.button_cancel_list_batch);
        button_cancel_list_batch.setText("Cancel/List Batch");
        button_cancel_list_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AddWeightLossActivity.this, EggBatchListActivity.class);
                startActivity(intent1);
            }
        });

        button_cancel_list_eggs = findViewById(R.id.button_cancel_list_eggs);
        button_cancel_list_eggs.setText("Cancel/List Eggs");
        button_cancel_list_eggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
                intent1.putExtra("eggBatch", eggBatch);
                intent1.putExtra("eggDaily", eggDaily);
                startActivity(intent1);
            }
        });




        getEggBatchData();

        eggDaily = null;
        if (getIntent().getSerializableExtra("eggDailyAdd") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyAdd");
            update = false;
            getSupportActionBar().setTitle("Add Egg Weight for Batch: " + batchLabel);
            eggBatchID = eggDaily.getEggBatchID();
            et_egg_label.setText(eggDaily.getEggLabel());
            et_reading_date.setText(eggDaily.getReadingDate());
            et_reading_day_number.setText(eggDaily.getReadingDayNumber().toString());
            readingDayNumber = eggDaily.getReadingDayNumber();
            et_egg_weight.setText(eggDaily.getEggWeight().toString());
            et_weight_loss_comment.setText(eggDaily.eggDailyComment);
            button_save_add_weight_loss.setText("Save/Add Next");
        } else if (getIntent().getSerializableExtra("eggDailyUpdate") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyUpdate");
            update = true;
            getSupportActionBar().setTitle("Update Egg Weight for Batch: " + batchLabel);
            eggLabel = eggDaily.getEggLabel();
            eggBatchID = eggDaily.getEggBatchID();
            readingDate = eggDaily.getReadingDate();
            readingDayNumber = eggDaily.getReadingDayNumber();
            eggWeight = eggDaily.getEggWeight();
            eggDailyComment = eggDaily.getEggDailyComment();
            et_egg_label.setText(eggLabel);
            et_reading_date.setText(readingDate);
            et_reading_day_number.setText(readingDayNumber.toString());
            et_egg_weight.setText(eggWeight.toString());
            et_weight_loss_comment.setText(eggDailyComment);
            button_save_add_weight_loss.setText("Update/Add Next");
        }
        if ( eggDaily!=null ){
            //getSupportActionBar().setTitle("Update Egg Weight for Batch: " + batchLabel);

            batchLabel = eggDaily.getBatchLabel();
            eggBatchID = eggDaily.getEggBatchID();
            setDate = eggDaily.getSetDate();
            incubatorName = eggDaily.getIncubatorName();
            numberOfEggsRemaining = eggDaily.getNumberOfEggsRemaining();
            targetWeightLossInteger = eggDaily.getTargetWeightLossInteger();
            incubationDays = eggDaily.getIncubationDays();
            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggDaily.getBatchLabel()));
            //et_batch_label_add_egg_weight.setText(eggDaily.getBatchLabel());
            text_set_date.setText(parseHTMLBold("<B>Set Date:</B> " + eggDaily.getSetDate()));
            text_incubator_name.setText(parseHTMLBold("<B>Incubator Name:</B> " + eggDaily.getIncubatorName()));
            text_number_of_eggs_remaining.setText(parseHTMLBold("<B>Number Of Eggs Remaining:</B> " + eggDaily.getNumberOfEggsRemaining()));

        }

        //showReadingDayPrompt();
        //showSequenceManual(et_reading_day_number);

        /*if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            showSequenceManual(et_reading_day_number);
            editor.putBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", true);
            editor.commit();
        }*/
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void showSequenceManual(View view) {

        //((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
        //        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        et_reading_day_number.setInputType(TYPE_NULL);
        et_reading_date.setInputType(TYPE_NULL);
        et_egg_weight.setInputType(TYPE_NULL);
        et_egg_label.setInputType(TYPE_NULL);
        et_weight_loss_comment.setInputType(TYPE_NULL);

        SpannableStringBuilder secondaryText1 = new SpannableStringBuilder(
                "Tap this button to set the reading date and the reading day. Tap here to continue.");
        ForegroundColorSpan foregroundColour1 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText1.setSpan(foregroundColour1, 65, 69, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText2 = new SpannableStringBuilder(
                "Or enter the reading day and the reading date will be calculated when you tap the egg weight field. Tap here to continue.");
        ForegroundColorSpan foregroundColour2 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText2.setSpan(foregroundColour2, 104, 108, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText3 = new SpannableStringBuilder(
                "Enter an egg label. Tap here to continue.");
        ForegroundColorSpan foregroundColour3 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText3.setSpan(foregroundColour3, 24, 28, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText4 = new SpannableStringBuilder(
                "Enter the egg weight. Tap here to continue.");
        ForegroundColorSpan foregroundColour4 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText4.setSpan(foregroundColour4, 26, 30, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText5 = new SpannableStringBuilder(
                "Enter an optional comment. Tap here to continue.");
        ForegroundColorSpan foregroundColour5 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText5.setSpan(foregroundColour5, 31, 35, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText6 = new SpannableStringBuilder(
                "Tap one of the save buttons, to save your egg weight information. Tap here to continue.");
        ForegroundColorSpan foregroundColour6 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText6.setSpan(foregroundColour6, 70, 74, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableStringBuilder secondaryText7 = new SpannableStringBuilder(
                "Tap one of the cancel buttons, to cancel and return to the batch list or egg list. Tap here to finish.");
        ForegroundColorSpan foregroundColour7 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText7.setSpan(foregroundColour7, 87, 91, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.button_reading_date_lookup))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Set Reading Date")
                        .setFocalPadding(R.dimen.dp40)
                        .setSecondaryText(secondaryText1)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.et_reading_day_number))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Enter Reading Day")
                        .setSecondaryText(secondaryText2)
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.et_egg_label))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Enter Egg Label")
                        .setSecondaryText(secondaryText3)
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.et_egg_weight))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Enter Egg Weight")
                        .setSecondaryText(secondaryText4)
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.et_weight_loss_comment))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Enter Comment")
                        .setSecondaryText(secondaryText5)
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.button_save_add_weight_loss))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Save Details")
                        .setSecondaryText(secondaryText6)
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.button_cancel_list_eggs))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText(" or Cancel and Return")
                        .setSecondaryText(secondaryText7)
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPromptStateChangeListener((prompt, state) -> {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                            {
                                //mFabPrompt = null;
                                //Do something such as storing a value so that this prompt is never shown again
                                //et_reading_day_number.setInputType(InputType.TYPE_CLASS_TEXT);
                                //et_egg_weight.setInputType(InputType.TYPE_CLASS_TEXT);
                                ((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                                et_reading_day_number.setInputType(InputType.TYPE_CLASS_NUMBER);
                                et_reading_date.setInputType(InputType.TYPE_CLASS_TEXT);
                                et_egg_weight.setInputType(InputType.TYPE_CLASS_NUMBER);
                                et_egg_label.setInputType(InputType.TYPE_CLASS_TEXT);
                                et_weight_loss_comment.setInputType(InputType.TYPE_CLASS_TEXT);
                                button_reading_date_lookup.setFocusable(true);
                            }
                        })
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .show();
        //et_reading_day_number.setShowSoftInputOnFocus(true);
        //et_egg_weight.setShowSoftInputOnFocus(true);

    }

    public void showSequence(View view) {

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.et_reading_day_number))
                        .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                        .setPrimaryText("Step 1")
                        .setSecondaryText("Enter your egg weight details.")
                        .setFocalPadding(R.dimen.dp40)
                        .create(), 8000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.button_save_add_weight_loss))
                        .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                        .setPrimaryText("Step 2")
                        .setSecondaryText("Tap one of the save buttons, to save your egg information.")
                        .setFocalPadding(R.dimen.dp40)
                        .create(), 8000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                        .setTarget(findViewById(R.id.button_cancel_list_eggs))
                        .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                        .setPrimaryText(" or Cancel and Return")
                        .setSecondaryText("Tap one of the cancel buttons, to return to batch list or egg list.")
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create(), 8000)
                .show();
    }

    public void showReadingDayPrompt()
    {
        if (mFabPrompt != null)
        {
            return;
        }
        ((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        mFabPrompt = new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                .setTarget(findViewById(R.id.et_reading_day_number))
                .setFocalPadding(R.dimen.dp40)
                .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                .setPrimaryText("Enter Reading Day")
                .setSecondaryText("Enter the reading day and the reading date will be calculated from the Set Date when you tap the egg weight field. Tap the circle to continue.")
                .setBackButtonDismissEnabled(true)
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                            || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                    {
                        mFabPrompt = null;
                        ((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                                .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        showWeightPrompt();
                    }
                })
                .create();
        if (mFabPrompt != null)
        {
            mFabPrompt.show();
        }
    }

    public void showWeightPrompt()
    {
        if (mFabPrompt != null)
        {
            return;
        }
        ((InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        mFabPrompt = new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                .setTarget(findViewById(R.id.et_egg_weight))
                .setFocalPadding(R.dimen.dp40)
                .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                .setPrimaryText("Enter Egg Weight")
                .setSecondaryText("Enter the egg weight and optional comment. Tap the circle to continue.")
                .setBackButtonDismissEnabled(true)
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                            || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                    {
                        mFabPrompt = null;
                        //Do something such as storing a value so that this prompt is never shown again
                    }
                })
                .create();
        if (mFabPrompt != null)
        {
            mFabPrompt.show();
        }
    }

    public void showFabPrompt()
    {
        if (mFabPrompt != null)
        {
            return;
        }
        SpannableStringBuilder secondaryText = new SpannableStringBuilder("Enter your egg weight details and tap the save button to save your egg information.");
        secondaryText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 0, 30, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder primaryText = new SpannableStringBuilder("Save your egg.");
        //primaryText.setSpan(new BackgroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 0, 40, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mFabPrompt = new MaterialTapTargetPrompt.Builder(AddWeightLossActivity.this)
                .setTarget(findViewById(R.id.fab_add_save_weight_loss))
                .setFocalPadding(R.dimen.dp40)
                .setBackgroundColour(getResources().getColor(R.color.colorAccent))
                .setPrimaryText(primaryText)
                .setSecondaryText(secondaryText)
                .setBackButtonDismissEnabled(true)
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                            || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                    {
                        mFabPrompt = null;
                        //Do something such as storing a value so that this prompt is never shown again
                    }
                })
                .create();
        if (mFabPrompt != null)
        {
            mFabPrompt.show();
        }
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
                Intent intent = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
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

    private void getEggBatchData() {
        eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch");
        if ( eggBatch!=null ){

           //getSupportActionBar().setTitle("Update Egg Batch");
            //update = true;
            //button_save_add_weight_loss.setText("Update");

            batchLabel = eggBatch.getBatchLabel();
            eggBatchID = eggBatch.getEggBatchID();
            //getSupportActionBar().setTitle("Add Egg Weight for Batch: " + batchLabel);

            setDate = eggBatch.getSetDate();
            incubatorName = eggBatch.getIncubatorName();
            numberOfEggsRemaining = eggBatch.getNumberOfEggs();
            targetWeightLossInteger = eggBatch.getTargetWeightLoss();
            incubationDays = eggBatch.getIncubationDays();
            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggBatch.getBatchLabel()));
            //et_batch_label_add_egg_weight.setText(parseHTMLBold("<B>Batch Label:</B> " + eggBatch.getBatchLabel()));
            text_set_date.setText(parseHTMLBold("<B>Set Date:</B> " + eggBatch.getSetDate()));
            text_incubator_name.setText(parseHTMLBold("<B>Incubator Name:</B> " + eggBatch.getIncubatorName()));
            text_number_of_eggs_remaining.setText(parseHTMLBold("<B>Number Of Eggs Remaining:</B> " + eggBatch.getNumberOfEggs()));
        }
    }

    Boolean validateRequiredFields () throws ParseException {

        if (!(et_egg_label.getText().toString().length() == 0)) {
            eggLabel = et_egg_label.getText().toString();
        } else {
            et_egg_label.setHint("Please enter Egg Label.");
            et_egg_label.setError("Egg Label is required.");
            et_egg_label.requestFocus();
            return false;
        }
        if (!(et_egg_weight.getText().toString().length() == 0)) {
            eggWeight = Double.parseDouble(et_egg_weight.getText().toString());
        } else {
            et_egg_weight.setHint("Please enter Egg Weight.");
            et_egg_weight.setError("Egg Weight is required.");
            et_egg_weight.requestFocus();
            return false;
        }
        if (!(et_reading_date.getText().toString().length() == 0)) {
            readingDate = et_reading_date.getText().toString();
            readingDayNumber = common.computeReadingDateNumber(setDate,et_reading_date.getText().toString());
        } else {
            et_reading_date.setHint("Please enter Reading Date.");
            et_reading_date.setError("Reading Date is required.");
            et_reading_date.requestFocus();
            return false;
        }
        eggDailyComment =  common.blankIfNullString(et_weight_loss_comment.getText().toString());

        return true;
    }

    public boolean checkIsRequiredFieldMissing (EditText editText, Context context){
        boolean result = editText.getText().toString().length() == 0;
        if (result) {
            Toast.makeText(context, "Field " + editText.getHint() + " is a required field!", Toast.LENGTH_SHORT).show();
            editText.setError("Reguired field!");
            editText.requestFocus();
        }
        return result;
    }

    public boolean checkRequiredFieldNumber (EditText editText, Context context){
        boolean result = editText.getText().toString().length() <= 0;
        if (result) {
            Toast.makeText(context, "Field " + editText.getHint() + " is a required field!", Toast.LENGTH_SHORT).show();
            editText.requestFocus();
        }
        return result;
    }


    private CharSequence parseHTMLBold(String InputString) {
        return  HtmlCompat.fromHtml(InputString, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    private void updateLabelReadingDate_ReadingDayNumber() {

        myFormat = "MM/dd/yyyy"; //In which you need put here
        sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);
        try {
            readingDate = sdf.format(myCalendar.getTime());
            et_reading_date.setText(sdf.format(myCalendar.getTime()));

            readingDayNumber = Common.computeReadingDateNumber(setDate,et_reading_date.getText().toString());
            et_reading_day_number.setText(readingDayNumber.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateInsertEggDaily() throws java.text.ParseException {

        if (update){
            try {
                eggDaily.setBatchLabel(batchLabel);
                eggDaily.setSetDate(setDate);
                eggDaily.setIncubatorName(incubatorName);

                eggDaily.setNumberOfEggsRemaining(numberOfEggsRemaining);
                eggDaily.setIncubationDays(incubationDays);
                eggDaily.setTargetWeightLossInteger(targetWeightLossInteger);

                eggDaily.setEggLabel(eggLabel);
                eggDaily.setEggWeight(eggWeight);
                eggDaily.setReadingDate(readingDate);
                eggDaily.setReadingDayNumber(readingDayNumber);
                eggDaily.setEggDailyComment(eggDailyComment);

                    eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));
                    //setResult(eggDaily,2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }else {
            try {
            eggDaily = new EggDaily(settingID, eggBatchID, batchLabel, eggLabel, readingDate,
                    setDate, readingDayNumber, eggWeight, eggWeightAverageDay0, eggDailyComment,
                    incubatorName, numberOfEggsRemaining, eggWeightSum, actualWeightLossPercent,
                    targetWeightLossPercent, weightLossDeviation, eggWeightAverageCurrent,
                    targetWeightLossInteger, incubationDays, rejectedEgg
            );

                new InsertTask(AddWeightLossActivity.this,eggDaily).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        et_egg_label.setSelectAllOnFocus(true);
        et_reading_date.setSelectAllOnFocus(true);
        et_reading_day_number.setSelectAllOnFocus(true);
        et_egg_weight.setSelectAllOnFocus(true);
        et_weight_loss_comment.setSelectAllOnFocus(true);
    }


    private void setResult(EggDaily eggDaily, String flag){
        //System.out.println("eggDaily: " + eggDaily.toString());
        //setResult(flag,new Intent().putExtra("eggDaily",eggDaily)
        //        .putExtra("eggBatch", eggBatch));

        System.out.println("eggDaily: " + eggDaily.toString());
        //finish();



        if (flag.equalsIgnoreCase("insert_add_next")) { //Add record
            Intent intent2 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
            intent2.putExtra("eggBatch", eggBatch);
            intent2.putExtra("eggDailyAdd", eggDaily);
            startActivity(intent2);

        } else if (flag.equalsIgnoreCase("insert_or_update_list")){ // Update record
            Intent intent1 = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
            intent1.putExtra("eggBatch", eggBatch);
            intent1.putExtra("eggDaily", eggDaily);
            startActivity(intent1);
        }


    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddWeightLossActivity> activityReference;
        private EggDaily eggDaily;



        // only retain a weak reference to the activity
        InsertTask(AddWeightLossActivity context, EggDaily eggDaily) {
            activityReference = new WeakReference<>(context);
            this.eggDaily = eggDaily;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eggWiseDatabse.getEggDailyDao().insertEggDaily(eggDaily);
            eggDaily.setEggDailyID(j);
            Log.e("ID ", "doInBackground: "+j );
            //System.out.println("eggDaily: " + eggDaily.toString());
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                //activityReference.get().setResult(eggDaily,1);
                activityReference.get().finish();
            }

        }
    }

}
