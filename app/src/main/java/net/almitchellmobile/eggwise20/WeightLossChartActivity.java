package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
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


    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "Ounces");
        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
        Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));

        /*sharedpreferences = getSharedPreferences(mypreference,
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
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
            //double data = PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE;
            //int value = (int)data;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        } else {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 13.0D;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 13;
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));
        } else {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0D;
        }*/

        graphWeightLoss = (GraphView) findViewById(R.id.graph);
        graphWeightLoss.setTitle("Target Weight vs Actual Weight");
        graphWeightLoss.setTitleTextSize(60);
        graphWeightLoss.getGridLabelRenderer().setHorizontalAxisTitle("Incubation Period (days)");
        graphWeightLoss.getGridLabelRenderer().setHorizontalAxisTitleTextSize(50);
        String verticalAxixTitle1 = "Average Egg Weight (" + Common.PREF_WEIGHT_ENTERED_IN + ") ";
        graphWeightLoss.getGridLabelRenderer().setVerticalAxisTitle(verticalAxixTitle1);
        graphWeightLoss.getGridLabelRenderer().setVerticalAxisTitleTextSize(50);

        //TRACKING_OPTION = 1;
        //BATCH_LABEL = "b1";
        //displayChart();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            EGG_BATCH_ID = eggBatch.getEggBatchID();
            BATCH_LABEL = eggBatch.getBatchLabel();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            returnEggBatchListActivity = EggBatchListActivity.class;
            //returnActivity = "EggBatchListActivity";
            getSupportActionBar().setTitle("Weight Loss Chart for Batch " + BATCH_LABEL);
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
                getSupportActionBar().setTitle("Weight Loss Chart for Batch " + BATCH_LABEL);
                //createWeightLossChart();
                displayChart();
            }
        }

        /*fabWeightLossChart = findViewById(R.id.fabWeightLossChart);
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
        });*/


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
                Intent intent = new Intent(WeightLossChartActivity.this, EggBatchListActivity.class);
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
                //Measured weight loss (Y axis)
                dataPointsActualWeightLossAT = new DataPoint[countUniqueReadingDays]; // declare an array of DataPoint objects with the same size as your list
                for (index = 0; index <eggDailyListPostEx.size(); index++) {
                    if (previousReadingDayNumber != eggDailyListPostEx.get(index).readingDayNumber) {
                        // add new DataPoint object to the array for each of your list entries
                        if (dataPointIndex < countUniqueReadingDays) {
                            dataPointsActualWeightLossAT[dataPointIndex] = new DataPoint(Common.round(eggDailyListPostEx.get(index).readingDayNumber, 2),
                                    //Common.round(eggDailyListPostEx.get(index).getActualWeightLossPercent(), 2));
                                Common.round(eggDailyListPostEx.get(index).getEggWeightAverageCurrent(), 2));
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
                    Double expectedWeight = EGG_WEIGHT_AVG_DAY_0 * (TARGET_WEIGHT_LOSS_PERCENT / 100);
                    Double targetWeightLoss = (EGG_WEIGHT_AVG_DAY_0 - expectedWeight);
                    //dataPointsTargetWeightLossAT[dataPointIndex] = new DataPoint(Common.round(dataPointIndex, 1),
                    //        Common.round(TARGET_WEIGHT_LOSS_PERCENT, 1));
                    dataPointsTargetWeightLossAT[dataPointIndex] = new DataPoint(Common.round(dataPointIndex, 2),
                            Common.round(targetWeightLoss, 2));
                            //Common.round(TARGET_WEIGHT_LOSS_INTEGER, 0));
                    dataPointIndex +=1;
                }

                try {



                    lineGraphSeriesTargetWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsTargetWeightLossAT);
                    lineGraphSeriesTargetWeightLossAT.setDrawDataPoints(true);
                    lineGraphSeriesTargetWeightLossAT.setColor(Color.BLACK);
                    lineGraphSeriesTargetWeightLossAT.setTitle("Target Weight Loss");

                    lineGraphSeriesActualWeightLossAT = new LineGraphSeries<DataPoint>(dataPointsActualWeightLossAT);
                    lineGraphSeriesActualWeightLossAT.setDrawDataPoints(true);
                    String eggWiseYellow = "#ECC317";
                    lineGraphSeriesActualWeightLossAT.setColor(Color.parseColor(eggWiseYellow));
                    //lineGraphSeriesActualWeightLossAT.setColor(Color.YELLOW);
                    lineGraphSeriesActualWeightLossAT.setTitle("Actual Weight Loss");

                    graphWeightLossAT.addSeries(lineGraphSeriesTargetWeightLossAT);
                    graphWeightLossAT.addSeries(lineGraphSeriesActualWeightLossAT);



                    //graphWeightLossAT.getViewport().setMinX(0.00);
                    graphWeightLossAT.getViewport().setMaxX(INCUBATION_DAYS);
                    //graphWeightLossAT.getViewport().setMinY(0.00);
                    //Double targetWeightLossPercentage = Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER)/Double.valueOf(100);
                    graphWeightLossAT.getViewport().setMaxY(TARGET_WEIGHT_LOSS_INTEGER);
                    graphWeightLossAT.getLegendRenderer().setVisible(true);
                    graphWeightLossAT.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    //graphWeightLossAT.getViewport().setYAxisBoundsManual(true);
                    //graphWeightLossAT.getViewport().setXAxisBoundsManual(true);

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



    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }


}








