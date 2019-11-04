package com.example.lekhn.findyourdoctor.BloodManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lekhn on 1/14/2018.
 */

public class BloodRequestDBHelper extends SQLiteOpenHelper {

    public static final String DATABSE_NAME = "donor_table.db";
    public static final String TABLE_NAME="donor";
    public static final String CREATE_TABLE_QUERY="create table IF NOT EXISTS donor(donor_name TEXT, donor_contact TEXT, donor_group TEXT, donor_zone TEXT, donor_address TEXT);";
    public static final String DROP_TABLE_QUERY = "drop table if exists donor;";

    public BloodRequestDBHelper(Context context) {
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
        this.onCreate(sqLiteDatabase);
    }

    public void insertDonorData(String donor_name, String donor_contact, String donor_group, String donor_zone,String donor_address, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put("donor_name",donor_name);
        contentValues.put("donor_contact",donor_contact);
        contentValues.put("donor_group",donor_group);
        contentValues.put("donor_zone",donor_zone);
        contentValues.put("donor_address",donor_address);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        Log.d("Data insert","Inserted");
    }

    public Cursor getDonorData(SQLiteDatabase sqLiteDatabase){
        Cursor res=sqLiteDatabase.rawQuery("Select distinct * from donor", null); //retrive all information and return to res
        return res;
    }
}
