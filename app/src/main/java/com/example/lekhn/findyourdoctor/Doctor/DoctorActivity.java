package com.example.lekhn.findyourdoctor.Doctor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;
import com.example.lekhn.findyourdoctor.utilities.IParser;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DoctorRecyclerAdapter doctorRecyclerAdapter;
    ArrayList<DoctorGetterSetter> arrayList = new ArrayList<>();
    ProgressBar progressBarDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        recyclerView = findViewById(R.id.recycler_view_doctor_activity);
        progressBarDoc = findViewById(R.id.progressBarDoc);

        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Doctor");

        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorActivity.this, MainActivity.class);
                startActivity(intent);
                DoctorActivity.this.finish();
            }
        });

        new DoctorBackgroundTask(DoctorActivity.this, new IParser() {
            @Override
            public void onPreExecute() {
                progressBarDoc.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(ArrayList<?> resultArrayList) {
                progressBarDoc.setVisibility(View.GONE);
                Log.e("MainHome", "mainak_onPostExecute:  buisness ");
                arrayList = (ArrayList<DoctorGetterSetter>) resultArrayList;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("MainHome", "mainak_run:   buisness");


                        doctorRecyclerAdapter = new DoctorRecyclerAdapter(DoctorActivity.this, arrayList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(DoctorActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(doctorRecyclerAdapter);
                        doctorRecyclerAdapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onPostFailure() {

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DoctorActivity.this, MainActivity.class);
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
        for (DoctorGetterSetter doctorGetterSetter : arrayList) {
            String name = doctorGetterSetter.getDoctor_name().toLowerCase();
            String disease = doctorGetterSetter.getDisease_name().toLowerCase();
            if (name.contains(s) || disease.contains(s))
                newList.add(doctorGetterSetter);
        }
        doctorRecyclerAdapter.setFilter(newList);
        return true;
    }
}