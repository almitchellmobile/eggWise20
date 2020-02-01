package net.almitchellmobile.eggwise20;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

public class AddWeightLossActivity extends AppCompatActivity {

    private TextView text_batch_label,
            text_number_of_eggs_remaining,
            text_incubator_name,
            text_set_date;
    private AutoCompleteTextView et_egg_label,
            et_reading_date,
            et_reading_day_number,
            et_egg_weight,
            et_weight_loss_comment;
    private Button button_save_add_weight_loss, button_save_list_weight_loss, button_cancel;

    com.google.android.material.floatingactionbutton.FloatingActionButton fab_add_save_weight_loss;

    private EggWiseDatabse eggWiseDatabse;
    private EggDaily eggDaily;
    private EggBatch eggBatch;
    private boolean update;

    Long settingID = 0L;
    String batchLabel = "";
    String eggLabel = "";
    String readingDate = "";
    String setDate = "";
    Integer readingDayNumber = 0;
    Double eggWeight = 0.0D;
    Double eggWeightAverage = 0.0D;
    String eggDailyComment = "";
    String incubatorName = "";
    Integer numberOfEggsRemaining = 0;
    //Integer numberOfEggs = 0;
    Double eggWeightSum = 0D;
    Double actualWeightLossPercent = 0.0D;
    Double targetWeightLossPercent = 0.0D;
    Double weightLossDeviation = 0.0D;
    Integer incubationDays = 0;
    Double eggWeightAverageCurrent = 0.0D;
    Integer targetWeightLossInteger = 0;

    Common common = new Common();

