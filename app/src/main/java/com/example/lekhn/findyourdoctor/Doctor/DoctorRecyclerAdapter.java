package com.example.lekhn.findyourdoctor.Doctor;

import android.app.Activity;
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
    Context context;
    ArrayList<DoctorGetterSetter> arrayList = new ArrayList<>();

    DoctorRecyclerAdapter(Context context, ArrayList<DoctorGetterSetter> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_display_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DoctorGetterSetter doctorGetterSetter = arrayList.get(position);
        holder.DoctorName.setText(doctorGetterSetter.getDoctor_name());
        holder.Specialist.setText(doctorGetterSetter.getSpecialist());
        holder.DoctorHospital.setText(doctorGetterSetter.getHospital_name());
        Log.d("Recycler", "imageurl:::" + doctorGetterSetter.getImage_url());
        Glide.with(context).load(doctorGetterSetter.getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.doctor)
                .into(holder.DoctorImage);

        holder.DoctorComment.setText(doctorGetterSetter.getComment());
        holder.DoctorContact.setText(doctorGetterSetter.getContact());
        holder.DoctorGmail.setText(doctorGetterSetter.getGmail());
        Log.d("Recycler doc" ,doctorGetterSetter.getDoctor_name());
    }

    public void setFilter(ArrayList<DoctorGetterSetter> newList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged(); //refresh the adapter
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView DoctorName, Specialist, DoctorHospital, DoctorComment, DoctorContact, DoctorGmail;
        /*
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;*/
        ImageView DoctorImage;
        CardView doctor_list;

        RecyclerViewHolder(View view) {
            super(view);
            DoctorName = view.findViewById(R.id.doctor_name_online);
            Specialist = view.findViewById(R.id.doctor_specialist_online);
            DoctorHospital = view.findViewById(R.id.doctor_hospital_online);
            DoctorImage = view.findViewById(R.id.doctor_image_online);
            DoctorComment = view.findViewById(R.id.doctor_comment_online);
            DoctorContact = view.findViewById(R.id.doctor_contact_online);
            DoctorGmail = view.findViewById(R.id.doctor_gmail_online);


            doctor_list = view.findViewById(R.id.doctor_list_cardview);
            doctor_list.setClickable(true);
            doctor_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent;
                    intent = new Intent(view.getContext(), Doctor_Profile_Activity.class);
                    intent.putExtra("doctor_name", DoctorName.getText().toString());
                    intent.putExtra("doctor_specialist", Specialist.getText().toString());
                    intent.putExtra("doctor_hospital", DoctorHospital.getText().toString());
                    intent.putExtra("doctor_comment", DoctorComment.getText().toString());
                    intent.putExtra("doctor_contact", DoctorContact.getText().toString());
                    intent.putExtra("doctor_gmail", DoctorGmail.getText().toString());

                    DoctorImage.buildDrawingCache();
                    Bitmap bm = DoctorImage.getDrawingCache();
                    intent.putExtra("doctor_image", bm);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}