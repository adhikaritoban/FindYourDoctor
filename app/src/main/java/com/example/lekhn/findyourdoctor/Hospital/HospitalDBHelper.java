package com.example.lekhn.findyourdoctor.Hospital;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lekhn on 1/8/2018.
 */

public class HospitalDBHelper extends SQLiteOpenHelper {

    public static final String DATABSE_NAME = "hospital_table.db";
    public static final String TABLE_NAME="hospital";
    public static final String CREATE_TABLE_QUERY="create table IF NOT EXISTS hospital(hospital_id PRIMARY_KEY TEXT, hospital_names TEXT, hospital_address TEXT, image_url TEXT, total_doc TEXT, total_bed TEXT, total_icu TEXT);";
    public static final String DROP_TABLE_QUERY = "drop table if exists hospital;";

    public HospitalDBHelper(Context context) {
        super(context,DATABSE_NAME,null,1);
        Log.d("Database created","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        Log.d("Table Created?","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
        Log.d("Table drop","Table drop");
    }

    public void insert_hos_Data(String hospital_id, String hospital_names, String hospital_address, String image_url, String total_doc, String total_bed, String total_icu, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put("hospital_id",hospital_id);
        contentValues.put("hospital_names",hospital_names);
        contentValues.put("hospital_address",hospital_address);
        contentValues.put("image_url",image_url);
        contentValues.put("total_doc",total_doc);
        contentValues.put("total_bed",total_bed);
        contentValues.put("total_icu",total_icu);
        long l = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        Log.d("Data insert","Inserted");
    }

    public Cursor get_hos_Data(SQLiteDatabase sqLiteDatabase){
        Cursor res=sqLiteDatabase.rawQuery("Select distinct * from hospital", null); //retrive all information and return to res
        return res;
    }

}
