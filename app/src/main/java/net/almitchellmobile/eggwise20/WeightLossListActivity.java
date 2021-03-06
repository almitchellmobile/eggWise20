package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.chip.Chip;

import net.almitchellmobile.eggwise20.adapter.EggWeightLossAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class WeightLossListActivity extends AppCompatActivity implements EggWeightLossAdapter.OnEggWeightItemClick {

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    MaterialTapTargetPrompt mFabPrompt;
    public static Boolean HIDE_REJECTS = true;
    public static String ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel ASC";
    public static Integer ORDER_BY_SELECTION = 0;

    public ArrayList<EggDaily> eggDailyListOnClick = new ArrayList<>();
    public static LinkedHashMap<Integer ,String> sortByLinkedHashMap;
    public static ArrayList<EggDaily> EGG_DAILY_LIST_STATIC = null;
    public static HashMap<Long,EggDaily> HASHMAP_EGG_DAILY_ID_EGG_DAILY = new HashMap<Long,EggDaily>();
    public static ArrayList<EggDaily> eggDailyListFiltered = new ArrayList<>();
    public static Integer indexHashMap = 0;
    public static Integer EGG_DAILY_LIST_POS_STATIC = 0;
    public static String BATCH_LABEL = "";
    public static Long EGG_BATCH_ID = 0L;
    public static Long EGG_DAILY_ID_STATIC = 0L;
    public static String EGG_LABEL = "";
    public static Double EGG_WEIGHT_SUM = 0.0;
    public static Double EGG_WEIGHT_AVG_CURRENT = 0.0;
    public static Double EGG_WEIGHT_AVG_DAY_0 = 0.0;
    public static Double ACTUAL_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double TARGET_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double WEIGHT_LOSS_DEVIATION = 0.0;
    public static String SET_DATE = "";
    public static String WEIGHT_LOSS_TITLE = "";
    public static Integer TRACKING_OPTION = 0;
    public static Integer READING_DAY_NUMBER = 0;
    public static Integer TARGET_WEIGHT_LOSS_INTEGER = 0;
    public static Integer INCUBATION_DAYS = 0;
    public static Integer NUMBER_OF_EGGS = 0 ;
    public static Integer NUMBER_OF_REJECTED_EGGS = 0;
    public static DecimalFormat df = new DecimalFormat("00.00");

    public static String actionBarTitle = "";

    private SearchView searchView;
    //FloatingActionButton fab_weight_loss_rejects_visibility, fab_add_weight_loss_list;
    //private TextView tv_empty_weight_loss_message;
    private TextView tv_egg_weight_title1, tv_weight_loss_title;
    private RecyclerView recyclerViewWeightLossList;
    private EggWiseDatabse eggWiseDatabse;
    EggBatch eggBatch;
    public ArrayList<EggDaily> eggDailyList = new ArrayList<>();
    EggDaily eggDailyOnClick = null;

    private EggWeightLossAdapter eggWeightLossAdapter;
    private int pos;
    EditText et_weight_loss_filter_label, et_weight_loss_filter_day;
    Chip chip_filter_weight_loss_apply_reset;
    ToggleButton toggle_btn_and_or, toggle_btn_hide_show_rejects;
    Boolean toggleButtonState;
    Button btn_filter_weight_loss_apply, btn_filter_weight_loss_reset, button_show_hide_rejects, btn_add_weight;
    Spinner spinnerSortBy;
    public com.google.android.material.card.MaterialCardView cv_egg_weight;

    //public static CheckBox check_box_rejected_egg;

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
    public Integer rejectedEgg = 0;
    private LinearLayoutManager mLinearLayoutManager;

    Common common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_list);
        Toolbar toolbar_weight_loss_list = findViewById(R.id.toolbar_weight_loss_list);
        toolbar_weight_loss_list.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar_weight_loss_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_weight_loss_list.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeightLossListActivity.this, EggBatchListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "Ounces");
        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
        Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));

        common = new Common();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            BATCH_LABEL = eggBatch.getBatchLabel();
            EGG_BATCH_ID = eggBatch.getEggBatchID();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            SET_DATE = eggBatch.getSetDate();
            NUMBER_OF_EGGS = eggBatch.getNumberOfEggs();

            WEIGHT_LOSS_TITLE = "Set Date: " + SET_DATE;
            WEIGHT_LOSS_TITLE += ", Incubator: " + eggBatch.getIncubatorName();
            WEIGHT_LOSS_TITLE += ", Warn Deviation Amt.: " + Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE;

            getSupportActionBar().setTitle("Weight Loss for Batch: " + BATCH_LABEL);
        } else {
            getSupportActionBar().setTitle("Weight Loss");
        }

        tv_weight_loss_title = findViewById(R.id.tv_weight_loss_title);
        tv_weight_loss_title.setText(WEIGHT_LOSS_TITLE);
        tv_weight_loss_title.setTextSize(16);


        spinnerSortBy = (Spinner) findViewById(R.id.spinnerSortBy);
        List<String> list = new ArrayList<String>();
        list.add("Sort By Reading Day ASC, Egg Label ASC");
        list.add("Sort By Reading Day ASC, Egg Label DESC");
        list.add("Sort By Reading Day DESC, Egg Label ASC");
        list.add("Sort By Reading Day DESC, Egg Label DESC");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortBy.setAdapter(dataAdapter);
        spinnerSortBy.setSelection(0);
        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel ASC";
                        ORDER_BY_SELECTION = 0;
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel DESC ";
                        ORDER_BY_SELECTION = 1;
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber DESC, EggLabel ASC ";
                        ORDER_BY_SELECTION = 2;
                        break;
                    case 3:
                        //Toast.makeText(parent.getContext(), "Spinner item 4!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber DESC, EggLabel DESC ";
                        ORDER_BY_SELECTION = 3;
                        break;
                }
                initializeViews();
                displayList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        toggle_btn_and_or = (ToggleButton) findViewById(R.id.toggle_btn_and_or);
        toggle_btn_and_or.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    //toggle_btn_and_or.setChecked(false);
                    //toggle_btn_and_or.setTextOff("OR");
                } else {
                    // The toggle is disabled
                    //toggle_btn_and_or.setChecked(true);
                    //toggle_btn_and_or.setTextOff("AND");
                }
            }
        });

        btn_filter_weight_loss_apply = findViewById(R.id.btn_filter_weight_loss_apply);
        btn_filter_weight_loss_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //apply filters
                if (toggle_btn_and_or.isChecked()) {
                    if (et_weight_loss_filter_label.getText().toString().length() > 0 && et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterLabelAndReadingDay(et_weight_loss_filter_label.getText().toString(), et_weight_loss_filter_day.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() > 0 && et_weight_loss_filter_day.getText().toString().length() == 0) {
                        filterLabel(et_weight_loss_filter_label.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() == 0 && et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterDay(et_weight_loss_filter_day.getText().toString());
                    }
                } else {
                    if (et_weight_loss_filter_label.getText().toString().length() > 0 || et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterLabelAndReadingDay(et_weight_loss_filter_label.getText().toString(), et_weight_loss_filter_day.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() > 0 || et_weight_loss_filter_day.getText().toString().length() == 0) {
                        filterLabel(et_weight_loss_filter_label.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() == 0 || et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterDay(et_weight_loss_filter_day.getText().toString());
                    }
                }
                hideSoftKeyboard(WeightLossListActivity.this, v);
            }
        });


        btn_filter_weight_loss_reset = findViewById(R.id.btn_filter_weight_loss_reset);
        btn_filter_weight_loss_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset filters
                resetFilters();
                //eggDailyList = EGG_DAILY_LIST_STATIC;
                //eggWeightLossAdapter.filterEggDailyList(eggDailyList);
                initializeViews();
                displayList();
                //eggWeightLossAdapter.notifyDataSetChanged();
                hideSoftKeyboard(WeightLossListActivity.this, v);
            }
        });


        et_weight_loss_filter_label = (EditText) findViewById(R.id.et_weight_loss_filter_label);
        et_weight_loss_filter_label.setSelectAllOnFocus(true);
        et_weight_loss_filter_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)view;
                editText.setSelection(0, editText.getText().length()); // selects all the text
            }
        });
        //et_weight_loss_filter_label.setFocusable(true);

        et_weight_loss_filter_day = (EditText) findViewById(R.id.et_weight_loss_filter_day);
        et_weight_loss_filter_day.setSelectAllOnFocus(true);
        et_weight_loss_filter_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)view;
                editText.setSelection(0, editText.getText().length()); // selects all the text
            }
        });
        //et_weight_loss_filter_day.setFocusable(true);
        et_weight_loss_filter_day.setInputType(InputType.TYPE_CLASS_NUMBER);

        initializeViews();
        displayList();

    }


    private SpannableStringBuilder setSecondarytText(String secondaryText, Integer textStart, Integer textEnd) {
        ColorStateList oldColors =    et_weight_loss_filter_day.getTextColors(); //save original colors
        SpannableStringBuilder secondaryText1 = new SpannableStringBuilder(
                secondaryText);
        ForegroundColorSpan foregroundColour1 = new ForegroundColorSpan(
                ContextCompat.getColor(this,R.color.green_200));
        secondaryText1.setSpan(oldColors, textStart, textEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return secondaryText1;
    }

    public void showSequenceManual() {

        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.spinnerSortBy))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setPrimaryText("To Sort the List")
                        .setSecondaryText(setSecondarytText("This dropdown is used to sort the list. Tap here to continue.", 44, 48))
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.et_weight_loss_filter_day))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Filter by Day")
                        .setSecondaryText(setSecondarytText("To filter by reading day, enter a reading day number. Tap here to continue.", 58, 62))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.et_weight_loss_filter_label))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Filter by Egg Label")
                        .setSecondaryText(setSecondarytText("To filter by egg label, enter a label value here. Tap here to continue.", 54, 58))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.toggle_btn_and_or))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("AND or OR Filter Values")
                        .setSecondaryText(setSecondarytText("To 'AND' or 'OR' these values, click the [AND/OR] button. Tap here to continue.", 54, 58))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.btn_filter_weight_loss_apply))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Apply Filter")
                        .setSecondaryText(setSecondarytText("To apply the filter, click the [APPLY} button. Tap here to continue.", 44, 48))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.btn_filter_weight_loss_reset))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Reset Filter")
                        .setSecondaryText(setSecondarytText("To reset the filter, click [RESET] button.Tap here to continue.", 43, 47))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.btn_add_weight))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Add Egg Weights")
                        .setSecondaryText(setSecondarytText("Tap the [ADD WEIGHT} to add weight loss details. Tap here to continue.", 50, 54))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .create())
                .addPrompt(new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                        .setTarget(findViewById(R.id.button_show_hide_rejects))
                        .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                        .setPrimaryText("Show/Hide Rejected Eggs")
                        .setSecondaryText(setSecondarytText("Tap the [SHOW REJECTS] button to show/hide rejected eggs. Tap here to continue.", 58, 62))
                        .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                        .setFocalPadding(R.dimen.dp40)
                        .setPromptStateChangeListener((prompt, state) -> {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                            {
                                mFabPrompt = null;
                                //Do something such as storing a value so that this prompt is never shown again
                                // User has pressed the prompt target
                                showWeightLossListItemPrompt(mLinearLayoutManager);
                            }
                        })
                        .create())
                .show();
    }


    public void showAddWeightsPrompt()
    {
        if (mFabPrompt != null)
        {
            return;
        }
        mFabPrompt = new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                .setTarget(findViewById(R.id.btn_add_weight))
                .setFocalPadding(R.dimen.dp40)
                .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                .setPrimaryText("Add Egg Weights")
                .setSecondaryText(setSecondarytText("Tap the [ADD WEIGHT] button to add egg weights. Tap here to continue.", 42, 46))
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


    private void showWeightLossListItemPrompt(LinearLayoutManager mLinearLayoutManager_local) {
        // The example below uses a RecyclerView with LinearLayoutManager
        final LinearLayout card = (LinearLayout) mLinearLayoutManager_local.findViewByPosition(0);
        // Check that the view exists for the item
        if (card != null)
        {
            //showEggBatchSubMenuPrompt();

            final EggWeightLossAdapter.BeanHolder viewHolder = (EggWeightLossAdapter.BeanHolder) recyclerViewWeightLossList.getChildViewHolder(card);
            mFabPrompt = new MaterialTapTargetPrompt.Builder(WeightLossListActivity.this)
                    .setTarget(viewHolder.cv_egg_weight)
                    .setPrimaryText("Weight Loss Sub-Menu")
                    .setSecondaryText("Click on the flashing circle to open the weight loss sub-menu.")
                    .setBackgroundColour(ContextCompat.getColor(this,R.color.colorAccent))
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .setFocalPadding(R.dimen.dp40)
                    .setPromptStateChangeListener((prompt, state) -> {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                        {
                            mFabPrompt = null;
                            //Do something such as storing a value so that this prompt is never shown again
                            // User has pressed the prompt target
                            //showSequence(spinnerSortBy);
                        }
                    })

                    .create();
            if (mFabPrompt != null)
            {
                mFabPrompt.show();
            }
        }
    }



    private void resetFilters() {
        et_weight_loss_filter_label.setText("");
        et_weight_loss_filter_day.setText("");
    }

    private void filterLabelAndReadingDay(String textLabel, String textReadingDay) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();
        indexHashMap = 0;
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (toggle_btn_and_or.isChecked()) { // And the filters
                if (eggDaily.getEggLabel().equalsIgnoreCase(textLabel.toLowerCase()) &&
                        eggDaily.getReadingDayNumber().toString().equalsIgnoreCase(textReadingDay.toLowerCase())) {
                    eggDailyListFiltered.add(eggDaily);
                    HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
                }

            } else {  //Or the filters
                if (eggDaily.getEggLabel().equalsIgnoreCase(textLabel.toLowerCase()) ||
                        eggDaily.getReadingDayNumber().toString().equalsIgnoreCase(textReadingDay.toLowerCase())) {
                    //adding the element to filtered list
                    eggDailyListFiltered.add(eggDaily);
                    HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
                }
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
    }

    private void filterLabel(String text) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();

        //looping through existing elements
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (eggDaily.getEggLabel().equalsIgnoreCase(text.toLowerCase())) {
                //adding the element to filtered list
                eggDailyListFiltered.add(eggDaily);
                HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
    }

    private void filterDay(String text) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();

        //looping through existing elements
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (eggDaily.getReadingDayNumber().toString().equalsIgnoreCase(text.toLowerCase())) {
                //adding the element to filtered list
                eggDailyListFiltered.add(eggDaily);
                HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
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
                Intent intent = new Intent(WeightLossListActivity.this, EggBatchListActivity.class);
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
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossListActivity.this);
        new WeightLossListActivity.RetrieveTask(this).execute();
        if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", false)) {
            showAddWeightsPrompt();
            editor.putBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_1", true);
            editor.commit();
        }
        //updateActionBarTitle();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //tv_empty_weight_loss_message =  (TextView) findViewById(R.id.tv_empty_weight_loss_message);

        tv_egg_weight_title1 = (TextView) findViewById(R.id.tv_egg_weight_title1);
        tv_egg_weight_title1.setVisibility(View.GONE);
        cv_egg_weight =  findViewById(R.id.cv_egg_weight);
        cv_egg_weight.setVisibility(View.GONE);

        btn_add_weight   = findViewById(R.id.btn_add_weight);
        btn_add_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eggDailyList.size() > 0) {
                    //startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggDailyAdd", eggDailyList.get(0))
                    //        .putExtra("eggBatch", eggBatch));
                    startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggBatch", eggBatch));
                } else {
                    startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggBatch", eggBatch));
                }

            }
        });

        /*fab_add_weight_loss_list  = findViewById(R.id.fab_add_weight_loss_list);
        fab_add_weight_loss_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eggDailyList.size() > 0) {
                    //startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggDailyAdd", eggDailyList.get(0))
                    //        .putExtra("eggBatch", eggBatch));
                    startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggBatch", eggBatch));
                } else {
                    startActivity(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class).putExtra("eggBatch", eggBatch));
                }

            }
        });*/
        //fab_add_weight_loss_list.setVisibility(View.GONE);

        button_show_hide_rejects  = findViewById(R.id.button_show_hide_rejects);
        button_show_hide_rejects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HIDE_REJECTS) {
                    HIDE_REJECTS = false;
                } else {
                    HIDE_REJECTS = true;
                }
                setRejectButtonText(HIDE_REJECTS);
                initializeViews();
                displayList();
            }
        });
        //button_show_hide_rejects.setVisibility(View.GONE);

        cv_egg_weight = findViewById(R.id.cv_egg_weight);

        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossListActivity.this);

       /* NUMBER_OF_REJECTED_EGGS = eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(EGG_BATCH_ID);
        if (NUMBER_OF_REJECTED_EGGS == null) {
            NUMBER_OF_REJECTED_EGGS = 0;
            button_show_hide_rejects.setEnabled(false);
        } else {
            button_show_hide_rejects.setEnabled(true);
        }*/

        recyclerViewWeightLossList = findViewById(R.id.recycler_view_weight_loss_list);
        mLinearLayoutManager = new LinearLayoutManager(WeightLossListActivity.this);
        recyclerViewWeightLossList.setLayoutManager(mLinearLayoutManager);
        eggDailyList = new ArrayList<>();
        eggWeightLossAdapter = new EggWeightLossAdapter(eggDailyList,  WeightLossListActivity.this);
        recyclerViewWeightLossList.setAdapter(eggWeightLossAdapter);

        recyclerViewWeightLossList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                final LinearLayout card = (LinearLayout) mLinearLayoutManager.findViewByPosition(0);
                // Check that the view exists for the item
                if (card != null) {
                    //showEggBatchListItemPrompt1(mLinearLayoutManager);
                    /*if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_2", false)) {
                        // The user hasn't seen the OnboardingSupportFragment yet, so show it
                        showWeightLossListItemPrompt(mLinearLayoutManager);
                        editor.putBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_2", true);
                        editor.commit();
                    }*/

                    if (!sharedpreferences.getBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_2", false)) {
                        // The user hasn't seen the OnboardingSupportFragment yet, so show it
                        showSequenceManual();
                        editor.putBoolean("COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST_2", true);
                        editor.commit();
                    }
                    recyclerViewWeightLossList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

    public void setRejectButtonText(Boolean hideRejects) {
        if (hideRejects) {
            button_show_hide_rejects.setText("Show Rejects");
        } else {
            button_show_hide_rejects.setText("Hide Rejects");
        }
    }

    public static void hideSoftKeyboard (AppCompatActivity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
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
                //listVisibility();

                //initializeViews();
                //displayList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEggWeightClick(int pos) {


        if (eggDailyListFiltered.size() != 0 && eggDailyListFiltered.size() < eggDailyList.size()) {
            eggDailyListOnClick = eggDailyListFiltered;
        } else {
            eggDailyListOnClick = eggDailyList;
        }

        new AlertDialog.Builder(WeightLossListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "Display Weight Loss Chart", "Reject/Restore Egg", "Cancel"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                if (HASHMAP_EGG_DAILY_ID_EGG_DAILY.size() > 0) {
                                    EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                    eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                    eggDailyList.remove(EGG_DAILY_ID_STATIC);
                                } else {
                                    EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                    eggDailyOnClick = eggDailyListOnClick.get(pos);
                                    eggDailyList.remove(pos);
                                }
                                try {
                                    eggWiseDatabse.getEggDailyDao().deleteEggDaily(eggDailyOnClick);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //listVisibility();
                                resetFilters();
                                initializeViews();
                                displayList();
                                break;
                            case 1:
                                if (HASHMAP_EGG_DAILY_ID_EGG_DAILY.size() > 0) {
                                    EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                    eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                } else {
                                    eggDailyOnClick =  eggDailyListOnClick.get(pos);
                                }
                                BATCH_LABEL = eggDailyListOnClick.get(pos).getBatchLabel();
                                startActivityForResult(
                                        new Intent(WeightLossListActivity.this,
                                                AddWeightLossActivity.class).putExtra("eggDailyUpdate", eggDailyOnClick).putExtra("eggBatch", eggBatch),
                                        100);

                                break;
                            case 2:
                                //EGG_DAILY_ID_STATIC = EGG_DAILY_LIST_STATIC.get(pos).getEggDailyID();
                                //EGG_DAILY_LIST_POS_STATIC = HASHMAP_EGG_DAILY_ID_EGG_DAILY_LIST_POS.get(EGG_DAILY_ID_STATIC);;
                                //WeightLossListActivity.this.EGG_DAILY_LIST_POS_STATIC = EGG_DAILY_LIST_POS_STATIC;
                                eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                WeightLossChartActivity.BATCH_LABEL = eggDailyList.get(EGG_DAILY_LIST_POS_STATIC).batchLabel;
                                WeightLossChartActivity.RETURN_ACTIVITY = "WeightLossListActivity";
                                startActivity(
                                        new Intent(WeightLossListActivity.this,
                                                WeightLossChartActivity.class).putExtra("eggBatch", eggBatch)
                                                .putExtra("eggDailyChart", eggDailyOnClick));
                                finish();
                                break;
                            case 3:
                                //EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                eggDailyOnClick = eggDailyListOnClick.get(pos);
                                if(eggDailyOnClick.getRejectedEgg() == 0){
                                    //reject this egg
                                    eggDailyOnClick.setRejectedEgg(1);
                                    rejectedEgg = eggDailyOnClick.getRejectedEgg();
                                    eggWiseDatabse.getEggDailyDao().updateEggDaily_RejectedEgg(rejectedEgg,
                                            eggDailyOnClick.getEggBatchID(), eggDailyOnClick.getEggLabel());
                                }else {
                                    //unreject this egg
                                    eggDailyOnClick.setRejectedEgg(0);
                                    rejectedEgg = eggDailyOnClick.getRejectedEgg();
                                    eggWiseDatabse.getEggDailyDao().updateEggDaily_RejectedEgg(rejectedEgg,
                                            eggDailyOnClick.getEggBatchID(), eggDailyOnClick.getEggLabel());
                                }
                                //NUMBER_OF_REJECTED_EGGS = eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(eggDailyOnClick.getEggBatchID());
                                //Integer numberOfRejectedEggs = eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(eggDailyOnClick.getEggBatchID());
                                //numberOfEggsRemaining = eggDailyOnClick.getNumberOfEggsRemaining() - numberOfRejectedEggs;
                                //eggWeightLossAdapter.notifyDataSetChanged();
                                //listVisibility();
                                resetFilters();
                                //eggWeightLossAdapter.notifyDataSetChanged();
                                initializeViews();
                                displayList();
                                break;
                            case 4:
                                displayList();
                                break;
                        }
                    }
                }).show();

    }

    public void showRejectsAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WeightLossListActivity.this);
        alertDialog.setTitle("All Eggs Rejected");
        String[] items = {"Show Rejected Eggs", "Restore All Rejects", "Cancel"};
        alertDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        HIDE_REJECTS = false;
                        setRejectButtonText(HIDE_REJECTS);
                        resetFilters();
                        initializeViews();
                        displayList();
                        break;
                    case 1:
                        eggWiseDatabse.getEggDailyDao().updateEggDaily_RejectedEgg(EGG_BATCH_ID);
                        HIDE_REJECTS = false;
                        setRejectButtonText(HIDE_REJECTS);
                        resetFilters();
                        initializeViews();
                        displayList();
                        break;
                    case 2:
                        resetFilters();
                        displayList();
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }


    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggDaily>> {

        private WeakReference<WeightLossListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(WeightLossListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggDaily> doInBackground(Void... voids) {
            //EGG_BATCH_ID = 0L;
            List<EggDaily> returnEggDaily = null;
            if (activityReference.get()!=null) {
                //return activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(EGG_BATCH_ID);
                switch (ORDER_BY_SELECTION) {
                    case 0:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(EGG_BATCH_ID);
                        break;
                    case 1:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_Label_DESC(EGG_BATCH_ID);
                        break;
                    case 2:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_DESC_Label_ASC(EGG_BATCH_ID);
                        break;
                    case 3:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_DESC_Label_DESC(EGG_BATCH_ID);
                    break;
                }
                NUMBER_OF_REJECTED_EGGS = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(EGG_BATCH_ID);

            } else {
                return returnEggDaily;
            }
            return returnEggDaily;
        }

        @Override
        protected void onPostExecute(List<EggDaily> eggDailyListPostEx) {

            //NUMBER_OF_REJECTED_EGGS = eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(EGG_BATCH_ID);
            if (NUMBER_OF_REJECTED_EGGS == null) {
                NUMBER_OF_REJECTED_EGGS = 0;
                activityReference.get().button_show_hide_rejects.setEnabled(false);
            } else {
                activityReference.get().button_show_hide_rejects.setEnabled(true);
            }

            if (eggDailyListPostEx!=null && eggDailyListPostEx.size()>0 ){

                if (HIDE_REJECTS) {
                    List<EggDaily> eggDailyListTemp = new ArrayList<>();
                    for (int i = 0; i < eggDailyListPostEx.size(); i++) {

                        if (eggDailyListPostEx.get(i).getRejectedEgg() == 0  ) {
                            eggDailyListTemp.add(eggDailyListPostEx.get(i));
                        }
                    }
                    eggDailyListPostEx.clear();
                    eggDailyListPostEx.addAll(eggDailyListTemp);
                    if (eggDailyListTemp.size() == 0) {
                        activityReference.get().showRejectsAlertDialog();
                    }
                }

                computeAveragesAndPercents(eggDailyListPostEx);

                activityReference.get().eggDailyList.clear();
                activityReference.get().eggDailyList.addAll(eggDailyListPostEx);
                activityReference.get().EGG_DAILY_LIST_STATIC = new ArrayList<>();
                activityReference.get().EGG_DAILY_LIST_STATIC.addAll(eggDailyListPostEx);
                // hides empty text view
                //activityReference.get().tv_egg_weight_title1.setVisibility(View.GONE);
                //activityReference.get().cv_egg_weight.setVisibility(View.GONE);
                //activityReference.get().listVisibility();
                /*if (activityReference.get().NUMBER_OF_EGGS == activityReference.get().NUMBER_OF_REJECTED_EGGS) {
                    activityReference.get().tv_egg_weight_title1.setVisibility(View.VISIBLE);
                    activityReference.get().cv_egg_weight.setVisibility(View.VISIBLE);
                    activityReference.get().button_show_hide_rejects.setEnabled(true);
                }*/
                activityReference.get().eggWeightLossAdapter.notifyDataSetChanged();

            } else {
                activityReference.get().listVisibility();
            }
        }

        private void computeAveragesAndPercents(List<EggDaily> eggDailyListPostEx) {
            Integer index = 0;
            EGG_WEIGHT_SUM = 0.0D;
            READING_DAY_NUMBER = 0;
            EGG_LABEL = "";
            String previousRejectedEggLabel = "";
            for (index = 0; index < eggDailyListPostEx.size(); index++) {

                //Integer rejectedEggCount = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_CountRejectedEggs(EGG_BATCH_ID);
                //Integer numberOfEggsRemaining =  eggDailyListPostEx.get(index).getNumberOfEggsRemaining() - rejectedEggCount;
                //eggDailyListPostEx.get(index).setNumberOfEggsRemaining(numberOfEggsRemaining);
                /*if (eggDailyListPostEx.get(index).getRejectedEgg() == 1) {
                    if (eggDailyListPostEx.get(index).getEggLabel() == previousRejectedEggLabel) {
                        //Do nothing.
                    } else { // another rejected egg
                        NUMBER_OF_REJECTED_EGGS += 1;
                        previousRejectedEggLabel = eggDailyListPostEx.get(index).getEggLabel();
                    }
                }*/


                READING_DAY_NUMBER = eggDailyListPostEx.get(index).getReadingDayNumber();
                EGG_LABEL = eggDailyListPostEx.get(index).getEggLabel();
                if(TRACKING_OPTION == 1) { //Track entire batch
                    EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(EGG_BATCH_ID));
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

                ACTUAL_WEIGHT_LOSS_PERCENT = 100D * ((EGG_WEIGHT_AVG_DAY_0 - EGG_WEIGHT_AVG_CURRENT) / EGG_WEIGHT_AVG_DAY_0);

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

                activityReference.get().eggWiseDatabse.getEggDailyDao().updateEggDaily_Sum_Averages_Percents(EGG_WEIGHT_SUM,
                        EGG_WEIGHT_AVG_CURRENT, EGG_WEIGHT_AVG_DAY_0, ACTUAL_WEIGHT_LOSS_PERCENT, TARGET_WEIGHT_LOSS_PERCENT,
                        WEIGHT_LOSS_DEVIATION, eggDailyListPostEx.get(index).getEggDailyID());

                EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = "";
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



    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (eggDailyList.size() == 0) { // no item to display
            if (tv_egg_weight_title1.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        } else {
            tv_egg_weight_title1.setVisibility(emptyMsgVisibility);
            cv_egg_weight.setVisibility(emptyMsgVisibility);
            eggWeightLossAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
