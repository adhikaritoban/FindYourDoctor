package com.example.lekhn.findyourdoctor.BloodManagement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

/**
 * Created by Lekhn on 1/14/2018.
 */

public class BloodRequestRecyclerAdapter extends RecyclerView.Adapter<BloodRequestRecyclerAdapter.RecyclerViewHolder>{
    private final Context mContext;
    private final static int REQUEST_READ_SMS_PERMISSION = 3004;
    private int PHONE_PERMISSION_CODE = 03;
    ArrayList<BloodRequestGetterSetter> arrayList = new ArrayList<>();

    BloodRequestRecyclerAdapter(Context context, Activity activity, ArrayList<BloodRequestGetterSetter> arrayList) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_donor_list, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final BloodRequestGetterSetter bloodRequestGetterSetter = arrayList.get(position);
        holder.DonorName.setText(bloodRequestGetterSetter.getDonor_name());
        holder.DonorContact.setText(bloodRequestGetterSetter.getDonor_contact());
        holder.DonorGroup.setText(bloodRequestGetterSetter.getDonor_group());
        holder.DonorAddress.setText(bloodRequestGetterSetter.getDonor_address() + " - " + bloodRequestGetterSetter.getDonor_zone());
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + bloodRequestGetterSetter.getDonor_contact()));
                        mContext.startActivity(callIntent);
                        //mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bloodRequestGetterSetter.getDonor_contact())));
                    }else{
                        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CALL_PHONE)){
                            Toast.makeText(mContext, "App requires Phone Call permission.\nPlease allow that in the device settings.", Toast.LENGTH_LONG).show();
                        }
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_CODE);
                    }
                }else{
                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bloodRequestGetterSetter.getDonor_contact())));
                }
            }
        });
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"SMS",Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + bloodRequestGetterSetter.getDonor_contact()));
                        intent.putExtra("sms_body", "Hello "+bloodRequestGetterSetter.getDonor_name()+ ". I need " + bloodRequestGetterSetter.getDonor_group()+" blood group at this time. So, please help me if you can. Thank You");
                        mContext.startActivity(intent);

                    }else{
                        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.READ_SMS)){
                            Toast.makeText(mContext, "App requires SMS Read permission.\nPlease allow that in the device settings.", Toast.LENGTH_LONG).show();
                        }
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS_PERMISSION);
                    }
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + bloodRequestGetterSetter.getDonor_contact()));
                    intent.putExtra("sms_body", "Hello "+bloodRequestGetterSetter.getDonor_name()+ ". I need " + bloodRequestGetterSetter.getDonor_group()+" blood group at this time. So, please help me if you can. Thank You");
                    mContext.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView DonorName, DonorContact, DonorGroup, DonorAddress;
        ImageView phone,sms;
        RecyclerViewHolder(View view) {
            super(view);
            DonorName = view.findViewById(R.id.donor_list_name);
            DonorContact = view.findViewById(R.id.donor_list_contact);
            DonorGroup = view.findViewById(R.id.donor_list_blood);
            DonorAddress = view.findViewById(R.id.donor_list_address);
            phone = view.findViewById(R.id.call);
            sms = view.findViewById(R.id.sms);
        }
    }
}
