package com.example.lekhn.findyourdoctor.Hospital;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

/**
 * Created by Lekhn on 1/8/2018.
 */

public class HospitalRecyclerAdapter extends RecyclerView.Adapter<HospitalRecyclerAdapter.RecyclerViewHolder>{
    Context mContext;
    ArrayList<HospitalGetterSetter> arrayList = new ArrayList<>();
    HospitalRecyclerAdapter(Context context, ArrayList<HospitalGetterSetter> arrayList){
        this.arrayList = arrayList;
        this.mContext = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_list_display_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        HospitalGetterSetter hospitalGetterSetter = arrayList.get(position);
        holder.HospitalName.setText(hospitalGetterSetter.getHospital_names());
        holder.HospitalAddress.setText(hospitalGetterSetter.getHospital_address());
        Glide.with(mContext).load(hospitalGetterSetter.getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.hospital)
                .into(holder.HospitalImage);
        holder.TotalDoc.setText(hospitalGetterSetter.getTotal_doc());
        holder.TotalBed.setText(hospitalGetterSetter.getTotal_bed());
        holder.TotalIcu.setText(hospitalGetterSetter.getTotal_icu());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView HospitalName, HospitalAddress, TotalDoc, TotalBed, TotalIcu;
        ImageView HospitalImage;
        RecyclerViewHolder(View view){
            super(view);
            HospitalName = (TextView)view.findViewById(R.id.hospital_name_online);
            HospitalAddress = (TextView)view.findViewById(R.id.hospital_address_online);
            HospitalImage = (ImageView) view.findViewById(R.id.hospital_image_online);
            TotalDoc = view.findViewById(R.id.hospital_doctor_online);
            TotalBed = view.findViewById(R.id.hospital_beds_online);
            TotalIcu = view.findViewById(R.id.hospital_icus_online);
        }
    }

    public void setHospitalFilter(ArrayList<HospitalGetterSetter> newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged(); //refresh the adapter
    }
}
