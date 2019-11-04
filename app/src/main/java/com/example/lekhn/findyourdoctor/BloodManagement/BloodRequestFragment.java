package com.example.lekhn.findyourdoctor.BloodManagement;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

public class BloodRequestFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BloodRequestGetterSetter> arrayList = new ArrayList<>();
    public BloodRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blood_request,container,false);
        BackgroundTaskRequest backgroundTaskRequest = new BackgroundTaskRequest(getActivity());
        backgroundTaskRequest.execute();

        recyclerView = view.findViewById(R.id.recycler_view_donor_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        BloodRequestDBHelper bloodRequestDBHelper = new BloodRequestDBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = bloodRequestDBHelper.getReadableDatabase();
        Cursor cursor = bloodRequestDBHelper.getDonorData(sqLiteDatabase);

        while (cursor.moveToNext()){
            BloodRequestGetterSetter bloodRequestGetterSetter = new BloodRequestGetterSetter(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            arrayList.add(bloodRequestGetterSetter);
        }

        adapter = new BloodRequestRecyclerAdapter(getContext(),getActivity(),arrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
