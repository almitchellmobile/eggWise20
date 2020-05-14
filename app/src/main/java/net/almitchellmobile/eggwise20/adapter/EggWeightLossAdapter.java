package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.WeightLossListActivity;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggWeightLossAdapter extends RecyclerView.Adapter <EggWeightLossAdapter.BeanHolder>  {

    //public static Integer NUMBER_OF_REJECTED_EGGS = 0;

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    private List<EggDaily> eggDailyList;
    private List<EggDaily> eggDailyListFiltered;

    private EggDaily eggDaily;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggWeightItemClick onEggWeightItemClick;

    public static Boolean warnAboutDeviation = false;
    public static String warnAboutDeviationValue = "";
    public static Double warningDeviationPercentAmount = 0.0;
    public static Integer numberOfReadings = 0;
    public static String SPECIES_BREED = "";
    public static Integer TAXON_INCUBATION_DAYS = 0;
    public static String WEIGHT_LOSS_DEVIATION_MESSAGE = "";

    public Integer eggsRemaining = -1;
    public Integer numberOfEggs = 0;
    //public Integer numberOfRejectedEggs = 0;
    public Integer previousReadingDayNumber = 0;
    public Double previousEggWeightAverage = 0.0;
    public Double eggWeightAverage = 0.0;
    public Double eggWeightSum = 0.0;
    public Double actualWeightLossPercent = 0.0;
    public Double targetWeightLossPercent = 0.0;
    public Double computedTargetWeightLossPercent = 0.0;
    public Integer incubationPeriod = 0;
    public Integer readingDayNumber = 0;
    public Double eggWeight = 0.0;
    public Double eggWeightWarningDeviationPercent = 0.0;
    public Double eggWeightPercentDeviationAmount = 0.0;
    Integer rejectedEgg = 0;

    String previousTitle = "";
    String currentTitle = "";

    public Map<String, Double> eggWeightDaySumsMap = new HashMap<String, Double>();
    public Map<String, String> eggWeightAveragesMap = new HashMap<String, String>();
    public Map<String, String> actualWeightLossPercentsMap = new HashMap<String, String>();
    public Map<String, String> targetWeightLossPercentMap = new HashMap<String, String>();
    public Map<String, String> weightPercentDeviationAmountMap = new HashMap<String, String>();

    public String key = "";
    public String previousKey = "";
    String line1 = "";

    EggWiseDatabse eggWiseDatabse;


    Common common;


    public EggWeightLossAdapter(List<EggDaily> eggDailyList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.eggDailyList = eggDailyList;
        this.context = context;
        this.onEggWeightItemClick = (OnEggWeightItemClick) context;
        //this.eggWiseDatabse = eggWiseDatabse;
        //this.NUMBER_OF_REJECTED_EGGS = numberOfRejectedEggs;
        common = new Common();
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));
        } else {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.5D;
        }
    }

    @Override
    public BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_weight,parent,false);
        return new BeanHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BeanHolder holder, int position) {


        try {

            CharSequence styledTextTitle = "";
            CharSequence styledText = "";
            ColorStateList oldColors =    holder.tv_egg_weight_title1.getTextColors(); //save original colors

            currentTitle = "<B>Day: " + common.zeroIfNullInteger(eggDailyList.get(position).getReadingDayNumber())  + "</B>" +
                    ", <B>Egg: " + eggDailyList.get(position).getEggLabel() + "</B>" +
                    ", <B># Eggs Remaining = </B> " + common.zeroIfNullInteger(eggDailyList.get(position).getNumberOfEggsRemaining() - WeightLossListActivity.NUMBER_OF_REJECTED_EGGS);
            holder.tv_egg_weight_title1.setTextColor(oldColors);
            if (eggDailyList.get(position).getRejectedEgg() == 1) {
                //holder.cv_egg_weight.setCardBackgroundColor(Color.RED);
                holder.tv_egg_weight_title1.setTextColor(Color.RED);
                currentTitle += "  <B>+++ REJECTED EGG +++</B>";
            } else {
                //holder.cv_egg_weight.setCardBackgroundColor(Color.TRANSPARENT);
            }
            styledTextTitle = HtmlCompat.fromHtml(currentTitle, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_title1.setText(styledTextTitle);

            /*line1 = "<B>Batch Label:</B> " + common.blankIfNullString(eggDailyList.get(position).getBatchLabel()) +
                    ", <B>Reading Day:</B> " + common.zeroIfNullInteger(eggDailyList.get(position).getReadingDayNumber()) +*/

            //line1 = "<B>Label:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggLabel()) +
            line1 = "<B>Weight:</B> " + common.zeroIfNullDouble(eggDailyList.get(position).getEggWeight()) +
                    ", <B>Set Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getSetDate()) +
                    //", <B>Incubator:</B> " + common.blankIfNullString(eggDailyList.get(position).getIncubatorName()) +
                    ", <B>Reading Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getReadingDate()) +
                    ", <B>Comment:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggDailyComment());

            if (isTablet(context)) {
                line1 += "<br>===<br>" +
                        //"<B> Weight Sum:</B> " + String.format(Locale.getDefault(), "%.1f", eggDailyList.get(position).getEggWeightSum()) +
                        "<B>Weight Avg:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getEggWeightAverageCurrent()) +
                        ", <B>Actual Loss %:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getActualWeightLossPercent()) +
                        ", <B>Target Loss %:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getTargetWeightLossPercent()) +
                        ", <B>% Deviation:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getWeightLossDeviation());

            } else {
                line1 += "<br>===<br>" +
                        //"<B>Weight Sum:</B> " + String.format(Locale.getDefault(), "%.1f", eggDailyList.get(position).getEggWeightSum()) +
                        "<B>Weight Avg:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getEggWeightAverageCurrent()) +
                        ", <B>Actual Loss %:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getActualWeightLossPercent()) +
                        ", <B>Target Loss %:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getTargetWeightLossPercent()) +
                        ", <B>% Deviation:</B> " + String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getWeightLossDeviation());
            }

            //if (eggDailyList.get(position).getRejectedEgg() == 1) {
            //    holder.cv_egg_weight.setBackgroundColor(Color.RED);
            //} else {
                if (eggDailyList.get(position).getWeightLossDeviation() != null) {
                    if (Math.abs(eggDailyList.get(position).getWeightLossDeviation()) > Math.abs(Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE)) {
                        if (eggDailyList.get(position).getWeightLossDeviation() > Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE) {
                            line1 += "<br>===<br>";
                            line1 += "*** Warning: Actual weight loss is greater than target by ";
                            line1 += (String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getWeightLossDeviation())) + " percent, increase humidity. ";
                        } else if (eggDailyList.get(position).getWeightLossDeviation() < Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE) {
                            line1 += "<br>===<br>";
                            line1 += "*** Warning: Actual weight loss is less than target by ";
                            line1 += (String.format(Locale.getDefault(), "%.2f", eggDailyList.get(position).getWeightLossDeviation())) + " percent, reduce humidity. ";
                        }
                        //line1 += "Warn deviation amount = " + Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE + ". ***";
                        holder.cv_egg_weight.setCardBackgroundColor(Color.YELLOW);
                    } else {
                        holder.cv_egg_weight.setCardBackgroundColor(Color.TRANSPARENT);
                    }
                }
            //}


            styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_line1.setText(styledText);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return eggDailyList.size();
    }

    public void filterEggDailyList(ArrayList<EggDaily> eggDailyListFiltered) {
        this.eggDailyList = eggDailyListFiltered;
        notifyDataSetChanged();
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Filterable {

        public TextView tv_egg_weight_line1;
        public TextView tv_egg_weight_title1;
        //CheckBox check_box_rejected_egg;
        public CardView cv_egg_weight;

        public BeanHolder(@NonNull View itemView) {
            super(itemView);

            tv_egg_weight_title1 = itemView.findViewById(R.id.tv_egg_weight_title1);
            cv_egg_weight = itemView.findViewById(R.id.cv_egg_weight);
            tv_egg_weight_line1  = itemView.findViewById(R.id.tv_egg_weight_line1);
            //check_box_rejected_egg = itemView.findViewById(R.id.check_box_rejected_egg);
            //check_box_rejected_egg.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEggWeightItemClick.onEggWeightClick(getAdapterPosition()

            );}

        @Override
        public Filter getFilter() {
            return null;
        }
    }

    public interface OnEggWeightItemClick{
        void onEggWeightClick(int pos);
    }



}
