package com.example.vpp_android.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vpp_android.R;
import java.util.ArrayList;
import java.util.List;
import costs_classes.CostsData;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    List<CostsData> data = new ArrayList<>();
    LayoutInflater layoutInflater;
    ItemClickListener itemClickListener;

    public HistoryAdapter(Context context, List<CostsData> data) {
        layoutInflater = LayoutInflater.from(context);
        this.data.addAll(data);
    }

    public interface ItemClickListener{
        void onItemClick(View view, CostsData position);
    }

    public void setOnClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.productTextView.setText(Float.toString(data.get(position).getPrice()));
        holder.regionTextView.setText(Float.toString(data.get(position).getProduced()));
        holder.dateTextView.setText(Float.toString(data.get(position).getResidual_volume()));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView productTextView;

        public TextView regionTextView;

        public TextView dateTextView;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            productTextView = itemView.findViewById(R.id.card_view__product);
            regionTextView = itemView.findViewById(R.id.card_view__region);
            dateTextView = itemView.findViewById(R.id.card_view__date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, data.get(getAdapterPosition()));
        }
    }
}
