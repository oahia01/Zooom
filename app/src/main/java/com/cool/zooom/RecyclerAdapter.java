package com.cool.zooom;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Oghenefego on 9/19/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<TransportData> transportDataList;

    public RecyclerAdapter(List<TransportData> transportDataList1) {
        this.transportDataList = transportDataList1;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TransportData transportData = transportDataList.get(position);
        holder.cost.setText(transportData.cost);
        holder.walking_time.setText(transportData.walking_time);
        holder.transport_type.setText(transportData.transport_type);
        holder.travel_time.setText(transportData.travel_time);
        holder.transport_type.setText(transportData.transport_type);
        holder.icon.setImageResource(transportData.imageRes);
        if (transportData.transport_type.equals("Uber")){
            holder.great_deal.setVisibility(View.VISIBLE);
            holder.launchUberApp.setVisibility(View.VISIBLE);
        } else {
            holder.great_deal.setVisibility(View.INVISIBLE);
            holder.launchUberApp.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return transportDataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView transport_type;
        TextView cost;
        TextView walking_time;
        TextView travel_time;
        TextView great_deal;
        TextView launchUberApp;
        ImageView icon;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cost = (TextView) itemView.findViewById(R.id.cost);
            transport_type = (TextView) itemView.findViewById(R.id.transport_type);
            walking_time = (TextView) itemView.findViewById(R.id.walking_time);
            travel_time = (TextView) itemView.findViewById(R.id.travel_time);
            icon = (ImageView) itemView.findViewById(R.id.type_icon);
            great_deal = (TextView) itemView.findViewById(R.id.textView_greatDeal);
            launchUberApp = (TextView) itemView.findViewById(R.id.textView_launchUberApp);
        }
    }
}
