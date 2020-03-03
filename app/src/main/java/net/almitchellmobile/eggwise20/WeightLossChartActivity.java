package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class WeightLossChartActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
   /* public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 0;*/

    public static final String mypreference = "mypref";

    public static String BATCH_LABEL = "";
    public static Long EGG_BATCH_ID = 0L;
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
    public static String RETURN_ACTIVITY = "";

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

    public  static NumberFormat numberFormat;

    //DataPoint dataPoint;
    //public  static DataPoint[] dataPointsTargetWeightLoss = new DataPoint[0];
    //public  static DataPoint[] dataPointsActualWeightLoss = new DataPoint[0];
    //public  static LineGraphSeries<DataPoint> lineGraphSeriesActualWeightLoss = new LineGraphSeries<DataPoint>(dataPointsActualWeightLoss);
    //public  static LineGraphSeries<DataPoint> lineGraphSeriesTargetWeightLoss = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLoss);

    public static ArrayList<String> y_axis_TARGET_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<String> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<Integer> x_axis_READING_DAY = new ArrayList<Integer>();

    public static ArrayList<Double> x_axis_TARGET_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();
    public static ArrayList<Double> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();

    Integer numberOfDataPairs = 1;

    public static GraphView graphWeightLoss;
    PointsGraphSeries<DataPoint> pointPointsGraphSeries;
    FloatingActionButton fabWeightLossChart;
    Class<WeightLossListActivity> returnWeightLossListActivity;
    Class<EggBatchListActivity> returnEggBatchListActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weight Loss Chart");

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

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

        graphWeightLoss = (GraphView) findViewById(R.id.graph);
        graphWeightLoss.setTitle("Target Weight Loss vs Actual Weight Loss");
        graphWeightLoss.setTitleTextSize(60);
        graphWeightLoss.getGridLabelRenderer().setHorizontalAxisTitle("Weigh Day");
        graphWeightLoss.getGridLabelRenderer().setVerticalAxisTitle("Weight Loss Percentage");

        //TRACKING_OPTION = 1;
        //BATCH_LABEL = "b1";
        //displayChart();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            EGG_BATCH_ID = eggBatch.getEggBatchID();
            EGG_LABEL = eggBatch.getBatchLabel();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            returnEggBatchListActivity = EggBatchListActivity.class;
            //returnActivity = "EggBatchListActivity";
            getSupportActionBar().setTitle("Weight Loss Chart Batch: " + BATCH_LABEL);
            displayChart();
        } else {
            eggDaily = null;
            if (getIntent().getSerializableExtra("eggDailyChart") != null) {
                eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyAdd");
                eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch");
                BATCH_LABEL = eggDaily.getBatchLabel();
                TRACKING_OPTION = eggBatch.getTrackingOption();
                returnWeightLossListActivity = WeightLossListActivity.class;
                //returnActivity = "WeightLossListActivity";
                getSupportActionBar().setTitle("Weight Loss Chart Batch: " + BATCH_LABEL);
                //createWeightLossChart();
                displayChart();
            }
        }

        fabWeightLossChart = findViewById(R.id.fabWeightLossChart);
        fabWeightLossChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = null;
                if (RETURN_ACTIVITY.equalsIgnoreCase("WeightLossListActivity")) {
                    intent2 = new Intent(WeightLossChartActivity.this, WeightLossListActivity.class);
                } else {
                    intent2 = new Intent(WeightLossChartActivity.this, EggBatchListActivity.class);
                }
                intent2.putExtra("eggBatch",eggBatch);
                startActivity(intent2);
                finish();
            }
        });

    }

    private void displayChart(){
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossChartActivity.this);
        new WeightLossChartActivity.RetrieveTask(this, eggDailyList, graphWeightLoss).execute();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggDaily>> {

        private WeakReference<WeightLossChartActivity> activityReference;

        List<EggDaily> eggDailyListAT;
        DataPoint dataPointAT;

        GraphView graphWeightLossAT;
        DataPoint[] dataPointsTargetWeightLossAT = new DataPoint[0];
        DataPoint[] dataPointsActualWeightLossAT = new DataPoint[0];
        LineGraphSeries<DataPoint> lineGraphSeriesActualWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsActualWeightLossAT);
        LineGraphSeries<DataPoint> lineGraphSeriesTargetWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLossAT);

        // only retain a weak reference to the activity
        RetrieveTask(WeightLossChartActivity context, List<EggDaily> eggDailyList, GraphView graphWeightLoss) {
            activityReference = new WeakReference<>(context);
            eggDailyListAT = eggDailyList;
            graphWeightLossAT = graphWeightLoss;
        }

        @Override
        protected List<EggDaily> doInBackground(Void... voids) {
            if (activityReference.get()!=null) {
                    eggDailyListAT =  activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(EGG_BATCH_ID);
                return eggDailyListAT;
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


                for (index = 0; index < eggDailyListPostEx.size(); index++) {
                    READING_DAY_NUMBER = eggDailyListPostEx.get(index).getReadingDayNumber();
                    EGG_LABEL = eggDailyListPostEx.get(index).getEggLabel();
                    if(TRACKING_OPTION == 1) { //Track entire batch
                        EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                        EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                        EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(EGG_BATCH_ID));
                    } else { //Single egg tracking
                        EGG_WEIGHT_SUM =  zeroIfNull(eggDailyListPostEx.get(index).getEggWeight());
                        EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggDailyListPostEx.get(index).getEggWeight()/1D);
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

                    eggDailyListPostEx.get(index).setActualWeightLossPercent(ACTUAL_WEIGHT_LOSS_PERCENT);
                    eggDailyListPostEx.get(index).setTargetWeightLossPercent(TARGET_WEIGHT_LOSS_PERCENT);
                    eggDailyListPostEx.get(index).setWeightLossDeviation(WEIGHT_LOSS_DEVIATION);

                }
                Integer countUniqueReadingDays = 0;
                Integer previousReadingDayNumber = -1;
                for (int i = 0; i < eggDailyListPostEx.size(); i++) {
                    if (previousReadingDayNumber != eggDailyListPostEx.get(i).readingDayNumber) {
                        // add new DataPoint object to the array for each of your list entries
                        countUniqueReadingDays += 1;
                    }
                    previousReadingDayNumber = eggDailyListPostEx.get(i).readingDayNumber;
                }

                Integer dataPointIndex = 0;
                previousReadingDayNumber = -1;
                dataPointsActualWeightLossAT = new DataPoint[countUniqueReadingDays]; // declare an array of DataPoint objects with the same size as your list
                for (index = 0; index <eggDailyListPostEx.size(); index++) {
                    if (previousReadingDayNumber != eggDailyListPostEx.get(index).readingDayNumber) {
                        // add new DataPoint object to the array for each of your list entries
                        if (dataPointIndex < countUniqueReadingDays) {
                            dataPointsActualWeightLossAT[dataPointIndex] = new DataPoint(Common.round(eggDailyListPostEx.get(index).readingDayNumber, 2),
                                    Common.round(eggDailyListPostEx.get(index).getActualWeightLossPercent(), 2));
                            dataPointIndex +=1;
                        }
                    }
                    //index +=1;
                    previousReadingDayNumber = eggDailyListPostEx.get(index).readingDayNumber;
                }

                dataPointIndex = 0;
                previousReadingDayNumber = -1;
                TARGET_WEIGHT_LOSS_INTEGER = eggDailyListPostEx.get(0).getTargetWeightLossInteger();
                INCUBATION_DAYS = eggDailyListPostEx.get(0).getIncubationDays();

                Integer incubationDays = eggDailyListPostEx.get(0).getIncubationDays();
                dataPointsTargetWeightLossAT = new DataPoint[incubationDays]; // declare an array of DataPoint objects with the same size as your list
                for (index = 0; index < incubationDays; index++) {
                    TARGET_WEIGHT_LOSS_PERCENT = ((Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER) * Double.valueOf(dataPointIndex) )/ Double.valueOf(INCUBATION_DAYS));
                    dataPointsTargetWeightLossAT[dataPointIndex] = new DataPoint(Common.round(dataPointIndex, 2),
                            Common.round(TARGET_WEIGHT_LOSS_PERCENT, 2));
                    dataPointIndex +=1;
                }

                try {

                    lineGraphSeriesActualWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsActualWeightLossAT);
                    lineGraphSeriesActualWeightLossAT.setDrawDataPoints(true);
                    lineGraphSeriesActualWeightLossAT.setColor(Color.YELLOW);
                    //lineGraphSeriesActualWeightLossAT.setDrawDataPoints(true);
                    //lineGraphSeriesActualWeightLossAT.setDrawAsPath(false);
                    //lineGraphSeriesActualWeightLossAT.isDrawDataPoints();
                    //lineGraphSeriesActualWeightLossAT.isDrawAsPath();


                    lineGraphSeriesTargetWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLossAT);
                    lineGraphSeriesTargetWeightLossAT.setDrawDataPoints(true);
                    lineGraphSeriesTargetWeightLossAT.setColor(Color.BLACK);

                    graphWeightLossAT.addSeries(lineGraphSeriesActualWeightLossAT);
                    graphWeightLossAT.addSeries(lineGraphSeriesTargetWeightLossAT);


                    graphWeightLossAT.getViewport().setMinX(0.00);
                    graphWeightLossAT.getViewport().setMaxX(INCUBATION_DAYS);
                    graphWeightLossAT.getViewport().setMinY(0.00);
                    //Double targetWeightLossPercentage = Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER)/Double.valueOf(100);
                    graphWeightLossAT.getViewport().setMaxY(TARGET_WEIGHT_LOSS_INTEGER);

                    graphWeightLossAT.getViewport().setYAxisBoundsManual(true);
                    graphWeightLossAT.getViewport().setXAxisBoundsManual(true);

                    //numberFormat = NumberFormat.getInstance();
                    //numberFormat.setMinimumFractionDigits(2);
                    //numberFormat.setMinimumIntegerDigits(1);
                    //numberFormat.setRoundingMode(RoundingMode.UP);
                    //graphWeightLossAT.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(numberFormat, numberFormat));

                    activityReference.get().graphWeightLoss = graphWeightLossAT;

                   //activityReference.get().graphWeightLoss.refreshDrawableState();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void createWeightLossChart() {

        eggDailyList = new ArrayList<>();
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossChartActivity.this);
        eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(EGG_BATCH_ID);

        Integer index = 0;
        EGG_WEIGHT_SUM = 0.0D;
        READING_DAY_NUMBER = 0;


        for (index = 0; index < eggDailyList.size(); index++) {
            READING_DAY_NUMBER = eggDailyList.get(index).getReadingDayNumber();
            EGG_LABEL = eggDailyList.get(index).getEggLabel();
            if(TRACKING_OPTION == 1) { //Track entire batch
                EGG_WEIGHT_SUM = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(EGG_BATCH_ID));
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


        /*lineGraphSeriesTargetWeightLoss = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLoss);
        graphWeightLoss.addSeries(lineGraphSeriesTargetWeightLoss);

        lineGraphSeriesActualWeightLoss = new LineGraphSeries<DataPoint>(dataPointsActualWeightLoss);
        graphWeightLoss.addSeries(lineGraphSeriesActualWeightLoss);


        lineGraphSeriesActualWeightLoss.setDrawDataPoints(true);
        lineGraphSeriesActualWeightLoss.setColor(Color.YELLOW);

        lineGraphSeriesTargetWeightLoss.setDrawDataPoints(true);
        lineGraphSeriesTargetWeightLoss.setColor(Color.BLUE);*/

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








