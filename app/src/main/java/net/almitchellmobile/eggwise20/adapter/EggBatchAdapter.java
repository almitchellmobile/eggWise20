package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggBatch;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggBatchAdapter extends RecyclerView.Adapter<EggBatchAdapter.BeanHolder> {

    private List<EggBatch> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggBatchItemClick onEggBatchItemClick;

    public EggBatchAdapter(List<EggBatch> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onEggBatchItemClick = (OnEggBatchItemClick) context;
    }

   /* @Override
    public IncubatorsAdapter.BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_incubator,parent,false);
        return new IncubatorsAdapter.BeanHolder(view);
    }*/


    @Override
    public EggBatchAdapter.BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_batch,parent,false);
        return new EggBatchAdapter.BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EggBatchAdapter.BeanHolder holder, int position) {

        Log.e("bind", "onBindViewHolder: "+ list.get(position));

        String line1 = "";
        line1 = "<B>Batch Label:</B> " + list.get(position).getBatchLabel() +
                ", <B>Number Of Eggs:</B> " + list.get(position).getNumberOfEggs().toString() +
                ", <B>Common Name:</B> " + list.get(position).getCommonName() +
                ", <B>Species Name:</B> " + list.get(position).getSpeciesName() +
                ", <B>Incubator Name:</B> " + list.get(position).getIncubatorName() +
                ", <B>Set Date:</B> " + list.get(position).getSetDate() +
                ", <B>Hatch Date:</B> " + list.get(position).getHatchDate() +
                ", <B>Location:</B> " + list.get(position).getLocation() +
                ", <B>Tracking Option:</B> ";
                if (list.get(position).getTrackingOption() == 1) {
                    line1 += "Track entire batch";
                } else {
                    line1 += "Track specific eggs";
                }
                line1 += ", <B>Desired Weight Loss:</B> " + list.get(position).getDesiredWeightLoss().toString();

        CharSequence styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_batch_line1.setText(styledText);

        //textview.setText(styledText);

        /*TextView tvBatchLabel = new TextView(context);
        tvBatchLabel.setText("Batch Label: ");
        tvBatchLabel.setTypeface(tvBatchLabel.getTypeface(), Typeface.BOLD);
        holder.cv_egg_batch.addView(tvBatchLabel);

        TextView tvBatchLabelValue = new TextView(context);
        tvBatchLabelValue.setText(", Number of eggs: " + list.get(position).getEggLabel() );
        //tvBatchLabelValue.setTypeface(tvBatchLabelValue.getTypeface(), Typeface.BOLD);
        holder.cv_egg_batch.addView(tvBatchLabelValue);*/




        /*holder.tv_egg_label.setText("Batch label: " + list.get(position).getEggLabel());
        holder.number_of_eggs.setText(list.get(position).getNumberOfEggs().toString());
        holder.tv_common_name.setText(list.get(position).getCommonName());
        holder.tv_incubator.setText(list.get(position).getIncubatorName());
        holder.tv_set_date.setText(list.get(position).getSetDate());
        holder.tv_hatch_date.setText(list.get(position).getHatchDate());
        holder.tv_location.setText(list.get(position).getLocation());
        if (list.get(position).getTrackingOption() == 1) {
            holder.tv_weight_loss_racking.setText("Track Entire Batch");
        } else {
            holder.tv_weight_loss_racking.setText("Track Specific Eggs");
        }
        holder.tv_desired_weight_loss.setText(list.get(position).getDesiredWeightLoss().toString());*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_egg_batch_line1, tv_egg_label, number_of_eggs, tv_common_name, tv_incubator, tv_set_date, tv_hatch_date,
                tv_location, tv_weight_loss_racking, tv_desired_weight_loss;
        CardView cv_egg_batch;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv_egg_batch = itemView.findViewById(R.id.cv_egg_batch);
            tv_egg_batch_line1  = itemView.findViewById(R.id.tv_egg_batch_line1);

            /*tv_egg_label = itemView.findViewById(R.id.tv_egg_label);
            number_of_eggs = itemView.findViewById(R.id.tv_number_of_eggs);
            tv_common_name = itemView.findViewById(R.id.tv_common_name);
            tv_incubator = itemView.findViewById(R.id.tv_incubator);
            tv_set_date = itemView.findViewById(R.id.tv_set_date);
            tv_hatch_date = itemView.findViewById(R.id.tv_hatch_date);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_weight_loss_racking = itemView.findViewById(R.id.tv_weight_loss_tracking);
            tv_desired_weight_loss = itemView.findViewById(R.id.tv_desired_weight_loss);*/

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
