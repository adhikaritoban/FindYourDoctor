package com.example.lekhn.findyourdoctor.Doctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lekhn on 1/4/2018.
 */
public class DoctorDBHelper extends SQLiteOpenHelper {

    public static final String DATABSE_NAME = "doctor_table.db";
    public static final String TABLE_NAME="doctor";
    public static final String CREATE_TABLE_QUERY="create table IF NOT EXISTS doctor(doctor_id PRIMARY_KEY TEXT, doctor_name TEXT, specialist TEXT, hospital_name TEXT, image_url TEXT, comment TEXT, contact TEXT, gmail TEXT, sunday TEXT, monday TEXT, tuesday TEXT, wednesday TEXT, thursday TEXT, friday TEXT, saturday TEXT, disease_name TEXT);";
    public static final String DROP_TABLE_QUERY = "drop table if exists doctor;";

    public DoctorDBHelper(Context context) {
        super(context,DATABSE_NAME,null,1);
        Log.d("Database created","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        Log.d("Table Created?","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
        Log.d("Table drop","Table drop");
    }

    public void insertData(String doctor_id, String doctor_name, String specialist, String hospital_name,String image_url, String comment, String contact, String gmail, String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String disease_name, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctor_id",doctor_id);
        contentValues.put("doctor_name",doctor_name);
        contentValues.put("specialist",specialist);
        contentValues.put("hospital_name",hospital_name);
        contentValues.put("image_url",image_url);
        contentValues.put("comment",comment);
        contentValues.put("contact",contact);
        contentValues.put("gmail",gmail);
        contentValues.put("sunday",sunday);
        contentValues.put("monday",monday);
        contentValues.put("tuesday",tuesday);
        contentValues.put("wednesday",wednesday);
        contentValues.put("thursday",thursday);
        contentValues.put("friday",friday);
        contentValues.put("saturday",saturday);
        contentValues.put("disease_name",disease_name);
        long l = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        Log.d("Data insert","Inserted");
    }

    public Cursor getData(SQLiteDatabase sqLiteDatabase){
        Cursor res=sqLiteDatabase.rawQuery("Select distinct * from doctor", null); //retrive all information and return to res
        return res;
    }

}
