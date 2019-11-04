package com.example.lekhn.findyourdoctor.Hospital;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

import java.util.ArrayList;

public class HospitalActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    Toolbar toolbar;
    RecyclerView recyclerView;
    HospitalRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HospitalGetterSetter> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        HospitalBackgroundTask hospitalBackgroundTask= new HospitalBackgroundTask(HospitalActivity.this);
        hospitalBackgroundTask.execute();

        recyclerView = findViewById(R.id.recycler_view_hospital_activity);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        HospitalDBHelper hospitalDBHelper= new HospitalDBHelper(this);
        SQLiteDatabase sqLiteDatabase = hospitalDBHelper.getReadableDatabase();
        Cursor cursor = hospitalDBHelper.get_hos_Data(sqLiteDatabase);

        while (cursor.moveToNext()){
            HospitalGetterSetter hospitalGetterSetter = new HospitalGetterSetter(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            arrayList.add(hospitalGetterSetter);
        }

        adapter = new HospitalRecyclerAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);

        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Hospital");
        ImageView imageView=(ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HospitalActivity.this, MainActivity.class);
                startActivity(intent);
                HospitalActivity.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(HospitalActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<HospitalGetterSetter> newList = new ArrayList<>();
        for (HospitalGetterSetter hospitalGetterSetter : arrayList){
            String name = hospitalGetterSetter.getHospital_names().toLowerCase();
            if (name.contains(newText))
                newList.add(hospitalGetterSetter);
        }
        adapter.setHospitalFilter(newList);
        return true;
    }
}