package com.ousl.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_STUDENT = "student.db";
    public static final String TABLE_STUDENT = "student_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_COURSE = "COURSE";
    public static final String COLUMN_MARKS = "MARKS";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_STUDENT, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENT_TABLE_QUERY = "CREATE TABLE " + TABLE_STUDENT + " (" + COLUMN_ID + " INTEGER "
                + "PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COURSE + " TEXT, "
                + COLUMN_MARKS + " INT )";

        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertData(String name, String course, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COURSE, course);
        contentValues.put(COLUMN_MARKS, marks);

        long result = db.insert(TABLE_STUDENT, null, contentValues);

        if(result == 1){
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);
        return result;
    }
    public boolean updateData(String id, String name, String course, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COURSE, course);
        contentValues.put(COLUMN_MARKS, marks);

        long count = db.update(TABLE_STUDENT, contentValues, "ID = ? ", new String[] {id});

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    public  int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENT, "ID = ? ", new String[] {id});
    }
}