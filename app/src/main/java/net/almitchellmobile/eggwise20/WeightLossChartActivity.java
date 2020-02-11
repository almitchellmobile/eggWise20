package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import net.almitchellmobile.eggwise20.adapter.EggWeightLossAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class WeightLossChartActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;

    public static final String mypreference = "mypref";

    public static String BATCH_LABEL = "b1";
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

    FloatingActionButton fab_weight_loss;
    private TextView tv_empty_weight_loss_message;
    RecyclerView recyclerViewWeightLossList;
    EggWiseDatabse eggWiseDatabse;
    EggBatch eggBatch;
    EggDaily eggDaily;
    List<EggDaily> eggDailyList;
    private EggWeightLossAdapter eggWeightLossAdapter;
    private int pos;

    public Long settingID = 0L;

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

    DataPoint dataPoint;
    LineGraphSeries<DataPoint> lineGraphSeriesActualWeightLoss = null;
    LineGraphSeries<DataPoint> lineGraphSeriesTargetWeightLoss = null;
    public static ArrayList<String> y_axis_TARGET_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<String> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<Integer> x_axis_READING_DAY = new ArrayList<Integer>();

    public static ArrayList<Double> x_axis_TARGET_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();
    public static ArrayList<Double> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();

    Integer numberOfDataPairs = 1;

    GraphView graphWeightLoss;
    PointsGraphSeries<DataPoint> pointPointsGraphSeries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weight Loss Chart");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

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

        graphWeightLoss = (GraphView) findViewById(R.id.graph);
        //BATCH_LABEL = "b1";
        //createWeightLossChart();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            BATCH_LABEL = eggBatch.getBatchLabel();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            getSupportActionBar().setTitle("Weight Loss Chart Batch: " + BATCH_LABEL);
            createWeightLossChart();
        } else {
            eggDaily = null;
            if (getIntent().getSerializableExtra("eggDailyChart") != null) {
                eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyAdd");
                eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch");
                BATCH_LABEL = eggDaily.getBatchLabel();
                TRACKING_OPTION = eggBatch.getTrackingOption();
                getSupportActionBar().setTitle("Weight Loss Chart Batch: " + BATCH_LABEL);
                createWeightLossChart();
            }
        }

    }

    private void createWeightLossChart() {

        eggDailyList = new ArrayList<>();
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossChartActivity.this);
        eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);

        Integer index = 0;
        EGG_WEIGHT_SUM = 0.0D;
        READING_DAY_NUMBER = 0;


        for (index = 0; index < eggDailyList.size(); index++) {
            READING_DAY_NUMBER = eggDailyList.get(index).getReadingDayNumber();
            EGG_LABEL = eggDailyList.get(index).getEggLabel();
            if(TRACKING_OPTION == 1) { //Track entire batch
                EGG_WEIGHT_SUM = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(BATCH_LABEL, READING_DAY_NUMBER));
                EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(BATCH_LABEL, READING_DAY_NUMBER));
                EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(BATCH_LABEL));
            } else { //Single egg tracking
                EGG_WEIGHT_SUM =  zeroIfNull(eggDailyList.get(index).getEggWeight());
                EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggDailyList.get(index).getEggWeight()/1D);
                if (READING_DAY_NUMBER == 0) {
                    EGG_WEIGHT_AVG_DAY_0 = EGG_WEIGHT_AVG_CURRENT;
                }
            }
            eggDailyList.get(index).setEggWeightSum(EGG_WEIGHT_SUM);
            eggDailyList.get(index).setEggWeightAverageCurrent(EGG_WEIGHT_AVG_CURRENT);
            eggDailyList.get(index).setEggWeightAverageDay0(EGG_WEIGHT_AVG_DAY_0);

            ACTUAL_WEIGHT_LOSS_PERCENT = 100 * ((EGG_WEIGHT_AVG_DAY_0 - EGG_WEIGHT_AVG_CURRENT) / EGG_WEIGHT_AVG_DAY_0);

            READING_DAY_NUMBER = (eggDailyList.get(index).getReadingDayNumber());
            TARGET_WEIGHT_LOSS_INTEGER = eggDailyList.get(index).getTargetWeightLossInteger();
            INCUBATION_DAYS = eggDailyList.get(index).getIncubationDays();
            Double targetWeightLossDouble = Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER);
            Double readingDayNumberDouble = Double.valueOf(READING_DAY_NUMBER);
            Double incubationDaysDouble = Double.valueOf(INCUBATION_DAYS);
            TARGET_WEIGHT_LOSS_PERCENT = ((targetWeightLossDouble * readingDayNumberDouble) / incubationDaysDouble);

            WEIGHT_LOSS_DEVIATION = TARGET_WEIGHT_LOSS_PERCENT - ACTUAL_WEIGHT_LOSS_PERCENT;

            eggDailyList.get(index).setActualWeightLossPercent(ACTUAL_WEIGHT_LOSS_PERCENT);
            eggDailyList.get(index).setTargetWeightLossPercent(TARGET_WEIGHT_LOSS_PERCENT);
            eggDailyList.get(index).setWeightLossDeviation(WEIGHT_LOSS_DEVIATION);

            /*if (WEIGHT_LOSS_DEVIATION != null) {

                if (Math.abs(ACTUAL_WEIGHT_LOSS_PERCENT) >TARGET_WEIGHT_LOSS_PERCENT) {
                    String message = "*** Warning: Actual Weight deviates beyond Target Weight by "
                            + WEIGHT_LOSS_DEVIATION + " percent. ***";
                    //EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = message;

                }
            }*/
        }
        Integer countUniqueReadingDays = 0;
        Integer previousReadingDayNumber = -1;
        for (int i = 0; i < eggDailyList.size(); i++) {
            if (previousReadingDayNumber != eggDailyList.get(i).readingDayNumber) {
                // add new DataPoint object to the array for each of your list entries
                countUniqueReadingDays += 1;
            }
            previousReadingDayNumber = eggDailyList.get(i).readingDayNumber;
        }

        Integer dataPointIndex = 0;
        previousReadingDayNumber = -1;
        DataPoint[] dataPointsActualWeightLoss = new DataPoint[countUniqueReadingDays]; // declare an array of DataPoint objects with the same size as your list
        for (index = 0; index <eggDailyList.size(); index++) {
            if (previousReadingDayNumber != eggDailyList.get(index).readingDayNumber) {
                // add new DataPoint object to the array for each of your list entries
                if (dataPointIndex < countUniqueReadingDays) {
                    dataPointsActualWeightLoss[dataPointIndex] = new DataPoint(Common.round(eggDailyList.get(index).readingDayNumber, 1),
                            Common.round(eggDailyList.get(index).getActualWeightLossPercent(), 1));
                    dataPointIndex +=1;
                }
            }
            //index +=1;
            previousReadingDayNumber = eggDailyList.get(index).readingDayNumber;
        }

        dataPointIndex = 0;
        previousReadingDayNumber = -1;
        TARGET_WEIGHT_LOSS_INTEGER = eggDailyList.get(0).getTargetWeightLossInteger();
        INCUBATION_DAYS = eggDailyList.get(0).getIncubationDays();

        Integer incubationDays = eggDailyList.get(0).getIncubationDays();
        DataPoint[] dataPointsTargetWeightLoss = new DataPoint[incubationDays]; // declare an array of DataPoint objects with the same size as your list
        for (index = 0; index < incubationDays; index++) {
            TARGET_WEIGHT_LOSS_PERCENT = ((Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER) * Double.valueOf(dataPointIndex) )/ Double.valueOf(INCUBATION_DAYS));
                    dataPointsTargetWeightLoss[dataPointIndex] = new DataPoint(Common.round(dataPointIndex, 1),
                            Common.round(TARGET_WEIGHT_LOSS_PERCENT, 1));
                    dataPointIndex +=1;
        }

        graphWeightLoss.setTitle("Target Weight Loss vs Actual Weight Loss");
        graphWeightLoss.setTitleTextSize(60);
        graphWeightLoss.getGridLabelRenderer().setHorizontalAxisTitle("Weigh Day");
        graphWeightLoss.getGridLabelRenderer().setVerticalAxisTitle("Weight Loss Percentage");


        lineGraphSeriesTargetWeightLoss = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLoss);
        graphWeightLoss.addSeries(lineGraphSeriesTargetWeightLoss);

        lineGraphSeriesActualWeightLoss = new LineGraphSeries<DataPoint>(dataPointsActualWeightLoss);
        graphWeightLoss.addSeries(lineGraphSeriesActualWeightLoss);


        lineGraphSeriesActualWeightLoss.setDrawDataPoints(true);
        lineGraphSeriesActualWeightLoss.setColor(Color.YELLOW);

        lineGraphSeriesTargetWeightLoss.setDrawDataPoints(true);
        lineGraphSeriesTargetWeightLoss.setColor(Color.BLUE);

        //graphWeightLoss.refreshDrawableState();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMinimumIntegerDigits(1);
        graphWeightLoss.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));


    }

    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }


}








