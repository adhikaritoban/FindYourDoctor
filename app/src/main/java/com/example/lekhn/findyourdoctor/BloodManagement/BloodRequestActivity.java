package com.example.lekhn.findyourdoctor.BloodManagement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

public class BloodRequestActivity extends AppCompatActivity {
    Toolbar toolbar;
    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<BloodRequestGetterSetter> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);

        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Blood Request");

        BackgroundTaskRequest backgroundTaskRequest = new BackgroundTaskRequest(this);
        backgroundTaskRequest.execute();

        recyclerView = findViewById(R.id.recycler_view_donor_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        BloodRequestDBHelper bloodRequestDBHelper = new BloodRequestDBHelper(this);
        SQLiteDatabase sqLiteDatabase = bloodRequestDBHelper.getReadableDatabase();
        Cursor cursor = bloodRequestDBHelper.getDonorData(sqLiteDatabase);
        cursor.moveToFirst();
        do{
         BloodRequestGetterSetter bloodRequestGetterSetter= new BloodRequestGetterSetter(cursor.getString(0),
                 cursor.getString(1),
                 cursor.getString(2),
                 cursor.getString(3),
                 cursor.getString(4));
         arrayList.add(bloodRequestGetterSetter);
        }
        while (cursor.moveToNext());
        adapter = new BloodRequestRecyclerAdapter(this,activity,arrayList);
        recyclerView.setAdapter(adapter);
        ImageView imageView=(ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BloodRequestActivity.this, MainActivity.class);
                startActivity(intent);
                BloodRequestActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(BloodRequestActivity.this, MainActivity.class);
        startActivity(intent);
        BloodRequestActivity.this.finish();
    }
}