    android.icu.util.Calendar myCalendar;
    String myFormat;
    android.icu.text.SimpleDateFormat sdf;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight_loss);
        Toolbar toolbar_add_weight_loss = findViewById(R.id.toolbar_add_weight_loss);
        setSupportActionBar(toolbar_add_weight_loss);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddWeightLossActivity.this);

        fab_add_save_weight_loss = findViewById(R.id.fab_add_save_weight_loss);
        fab_add_save_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggDaily();
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //fab_add_save_weight_loss.setVisibility(View.VISIBLE);

        text_batch_label = findViewById(R.id.text_batch_label);
        text_number_of_eggs_remaining = findViewById(R.id.text_number_of_eggs_remaining);
        text_incubator_name = findViewById(R.id.text_incubator_name);
        text_set_date = findViewById(R.id.text_set_date);

        et_egg_label = findViewById(R.id.et_egg_label);
        et_egg_weight = findViewById(R.id.et_egg_weight);
        et_reading_day_number = findViewById(R.id.et_reading_day_number);
        et_reading_day_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                    String input = setDate;

                    myCalendar = android.icu.util.Calendar.getInstance();
                    String myFormat = "MM/dd/yyyy"; //In which you need put here
                    sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);


                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( myFormat );
                    LocalDate localDate = LocalDate.parse( input , formatter );

                    //Calendar cal = Calendar.getInstance();

                    myCalendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
                    myCalendar.set(Calendar.MONTH, localDate.getMonthValue());
                    myCalendar.set(Calendar.YEAR, localDate.getYear());
                    myCalendar.add(Calendar.DAY_OF_MONTH, readingDayNumber);

                    readingDate = sdf.format(myCalendar.getTime());
                    et_reading_date.setText(sdf.format(myCalendar.getTime()));

                    System.out.println(readingDate);
                    System.out.println(et_reading_date.getText());
                }
            }

            /*@Override
            public void onFocusChange(View v, boolean hasFocus) {
                String input = setDate;

                myCalendar = android.icu.util.Calendar.getInstance();
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern( myFormat );
                LocalDate localDate = LocalDate.parse( input , formatter );

                //Calendar cal = Calendar.getInstance();

                myCalendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
                myCalendar.set(Calendar.MONTH, localDate.getMonthValue());
                myCalendar.set(Calendar.YEAR, localDate.getYear());
                myCalendar.add(Calendar.DAY_OF_MONTH, readingDayNumber);

                readingDate = sdf.format(myCalendar.getTime());
                et_reading_date.setText(sdf.format(myCalendar.getTime()));

                System.out.println(readingDate);
                System.out.println(et_reading_date.getText());

            }*/
        });
        /*et_reading_day_number.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    String input = setDate;

                    myCalendar = android.icu.util.Calendar.getInstance();
                    String myFormat = "MM/dd/yyyy"; //In which you need put here
                    sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);


                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( myFormat );
                    LocalDate localDate = LocalDate.parse( input , formatter );

                    //Calendar cal = Calendar.getInstance();

                    myCalendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
                    myCalendar.set(Calendar.MONTH, localDate.getMonthValue());
                    myCalendar.set(Calendar.YEAR, localDate.getYear());
                    myCalendar.add(Calendar.DAY_OF_MONTH, readingDayNumber);

                    readingDate = sdf.format(myCalendar.getTime());
                    et_reading_date.setText(sdf.format(myCalendar.getTime()));

                    System.out.println(readingDate);
                    System.out.println(et_reading_date.getText());

                    //readingDate = sdf.format(myCalendar.getTime())

                    //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    //Calendar c = Calendar.getInstance();
                    //c.add(Calendar.DATE, 5);

                }
                return false;
            }
        });*/

        DatePickerDialog.OnDateSetListener setDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelReadingDate();
                        //et_hatch_date.requestFocus();
                    }

                };

        et_reading_date = findViewById(R.id.et_reading_date);
        et_reading_date.setFocusable(true);
        et_reading_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(AddWeightLossActivity.this, setDateDatePickerDialog, myCalendar
                            .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                            myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        et_weight_loss_comment = findViewById(R.id.et_weight_loss_comment);


        button_save_list_weight_loss = findViewById(R.id.button_save_list_weight_loss);
        button_save_list_weight_loss.setText("Save");
        button_save_list_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validateRequiredFields()) {
                        updateInsertEggDaily();
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        button_save_add_weight_loss = findViewById(R.id.button_save_add_weight_loss);
        button_save_add_weight_loss.setText("Save / Next");
        button_save_add_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //common

                    //if(checkInputFields()) {
                        updateInsertEggDaily();
                    //}
                    //setResult(eggDaily,3);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                //finish();
                //Intent intent1 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
                //intent1.putExtra("WeightLossListActivity", eggBatch);
                //startActivity(intent1);
            }
        });
        button_save_add_weight_loss.setVisibility(View.GONE);

        button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setText("Return");
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateInsertEggDaily();

                Intent intent1 = new Intent(AddWeightLossActivity.this, EggBatchListActivity.class);
                //intent1.putExtra("WeightLossListActivity", eggBatch);
                startActivity(intent1);
            }
        });

        getEggBatchData();


        eggDaily = null;
        if (getIntent().getSerializableExtra("eggDailyAdd") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyAdd");
            update = false;
            button_save_add_weight_loss.setText("Save");
        } else if (getIntent().getSerializableExtra("eggDailyUpdate") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyUpdate");
            update = true;
            button_save_add_weight_loss.setText("Update");
        }
        if ( eggDaily!=null ){
            getSupportActionBar().setTitle("Update Egg Weight Batch: " + batchLabel);

            batchLabel = eggDaily.getBatchLabel();
            setDate = eggDaily.getSetDate();
            incubatorName = eggDaily.getIncubatorName();
            numberOfEggsRemaining = eggDaily.getNumberOfEggsRemaining();
            targetWeightLossInteger = eggDaily.getTargetWeightLossInteger();
            incubationDays = eggDaily.getIncubationDays();

            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggDaily.getBatchLabel()));
            text_set_date.setText(parseHTMLBold("<B>Set Date:</B> " + eggDaily.getSetDate()));
            text_incubator_name.setText(parseHTMLBold("<B>Incubator Name:</B> " + eggDaily.getIncubatorName()));
            text_number_of_eggs_remaining.setText(parseHTMLBold("<B>Number Of Eggs Remaining:</B> " + eggDaily.getNumberOfEggsRemaining()));

            if (update == true) {
                eggLabel = eggDaily.getEggLabel();
                readingDate = eggDaily.getReadingDate();
                readingDayNumber = eggDaily.getReadingDayNumber();
                eggWeight = eggDaily.getEggWeight();
                eggDailyComment = eggDaily.getEggDailyComment();

                et_egg_label.setText(eggLabel);
                et_reading_date.setText(readingDate);
                et_egg_weight.setText(eggWeight.toString());
                et_weight_loss_comment.setText(eggDailyComment);
            }

        }



    }

    private void getEggBatchData() {
        eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch");
        if ( eggBatch!=null ){

           //getSupportActionBar().setTitle("Update Egg Batch");
            //update = true;
            //button_save_add_weight_loss.setText("Update");

            batchLabel = eggBatch.getBatchLabel();
            getSupportActionBar().setTitle("Weight Loss Batch: " + batchLabel);

            setDate = eggBatch.getSetDate();
            incubatorName = eggBatch.getIncubatorName();
            numberOfEggsRemaining = eggBatch.getNumberOfEggs();
            targetWeightLossInteger = eggBatch.getTargetWeightLoss();
            incubationDays = eggBatch.getIncubationDays();
            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggBatch.getBatchLabel()));
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

    private void updateLabelReadingDate() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);
        readingDate = sdf.format(myCalendar.getTime());
        et_reading_date.setText(sdf.format(myCalendar.getTime()));
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
            //readingDayNumber = common.computeReadingDateNumber(setDate,et_reading_date.getText().toString());
            eggDaily.setReadingDayNumber(readingDayNumber);
            eggDaily.setEggDailyComment(eggDailyComment);

                eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));
                setResult(eggDaily,2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
            eggDaily = new EggDaily(settingID,
                    batchLabel,
                    eggLabel,
                    readingDate,
                    setDate,
                    readingDayNumber,
                    eggWeight,
                    eggWeightAverage,
                    eggDailyComment,
                    incubatorName,
                    numberOfEggsRemaining,

                    eggWeightSum,
                    actualWeightLossPercent,
                    targetWeightLossPercent,
                    weightLossDeviation,
                    eggWeightAverageCurrent,
                    targetWeightLossInteger,
                    incubationDays);


                new InsertTask(AddWeightLossActivity.this,eggDaily).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    private void setResult(EggDaily eggDaily, int flag){
        setResult(flag,new Intent().putExtra("eggDaily",eggDaily)
                .putExtra("eggBatch", eggBatch));
        finish();

        //if (flag == 1) {
            Intent intent1 = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
            intent1.putExtra("eggBatch", eggBatch);
            intent1.putExtra("eggDaily", eggDaily);
            startActivity(intent1);
        //} else {
        //    Intent intent2 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
        //    intent2.putExtra("eggBatch", eggBatch);
        //    intent2.putExtra("eggDaily", eggDaily);
        //}

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
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(eggDaily,1);
                //activityReference.get().finish();
            }

        }
    }

}
