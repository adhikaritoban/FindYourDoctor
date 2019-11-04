package com.example.lekhn.findyourdoctor.Doctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.RecyclerViewHolder> {
    Context mContext;
    ArrayList<DoctorGetterSetter> arrayList = new ArrayList<>();

    DoctorRecyclerAdapter(Context context, ArrayList<DoctorGetterSetter> arrayList){
        this.arrayList = arrayList;
        this.mContext = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_display_layout,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
    DoctorGetterSetter doctorGetterSetter = arrayList.get(position);
        holder.DoctorName.setText(doctorGetterSetter.getDoctor_name());
        holder.Specialist.setText(doctorGetterSetter.getSpecialist());
        holder.DoctorHospital.setText(doctorGetterSetter.getHospital_name());
        Log.d("Recycler","imageurl:::"+doctorGetterSetter.getImage_url());
        Glide.with(mContext).load(doctorGetterSetter.getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.doctor)
                .into(holder.DoctorImage);

        holder.DoctorComment.setText(doctorGetterSetter.getComment());
        holder.DoctorContact.setText(doctorGetterSetter.getContact());
        holder.DoctorGmail.setText(doctorGetterSetter.getGmail());
        holder.Sunday.setText(doctorGetterSetter.getSunday());
        holder.Monday.setText(doctorGetterSetter.getMonday());
        holder.Tuesday.setText(doctorGetterSetter.getTuesday());
        holder.Wednesday.setText(doctorGetterSetter.getWednesday());
        holder.Thursday.setText(doctorGetterSetter.getThursday());
        holder.Friday.setText(doctorGetterSetter.getFriday());
        holder.Saturday.setText(doctorGetterSetter.getSaturday());
    }
    public void setFilter(ArrayList<DoctorGetterSetter> newList){
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged(); //refresh the adapter
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView DoctorName, Specialist, DoctorHospital, DoctorComment, DoctorContact, DoctorGmail,
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
        ImageView DoctorImage;
        CardView doctor_list;

        RecyclerViewHolder(View view){
            super(view);
            DoctorName = view.findViewById(R.id.doctor_name_online);
            Specialist = view.findViewById(R.id.doctor_specialist_online);
            DoctorHospital = view.findViewById(R.id.doctor_hospital_online);
            DoctorImage = view.findViewById(R.id.doctor_image_online);
            DoctorComment = view.findViewById(R.id.doctor_comment_online);
            DoctorContact = view.findViewById(R.id.doctor_contact_online);
            DoctorGmail = view.findViewById(R.id.doctor_gmail_online);
            Sunday = view.findViewById(R.id.sunday);
            Monday = view.findViewById(R.id.monday);
            Tuesday = view.findViewById(R.id.tuesday);
            Wednesday = view.findViewById(R.id.wednesday);
            Thursday = view.findViewById(R.id.thursday);
            Friday = view.findViewById(R.id.friday);
            Saturday = view.findViewById(R.id.saturday);

            doctor_list = view.findViewById(R.id.doctor_list_cardview);
            doctor_list.setClickable(true);
            doctor_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent;
                    intent =  new Intent(view.getContext(), Doctor_Profile_Activity.class);
                    intent.putExtra("doctor_name", DoctorName.getText().toString());
                    intent.putExtra("doctor_specialist", Specialist.getText().toString());
                    intent.putExtra("doctor_hospital", DoctorHospital.getText().toString());
                    intent.putExtra("doctor_comment", DoctorComment.getText().toString());
                    intent.putExtra("doctor_contact", DoctorContact.getText().toString());
                    intent.putExtra("doctor_gmail", DoctorGmail.getText().toString());
                    intent.putExtra("sunday",Sunday.getText().toString());
                    intent.putExtra("monday",Monday.getText().toString());
                    intent.putExtra("tuesday",Tuesday.getText().toString());
                    intent.putExtra("wednesday",Wednesday.getText().toString());
                    intent.putExtra("thursday",Thursday.getText().toString());
                    intent.putExtra("friday",Friday.getText().toString());
                    intent.putExtra("saturday",Saturday.getText().toString());
                    DoctorImage.buildDrawingCache();
                    Bitmap bm = DoctorImage.getDrawingCache();
                    intent.putExtra("doctor_image",bm);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}