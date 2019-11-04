package com.example.lekhn.findyourdoctor.Doctor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    Toolbar toolbar;
    RecyclerView recyclerView;
    DoctorRecyclerAdapter doctorRecyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DoctorGetterSetter> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        DoctorBackgroundTask doctorBackgroundTask = new DoctorBackgroundTask(DoctorActivity.this);
        doctorBackgroundTask.execute();

        recyclerView = findViewById(R.id.recycler_view_doctor_activity);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        DoctorDBHelper doctorDBHelper = new DoctorDBHelper(this);
        SQLiteDatabase sqLiteDatabase = doctorDBHelper.getReadableDatabase();
        Cursor cursor = doctorDBHelper.getData(sqLiteDatabase);

        while (cursor.moveToNext()){
            DoctorGetterSetter doctorGetterSetter = new DoctorGetterSetter(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14), cursor.getString(15));
            arrayList.add(doctorGetterSetter);
        }

        //adapter = new DoctorRecyclerAdapter(this,arrayList);
        doctorRecyclerAdapter = new DoctorRecyclerAdapter(this,arrayList);
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(doctorRecyclerAdapter);

        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar
        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Doctor");

        ImageView imageView=(ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorActivity.this, MainActivity.class);
                startActivity(intent);
                DoctorActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(DoctorActivity.this, MainActivity.class);
        startActivity(intent);
        DoctorActivity.this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        s = s.toLowerCase();
        ArrayList<DoctorGetterSetter> newList = new ArrayList<>();
        for (DoctorGetterSetter doctorGetterSetter : arrayList){
            String name = doctorGetterSetter.getDoctor_name().toLowerCase();
            String disease = doctorGetterSetter.getDisease_name().toLowerCase();
            if (name.contains(s) || disease.contains(s))
                newList.add(doctorGetterSetter);
        }
        doctorRecyclerAdapter.setFilter(newList);
        return true;
    }
}