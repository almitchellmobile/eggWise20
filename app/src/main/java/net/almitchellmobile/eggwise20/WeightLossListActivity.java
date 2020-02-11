package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggWeightLossAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeightLossListActivity extends AppCompatActivity implements EggWeightLossAdapter.OnEggWeightItemClick {

    SharedPreferences sharedpreferences;
    public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;

    public static final String mypreference = "mypref";

    public static String BATCH_LABEL = "";
    public static String EGG_LABEL = "";
    public static Double EGG_WEIGHT_SUM = 0.0;
    public static Double EGG_WEIGHT_AVG_CURRENT = 0.0;
    public static Double EGG_WEIGHT_AVG_DAY_0 = 0.0;
    public static Double ACTUAL_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double TARGET_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double WEIGHT_LOSS_DEVIATION = 0.0;
    public static String WEIGHT_LOSS_DEVIATION_MESSAGE = "";
    public static Integer TRACKING_OPTION = 0;
    public static Integer READING_DAY_NUMBER = 0;
    public static Integer TARGET_WEIGHT_LOSS_INTEGER = 0;
    public static Integer INCUBATION_DAYS = 0;
    public static Integer NUMBER_OF_EGGS_REMAINING = 0 ;
    public static DecimalFormat df = new DecimalFormat("00.00");

    FloatingActionButton fab_weight_loss;
    private TextView tv_empty_weight_loss_message;
    private RecyclerView recyclerViewWeightLossList;
    private EggWiseDatabse eggWiseDatabse;
    EggBatch eggBatch;
    private List<EggDaily> eggDailyList;
    private EggWeightLossAdapter eggWeightLossAdapter;
    private int pos;

    public Long settingID = 0L;
    public EggDaily eggDaily = null;

    public String eggLabel = "";
    public String readingDate = "";
    public String setDate = "";
    public Integer readingDayNumber = 0;
    public Double eggWeight = 0.0D;
    public Double eggWeightAverage = 0.0D;
    public String eggDailyComment = "";
    public String incubatorName = "";
    public Integer numberOfEggsRemaining = 0;
    public Integer numberOfEggs = 0;

    Common common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_list);
        Toolbar toolbar_weight_loss_list = findViewById(R.id.toolbar_weight_loss_list);
        setSupportActionBar(toolbar_weight_loss_list);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "@string/rb_value_celsius");
        PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "@string/rb_value_wet_bulb_readings");
        PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "@string/rb_value_grams");
        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        } else {
            PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 0;
        }
        if (sharedpreferences.contains("default_weight_loss_percentage")) {
            PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F);
        } else {
            PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 0.0F;
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F);
        } else {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
        }

        common = new Common();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            BATCH_LABEL = eggBatch.getBatchLabel();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            getSupportActionBar().setTitle("Weight Loss List Batch: " + BATCH_LABEL);
        } else {
            getSupportActionBar().setTitle("Weight Loss List");
        }
        initializeViews();
        displayList();
    }

    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossListActivity.this);
        new WeightLossListActivity.RetrieveTask(this).execute();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_empty_weight_loss_message =  (TextView) findViewById(R.id.tv_empty_weight_loss_message);
        fab_weight_loss = (FloatingActionButton) findViewById(R.id.fab_add_weight_loss);
        fab_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(WeightLossListActivity.this,
                        AddWeightLossActivity.class).putExtra("eggDailyAdd", eggDailyList.get(0)),100);
            }
        });

        recyclerViewWeightLossList = findViewById(R.id.recycler_view_weight_loss_list);
        recyclerViewWeightLossList.setLayoutManager(new LinearLayoutManager(WeightLossListActivity.this));
        eggDailyList = new ArrayList<>();
        eggWeightLossAdapter = new EggWeightLossAdapter(eggDailyList,  WeightLossListActivity.this);
        recyclerViewWeightLossList.setAdapter(eggWeightLossAdapter);

        //RoomExplorer.show(WeightLossListActivity.this, EggWiseDatabse.class, "EggWiseDB.db");
        //Stetho.initializeWithDefaults(this);
    }



    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 100 && resultCode > 0) {
                //eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);
                if (resultCode == 1) {
                    eggDailyList.add((EggDaily) data.getSerializableExtra("eggDailyList"));
                } else if (resultCode == 2) {
                    eggDailyList.set(pos, (EggDaily) data.getSerializableExtra("eggDailyList"));
                    //eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);
                }
                listVisibility();

                //initializeViews();
                //displayList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEggWeightClick(int pos) {
        new AlertDialog.Builder(WeightLossListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "Weight Loss Chart"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getEggDailyDao().deleteEggDaily(eggDailyList.get(pos));
                                eggDailyList.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                WeightLossListActivity.this.pos = pos;
                                BATCH_LABEL = eggDailyList.get(pos).batchLabel;
                                startActivityForResult(
                                        new Intent(WeightLossListActivity.this,
                                                AddWeightLossActivity.class).putExtra("eggDailyUpdate", eggDailyList.get(pos)),
                                        100);

                                break;
                            case 2:
                                WeightLossListActivity.this.pos = pos;
                                WeightLossChartActivity.BATCH_LABEL = eggDailyList.get(pos).batchLabel;
                                startActivity(
                                        new Intent(WeightLossListActivity.this,
                                                WeightLossChartActivity.class).putExtra("eggDailyChart", eggDailyList.get(pos))
                                                                                .putExtra("eggBatch", eggBatch));
                                finish();
                                break;
                        }
                    }
                }).show();

    }


    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggDaily>> {

        private WeakReference<WeightLossListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(WeightLossListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggDaily> doInBackground(Void... voids) {
            if (activityReference.get()!=null) {
                return activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<EggDaily> eggDailyListPostEx) {
            if (eggDailyListPostEx!=null && eggDailyListPostEx.size()>0 ){

                Integer index = 0;
                EGG_WEIGHT_SUM = 0.0D;
                READING_DAY_NUMBER = 0;
                EGG_LABEL = "";
                for (index = 0; index < eggDailyListPostEx.size(); index++) {
                    READING_DAY_NUMBER = eggDailyListPostEx.get(index).getReadingDayNumber();
                    EGG_LABEL = eggDailyListPostEx.get(index).getEggLabel();
                    if(TRACKING_OPTION == 1) { //Track entire batch
                        EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(BATCH_LABEL, READING_DAY_NUMBER));
                        EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(BATCH_LABEL, READING_DAY_NUMBER));
                        EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(BATCH_LABEL));
                    } else { //Single egg tracking
                        EGG_WEIGHT_SUM =  zeroIfNull(eggDailyListPostEx.get(index).getEggWeight());
                        EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggDailyListPostEx.get(index).getEggWeight()/1D);
                        //EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_EggLabel_Single_Tracking(BATCH_LABEL, READING_DAY_NUMBER, EGG_LABEL));
                        //EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Egglabel_Single_Tracking(BATCH_LABEL, READING_DAY_NUMBER, EGG_LABEL));
                        //EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Single_Tracking(BATCH_LABEL, EGG_LABEL ));
                        if (READING_DAY_NUMBER == 0) {
                            EGG_WEIGHT_AVG_DAY_0 = EGG_WEIGHT_AVG_CURRENT;
                        }
                    }

                    eggDailyListPostEx.get(index).setEggWeightSum(EGG_WEIGHT_SUM);
                    eggDailyListPostEx.get(index).setEggWeightAverageCurrent(EGG_WEIGHT_AVG_CURRENT);
                    eggDailyListPostEx.get(index).setEggWeightAverageDay0(EGG_WEIGHT_AVG_DAY_0);

                    ACTUAL_WEIGHT_LOSS_PERCENT = 100 * ((EGG_WEIGHT_AVG_DAY_0 - EGG_WEIGHT_AVG_CURRENT) / EGG_WEIGHT_AVG_DAY_0);

                    READING_DAY_NUMBER = (eggDailyListPostEx.get(index).getReadingDayNumber());
                    TARGET_WEIGHT_LOSS_INTEGER = eggDailyListPostEx.get(index).getTargetWeightLossInteger();
                    INCUBATION_DAYS = eggDailyListPostEx.get(index).getIncubationDays();
                    Double targetWeightLossDouble = Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER);
                    Double readingDayNumberDouble = Double.valueOf(READING_DAY_NUMBER);
                    Double incubationDaysDouble = Double.valueOf(INCUBATION_DAYS);
                    TARGET_WEIGHT_LOSS_PERCENT = ((targetWeightLossDouble * readingDayNumberDouble) / incubationDaysDouble);

                    WEIGHT_LOSS_DEVIATION = TARGET_WEIGHT_LOSS_PERCENT - ACTUAL_WEIGHT_LOSS_PERCENT;

                    eggDailyListPostEx.get(index).setActualWeightLossPercent(Common.round(ACTUAL_WEIGHT_LOSS_PERCENT,1));
                    eggDailyListPostEx.get(index).setTargetWeightLossPercent(Common.round(TARGET_WEIGHT_LOSS_PERCENT,1));
                    eggDailyListPostEx.get(index).setWeightLossDeviation(Common.round(WEIGHT_LOSS_DEVIATION, 2));

                    //activityReference.get().eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));

                    /*if (WEIGHT_LOSS_DEVIATION != null) {

                        if (Math.abs(ACTUAL_WEIGHT_LOSS_PERCENT) >TARGET_WEIGHT_LOSS_PERCENT) {
                            String message = "*** Warning: Actual Weight deviates beyond Target Weight by "
                                    + WEIGHT_LOSS_DEVIATION + " percent. ***";
                            EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = message;

                        }
                    }*/
                }
                activityReference.get().eggDailyList.clear();
                activityReference.get().eggDailyList.addAll(eggDailyListPostEx);
                // hides empty text view
                activityReference.get().tv_empty_weight_loss_message.setVisibility(View.GONE);
                activityReference.get().eggWeightLossAdapter.notifyDataSetChanged();
            }
        }
    }



    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (eggDailyList.size() == 0){ // no item to display
            if (tv_empty_weight_loss_message.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        tv_empty_weight_loss_message.setVisibility(emptyMsgVisibility);
        eggWeightLossAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
