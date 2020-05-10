package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggBatchAdapter;
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
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class EggBatchListActivity extends AppCompatActivity implements EggBatchAdapter.OnEggBatchItemClick{

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    MaterialTapTargetPrompt mFabPrompt;

    //public static String BATCH_LABEL = "";
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
    public static DecimalFormat df = new DecimalFormat("00.00");

    public com.google.android.material.card.MaterialCardView cv_egg_batch;
    public Button btn_add_new_batch_1;

    private TextView textViewMsg;
    private RecyclerView recyclerViewEggBatchList;
    private EggWiseDatabse eggWiseDatabse;
    private List<EggBatch> eggBatchList;
    private EggBatchAdapter eggBatchAdapter;
    private int pos;
    Toolbar toolbar_egg_batch_list;
    SearchView searchView;
    FloatingActionButton fab_egg_batch_list_1;

    int itemIndex = 0;
    public LinearLayoutManager mLinearLayoutManager;
    LinearLayout card;
    public  LinearLayout ll_egg_batch_list;
    ListView listView;

    Common common;
    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_batch_list);
        toolbar_egg_batch_list = findViewById(R.id.toolbar_egg_batch_list);
        setSupportActionBar(toolbar_egg_batch_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        common = new Common();

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "Ounces");
        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
        Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));



        initializeViews();
        displayList();

        //showSequenceManual(mLinearLayoutManager);
        //showAddBatchPrompt();
        /*if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_2", false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            showSequenceManual(mLinearLayoutManager);
            editor.putBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_2", true);
            editor.commit();
        }*/
    }

    private SpannableStringBuilder setSecondarytText(String secondaryText, Integer textStart, Integer textEnd) {
        SpannableStringBuilder secondaryText1 = new SpannableStringBuilder(
                secondaryText);
        ForegroundColorSpan foregroundColour1 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText1.setSpan(foregroundColour1, textStart, textEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return secondaryText1;
    }


   /* public void showSequenceManual(LinearLayoutManager mLinearLayoutManager_local) {

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                        .setTarget(findViewById(R.id.fab_egg_batch_list))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Add Egg Batch.")
                        .setSecondaryText(setSecondarytText("To add a batch, tap the plus sign. Tap here to continue.", 39, 43))
                        .setFocalPadding(R.dimen.dp40)
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                        .setTarget(recyclerViewEggBatchList)
                        .setFocalPadding(R.dimen.dp40)
                        .setPrimaryText("Egg Batch Sub-Menu")
                        .setSecondaryText(setSecondarytText("Tap a batch list item to open the sub-menu. Tap here to continue.", 48, 52))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .create())
                .show();


    }*/

    /*public void showSequence(View view) {

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                        .setTarget(findViewById(R.id.fab_egg_batch_list))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("To Add Egg Batch.")
                        .setSecondaryText("Tap the plus sign to add a batch.")
                        .setFocalPadding(R.dimen.dp40)
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .create(), 8000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                        .setTarget(findViewById(R.id.button_save_add_weight_loss))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("To Save Egg Batch")
                        .setSecondaryText("Tap the save button to save your egg information.")
                        .setFocalPadding(R.dimen.dp40)
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .create(), 8000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                        .setTarget(recyclerViewEggBatchList)
                        .setFocalPadding(R.dimen.dp40)
                        .setPrimaryText("To Open the Egg Batch Sub-Menu")
                        .setSecondaryText("Tap an egg batch list item to open the egg batch sub-menu. Tap to continue.")
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .create())

                .show();
    }*/


    private void showEggBatchSubMenuPrompt() {

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(recyclerViewEggBatchList)
                .setFocalPadding(R.dimen.dp40)
                .setPrimaryText("Egg Batch Sub-Menu")
                .setSecondaryText("Tap an egg batch list item to open the egg batch sub-menu. Tap here to continue.")
                .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .show();
    }



    public void showAddBatchPrompt()
    {
        if (mFabPrompt != null)
        {
            return;
        }
        mFabPrompt = new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                .setTarget(findViewById(R.id.fab_egg_batch_list_1))
                .setFocalPadding(R.dimen.dp40)
                .setPrimaryText("Add Egg Batch.")
                //.setSecondaryText(setSecondarytText("Tap the plus sign to add a batch. Tap here to continue.", 38, 42))
                .setSecondaryText("Tap the plus sign to add your first batch.")
                .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
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
                Intent intent = new Intent(EggBatchListActivity.this, EggWiseMainActivity.class);
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



    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }




    private void displayList(){



        eggWiseDatabse = EggWiseDatabse.getInstance(EggBatchListActivity.this);
        new EggBatchListActivity.RetrieveTask(this).execute();
        if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_1", false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            showAddBatchPrompt();
            editor.putBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_1", true);
            editor.commit();
        }


    }

    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggBatch>> {

        private WeakReference<EggBatchListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(EggBatchListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggBatch> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                //return null;
                return activityReference.get().eggWiseDatabse.getEggBatchDao().getAllEggBatch();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<EggBatch> eggBatch) {
            if (eggBatch!=null && eggBatch.size()>0 ){

                activityReference.get().eggBatchList.clear();
                activityReference.get().eggBatchList.addAll(eggBatch);

                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().eggBatchAdapter.notifyDataSetChanged();

            } else {

                /*if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_1", false)) {
                    // The user hasn't seen the OnboardingSupportFragment yet, so show it
                    activityReference.get().showAddBatchPrompt();
                    editor.putBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_1", true);
                    editor.commit();
                }*/
            }

        }

        private void computeAveragesAndPercents() {

            //EGG_BATCH_ID = 0l;
            List<EggDaily> eggDailyListPostEx = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(EGG_BATCH_ID);

            Integer index = 0;
            EGG_WEIGHT_SUM = 0.0D;
            READING_DAY_NUMBER = 0;
            EGG_LABEL = "";
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
        }
    }

    private void initializeViews() {
        toolbar_egg_batch_list = (Toolbar) findViewById(R.id.toolbar_egg_batch_list);
        setSupportActionBar(toolbar_egg_batch_list);
        textViewMsg = (TextView) findViewById(R.id.tv_empty_egg_batches1);

        fab_egg_batch_list_1 =  findViewById(R.id.fab_egg_batch_list_1);
        fab_egg_batch_list_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(EggBatchListActivity.this, AddEggBatchActivity.class), 100);
                //showEggBatchListItemPrompt1(mLinearLayoutManager);
            }
        });
        cv_egg_batch = findViewById(R.id.cv_egg_batch);

       /* btn_add_new_batch_1 = findViewById(R.id.btn_add_new_batch_1);
        btn_add_new_batch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(EggBatchListActivity.this, AddEggBatchActivity.class), 100);
            }
        });*/



        recyclerViewEggBatchList = findViewById(R.id.recycler_view_egg_batch_list);
        mLinearLayoutManager = new LinearLayoutManager(EggBatchListActivity.this);
        recyclerViewEggBatchList.setLayoutManager(mLinearLayoutManager);
        eggBatchList = new ArrayList<EggBatch>();
        eggBatchAdapter = new EggBatchAdapter(eggBatchList, EggBatchListActivity.this);
        recyclerViewEggBatchList.setAdapter(eggBatchAdapter);

        recyclerViewEggBatchList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                final LinearLayout card = (LinearLayout) mLinearLayoutManager.findViewByPosition(0);
                // Check that the view exists for the item
                if (card != null) {
                    //showEggBatchListItemPrompt1(mLinearLayoutManager);
                    if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_2", false)) {
                        // The user hasn't seen the OnboardingSupportFragment yet, so show it
                        showEggBatchListItemPrompt1(mLinearLayoutManager);
                        editor.putBoolean("COMPLETED_ONBOARDING_PREF_BATCH_LIST_2", true);
                        editor.commit();
                    }
                    recyclerViewEggBatchList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });



    }

    private void showEggBatchListItemPrompt1(LinearLayoutManager mLinearLayoutManager_local) {
        // The example below uses a RecyclerView with LinearLayoutManager
        final LinearLayout card = (LinearLayout) mLinearLayoutManager_local.findViewByPosition(0);
        // Check that the view exists for the item
        if (card != null)
        {
            final EggBatchAdapter.BeanHolder viewHolder = (EggBatchAdapter.BeanHolder) recyclerViewEggBatchList.getChildViewHolder(card);
            new MaterialTapTargetPrompt.Builder(EggBatchListActivity.this)
                    .setTarget(viewHolder.cv_egg_batch)
                    //.setClipToView(card.getChildAt(0))
                    .setPrimaryText("Egg Batch Sub-Menu")
                    .setSecondaryText("Click on the flashing circle to open the egg batch sub-menu. Then tap 'Enter Egg Weight' to enter your egg weight details.")
                    .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                eggBatchList.add((EggBatch) data.getSerializableExtra("eggBatchList"));
            } else if (resultCode == 2) {
                eggBatchList.set(pos, (EggBatch) data.getSerializableExtra("eggBatchList"));
            }
            listVisibility();
        } else if (requestCode == 200) {
            Intent intent1 = new Intent(EggBatchListActivity.this, WeightLossListActivity.class);
            intent1.putExtra("eggBatchList", eggBatchList.get(pos));
            startActivity(intent1);
            finish();

        }
    }

    @Override
    public void onEggBatchClick(final int pos) {
        new AlertDialog.Builder(EggBatchListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "Enter Egg Weight", "List Egg Weights", "Display Weight Loss Chart", "Cancel"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getEggBatchDao().deleteEggBatch(eggBatchList.get(pos));
                                eggBatchList.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                EggBatchListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(EggBatchListActivity.this,
                                                AddEggBatchActivity.class).putExtra("UpdateEggBatch", eggBatchList.get(pos)),
                                        100);
                                break;
                            case 2:
                                EggBatchListActivity.this.pos = pos;
                                //WeightLossListActivity.BATCH_LABEL = eggBatchList.get(pos).getBatchLabel();
                                Intent intent1 = new Intent(EggBatchListActivity.this, AddWeightLossActivity.class);
                                intent1.putExtra("eggBatch", eggBatchList.get(pos));
                                startActivity(intent1);
                                finish();
                                break;
                            case 3:
                                EggBatchListActivity.this.pos = pos;
                                Intent intent2 = new Intent(EggBatchListActivity.this, WeightLossListActivity.class);
                                intent2.putExtra("eggBatch",eggBatchList.get(pos));
                                startActivity(intent2);
                                finish();
                                break;
                            case 4:
                                EggBatchListActivity.this.pos = pos;
                                WeightLossChartActivity.RETURN_ACTIVITY = "EggBatchListActivity";
                                Intent intent3 = new Intent(EggBatchListActivity.this, WeightLossChartActivity.class);
                                intent3.putExtra("eggBatch",eggBatchList.get(pos));
                                startActivity(intent3);
                                finish();
                                break;
                            case 5:
                                displayList();
                                break;


                        }
                    }
                }).show();

    }

    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (eggBatchList != null) {
            if (eggBatchList.size() == 0) { // no item to display
                if (textViewMsg.getVisibility() == View.GONE) {
                    emptyMsgVisibility = View.VISIBLE;

                }

            } else {

            }
            textViewMsg.setVisibility(emptyMsgVisibility);
            eggBatchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
