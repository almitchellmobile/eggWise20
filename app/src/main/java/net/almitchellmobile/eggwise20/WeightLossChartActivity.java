package net.almitchellmobile.eggwise20;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import net.almitchellmobile.eggwise20.adapter.EggWeightLossAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

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
    LineGraphSeries<DataPoint> lineGraphSeries = null;
    public static ArrayList<String> y_axis_TARGET_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<String> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_STRING = new ArrayList<String>();
    public static ArrayList<Integer> x_axis_READING_DAY = new ArrayList<Integer>();

    public static ArrayList<Double> x_axis_TARGET_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();
    public static ArrayList<Double> y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_DOUBLE = new ArrayList<Double>();

    Integer numberOfDataPairs = 1;
    //double[] x_axis_TARGET_WEIGHT_LOSS_PERCENT_DOUBLE= new double[numberOfDataPairs];
    //double[] y_axis_ACTUAL_WEIGHT_LOSS_PERCENT_DOUBLE= new double[numberOfDataPairs];

    GraphView graphWeightLoss;
    PointsGraphSeries<DataPoint> pointPointsGraphSeries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        graphWeightLoss = (GraphView) findViewById(R.id.graph);

        createWeightLossChart();


    }

    private void createWeightLossChart() {
        BATCH_LABEL = "b1";
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

            if (WEIGHT_LOSS_DEVIATION != null) {

                if (Math.abs(ACTUAL_WEIGHT_LOSS_PERCENT) >TARGET_WEIGHT_LOSS_PERCENT) {
                    String message = "*** Warning: Actual Weight deviates beyond Target Weight by "
                            + WEIGHT_LOSS_DEVIATION + " percent. ***";
                    //EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = message;

                }
            }
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
        DataPoint[] dataPoints = new DataPoint[countUniqueReadingDays]; // declare an array of DataPoint objects with the same size as your list
        for (index = 0; index <eggDailyList.size(); index++) {
            if (previousReadingDayNumber != eggDailyList.get(index).readingDayNumber) {
                // add new DataPoint object to the array for each of your list entries
                if (dataPointIndex < countUniqueReadingDays) {
                    dataPoints[dataPointIndex] = new DataPoint(Common.round(eggDailyList.get(index).readingDayNumber, 1),
                            Common.round(eggDailyList.get(index).getActualWeightLossPercent(), 1));
                    dataPointIndex +=1;
                }
            }
            //index +=1;
            previousReadingDayNumber = eggDailyList.get(index).readingDayNumber;
        }

        lineGraphSeries = new LineGraphSeries<DataPoint>(dataPoints); // This one should be obvious right? :)
        graphWeightLoss.addSeries(lineGraphSeries);
    }

    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }
}








