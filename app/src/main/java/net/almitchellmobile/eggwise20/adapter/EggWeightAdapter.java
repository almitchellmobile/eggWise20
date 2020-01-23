package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggWeightAdapter extends RecyclerView.Adapter <EggWeightAdapter.BeanHolder>{

    private List<EggDaily> eggDailyList;
    private  EggDaily eggDaily;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggWeightItemClick onEggWeightItemClick;

    public static Boolean warnAboutDeviation = false;
    public static String warnAboutDeviationValue = "";
    public static Double eggWeightWarningDeviationPercentStatic = 0.0;
    public static Integer numberOfReadings = 0;
    public static String SPECIES_BREED = "";
    public static Integer TAXON_INCUBATION_DAYS = 0;

    public Integer eggsRemaining = -1;
    public Integer numberOfEggs = 0;
    public Integer sumEggsLost = 0;
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

    //public String previousKey;

    public Map<String, Double> eggWeightDaySumsMap = new HashMap<String, Double>();
    public Map<String, String> eggWeightAveragesMap = new HashMap<String, String>();
    public Map<String, String> actualWeightLossPercentsMap = new HashMap<String, String>();
    public Map<String, String> targetWeightLossPercentMap = new HashMap<String, String>();
    public Map<String, String> weightPercentDeviationAmountMap = new HashMap<String, String>();

    public String key = "";
    public String previousKey = "";


    public EggWeightAdapter(List<EggDaily> eggDailyList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.eggDailyList = eggDailyList;
        this.context = context;
        this.onEggWeightItemClick = (EggWeightAdapter.OnEggWeightItemClick) context;
    }

    @Override
    public EggWeightAdapter.BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_weight,parent,false);
        return new EggWeightAdapter.BeanHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BeanHolder holder, int position) {

        Log.e("bind", "onBindViewHolder: "+ eggDailyList.get(position));

        //Sum egg weights
        eggWeightSum += eggDailyList.get(position).getEggWeight();

        String line1 = "";
        line1 = "<B>Batch Label:</B> " + eggDailyList.get(position).getBatchLabel() +
                ", <B>Eggs Remaining:</B> " + eggDailyList.get(position).getNumberOfEggsRemaining() +
                ", <B>Set Date:</B> " + eggDailyList.get(position).getSetDate() +
                ", <B>Incubator Name:</B> " + eggDailyList.get(position).getIncubatorName() +
                ", <B>Egg Label:</B> " + eggDailyList.get(position).getEggLabel() +
                ", <B>Egg Weight:</B> " + eggDailyList.get(position).getEggWeight() +
                ", <B>Reading Date:</B> " + eggDailyList.get(position).getReadingDate() +
                ", <B>Reading Day Number Weight:</B> " + eggDailyList.get(position).getReadingDayNumber().toString() +
                ", <B>Comment:</B> " + eggDailyList.get(position).getEggDailyComment()
        ;

        CharSequence styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_weight_line1.setText(styledText);

    }

    @Override
    public int getItemCount() {
        return eggDailyList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_egg_weight_line1;
        CardView cv_egg_weight;


        public BeanHolder(@NonNull View itemView) {
            super(itemView);

            cv_egg_weight = itemView.findViewById(R.id.cv_egg_weight);
            tv_egg_weight_line1  = itemView.findViewById(R.id.tv_egg_weight_line1);
        }

        @Override
        public void onClick(View v) {
            onEggWeightItemClick.onEggWeightClick(getAdapterPosition());

        }
    }

    public interface OnEggWeightItemClick{
        void onEggWeightClick(int pos);
    }

    public Double computeActualWeightLossPercent() {
        Double _actualWeightLossPercent = 100*((previousEggWeightAverage-eggWeightAverage)/previousEggWeightAverage);
        return _actualWeightLossPercent;
    }

    public Double computeEggWeightAverage() {
        Double _eggWeightAverage = (eggWeightSum/numberOfEggs);
        return _eggWeightAverage;
    }



    private void computeAveragesAndPercents() {


        try {
            // Clear the existing array
            /*if (alEggDailys2.size() != 0) {
                alEggDailys2.clear();
            }*/

            if (eggDailyList.size() > 0) {
                Integer index = 0;
                eggWeightAverage = 0.0;
                previousReadingDayNumber = -1;
                previousKey = "";

                do {
                    eggDaily = new EggDaily();
                    eggDaily = eggDailyList.get(index);
                    readingDayNumber = eggDaily.getReadingDayNumber();
                    readingDayNumber = eggDaily.getReadingDayNumber();
                    eggWeightSum = eggDaily.getEggWeightSum();

                    key = eggDaily.getBatchLabel()
                            + "~" + eggDaily.getIncubatorName() + "~" + eggDaily.getReadingDayNumber();

                    if (previousReadingDayNumber == -1 && eggWeightAverage == 0.0) {
                        if (!eggWeightAveragesMap.containsKey(key)) {
                            eggWeightSum = eggWeightDaySumsMap.get(key);
                            eggWeightAverage = eggWeightSum/numberOfEggs;
                            eggWeightAveragesMap.put(key, String.format("%.1f", eggWeightAverage));
                        }
                        previousEggWeightAverage = eggWeightAverage;
                        previousReadingDayNumber = eggDaily.getReadingDayNumber();
                        previousKey = key;
                    } else if (previousReadingDayNumber == eggDaily.getReadingDayNumber()) {
                        if (!eggWeightAveragesMap.containsKey(key)) {
                            eggWeightSum = eggWeightDaySumsMap.get(key);
                            eggWeightAverage = eggWeightSum/numberOfEggs;
                            eggWeightAveragesMap.put(key, String.format("%.1f", eggWeightAverage));
                        }
                        previousKey = key;
                        previousEggWeightAverage = eggWeightAverage;
                        previousReadingDayNumber = eggDaily.getReadingDayNumber();
                    } else if (previousReadingDayNumber != eggDaily.getReadingDayNumber()) {
                        if (!eggWeightAveragesMap.containsKey(key)) {
                            if (eggWeightDaySumsMap.get(key) != null) {
                                eggWeightSum = eggWeightDaySumsMap.get(key);
                                eggWeightAverage = eggWeightSum/numberOfEggs;
                                eggWeightAveragesMap.put(key, String.format("%.1f", eggWeightAverage));
                            }
                        }
                        if (!(previousEggWeightAverage == 0.0 && previousEggWeightAverage == eggWeightAverage)) {

                            if (!actualWeightLossPercentsMap.containsKey(key)) {
                                actualWeightLossPercent = 100*((previousEggWeightAverage-eggWeightAverage)/previousEggWeightAverage);
                                actualWeightLossPercentsMap.put(key, String.format("%.1f", actualWeightLossPercent));

                                if (SPECIES_BREED.equalsIgnoreCase("NA")) {
                                    targetWeightLossPercentMap.put(key, String.format("%.1f", computedTargetWeightLossPercent));
                                    weightPercentDeviationAmountMap.put(key, String.format("%.1f", eggWeightPercentDeviationAmount));
                                } else {
                                    computedTargetWeightLossPercent =
                                            (targetWeightLossPercent*readingDayNumber/TAXON_INCUBATION_DAYS);
                                                    //getTaxonIncubationDaysFromProvider(speciesBreed, ctx));
                                    eggWeightPercentDeviationAmount = computedTargetWeightLossPercent - actualWeightLossPercent;
                                    targetWeightLossPercentMap.put(key, String.format("%.1f", computedTargetWeightLossPercent));
                                    weightPercentDeviationAmountMap.put(key, String.format("%.1f", Math.abs(eggWeightPercentDeviationAmount)));
                                }
                            }
                        }
                        previousKey = key;
                        previousReadingDayNumber = eggDaily.getReadingDayNumber();
                        //Set WeightSum to 0 and save previous average.
                        eggWeightSum = 0.0;
                        previousEggWeightAverage = eggWeightAverage;
                    }
                    eggDailyList.add(eggDaily);
                    index = index + 1;
                } while(index < eggDailyList.size());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
