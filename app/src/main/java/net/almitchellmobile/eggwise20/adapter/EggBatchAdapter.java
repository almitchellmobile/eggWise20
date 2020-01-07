package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.database.model.EggSetting;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EggBatchAdapter extends RecyclerView.Adapter<EggBatchAdapter.BeanHolder> {

    private List<EggSetting> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggBatchItemClick onEggBatchItemClick;

    public EggBatchAdapter(List<EggSetting> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onEggBatchItemClick = (OnEggBatchItemClick) context;
    }


    @Override
    public EggBatchAdapter.BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EggBatchAdapter.BeanHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewContent;
        TextView textViewTitle;
        //CardView cardView;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //cardView = itemView.findViewById(R.id.cardView);
            //textViewContent = itemView.findViewById(R.id.item_text);
            //textViewTitle = itemView.findViewById(R.id.tv_title);
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
