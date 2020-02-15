package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggBatchAdapter extends RecyclerView.Adapter<EggBatchAdapter.BeanHolder> implements Filterable {

    private List<EggBatch> listEggBatch;
    private List<EggBatch> listEggBatchFiltered;
    private List<EggDaily> listEggDaily;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggBatchItemClick onEggBatchItemClick;

    public EggBatchAdapter(List<EggBatch> listEggBatch, Context context) {
    //public EggBatchAdapter(List<EggBatch> listEggBatch, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.listEggBatch = listEggBatch;
        //this.listEggDaily = listEggDaily;
        this.context = context;
        this.onEggBatchItemClick = (OnEggBatchItemClick) context;
    }


    @Override
    public EggBatchAdapter.BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_batch,parent,false);
        return new EggBatchAdapter.BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EggBatchAdapter.BeanHolder holder, int position) {

        Log.e("bind", "onBindViewHolder: "+ listEggBatch.get(position));
        CharSequence styledTextTitle = "";
        CharSequence styledText = "";
        String currentTitle;


        String line1 = "";
        line1 = "<B>Batch Label:</B> " + listEggBatch.get(position).getBatchLabel() +
                ", <B>Number Of Eggs:</B> " + listEggBatch.get(position).getNumberOfEggs().toString() +
                ", <B>Common Name:</B> " + listEggBatch.get(position).getCommonName() +
                ", <B>Species Name:</B> " + listEggBatch.get(position).getSpeciesName() +
                ", <B>Incubator Name:</B> " + listEggBatch.get(position).getIncubatorName() +
                ", <B>Set Date:</B> " + listEggBatch.get(position).getSetDate() +
                ", <B>Hatch Date:</B> " + listEggBatch.get(position).getHatchDate() +
                ", <B>Location:</B> " + listEggBatch.get(position).getLocation() +
                ", <B>Incubator Settings:</B> " + listEggBatch.get(position).getIncubatorSettings() +
                ", <B>Incubator Temperature:</B> " + listEggBatch.get(position).getTemperature().toString() +
                ", <B>Incubation Days:</B> " + listEggBatch.get(position).getIncubationDays().toString() +
                ", <B>Number Of Eggs Hatched:</B> " + listEggBatch.get(position).getNumberOfEggsHatched().toString() +
                ", <B>Target Weight Loss %:</B> " + listEggBatch.get(position).getTargetWeightLoss().toString() +
                ", <B>Tracking Option:</B> ";
                if (listEggBatch.get(position).getTrackingOption() == 1) {
                    line1 += "Track entire batch";
                } else {
                    line1 += "Track specific eggs";
                }

        /*line1 += "<br>===<br>" +
                 "<B>Weight Sum:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getEggWeightSum()) +
                 ", <B>Weight Avg:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getEggWeightAverageCurrent()) +
                 ", <B>Actual Loss %:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getActualWeightLossPercent()) +
                 ", <B>Target Loss %:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getTargetWeightLossPercent()) +
                 ", <B>% Deviation:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getWeightLossDeviation());*/

        styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_batch_line1.setText(styledText);

        currentTitle = "<B>Batch: " + listEggBatch.get(position).getBatchLabel()  + "</B>";
        styledTextTitle = HtmlCompat.fromHtml(currentTitle, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_batch_title1.setText(styledTextTitle);

    }

    @Override
    public int getItemCount() {
        return listEggBatch.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listEggBatchFiltered = listEggBatch;
                } else {
                    List<EggBatch> filteredList = new ArrayList<>();
                    for (EggBatch row : listEggBatch) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBatchLabel().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    listEggBatchFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listEggBatchFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listEggBatchFiltered = (ArrayList<EggBatch>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_egg_batch_title1;
        TextView tv_egg_batch_line1, tv_egg_label, number_of_eggs, tv_common_name, tv_incubator, tv_set_date, tv_hatch_date,
                tv_location, tv_weight_loss_racking, tv_desired_weight_loss;
        CardView cv_egg_batch;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv_egg_batch = itemView.findViewById(R.id.cv_egg_batch);
            tv_egg_batch_title1 = itemView.findViewById(R.id.tv_egg_batch_title1);
            tv_egg_batch_line1  = itemView.findViewById(R.id.tv_egg_batch_line1);


        }

        @Override
        public void onClick(View view) {
            onEggBatchItemClick.onEggBatchClick(getAdapterPosition());
        }
    }

    public interface OnEggBatchItemClick{
        void onEggBatchClick(int pos);
    }
}
