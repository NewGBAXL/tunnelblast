package com.newgbaxl.blastmaze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.newgbaxl.blastmaze.Objects.CarSkin;

import java.util.ArrayList;
import java.util.List;

//import mobileapp.course.edu.sqlitetutorial2.Note;

/**
 * Adapted from an example at www.androidhive.info
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cars_db";

    public static final String TABLE_NAME = "cars";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UNLOCKED = "unlocked";
    public static final String COLUMN_PURCHACED = "purchaced";
    public static final String COLUMN_PRICE = "price";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_UNLOCKED + " INTEGER,"
                    + COLUMN_PURCHACED + " INTEGER,"
                    + COLUMN_PRICE + " INTEGER"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertCar(boolean unlocked,boolean purchaced, int price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //Need to store boolean values as integers
        int unlockedInt = (unlocked?1:0);
        int purchacedInt = (purchaced?1:0);


        values.put(COLUMN_UNLOCKED, unlockedInt);
        values.put(COLUMN_PURCHACED, purchacedInt);
        values.put(COLUMN_PRICE, price);
        //values.put(COLUMN_ID, 123);
        //values.put(COLUMN_TIMESTAMP, "March 14 9:00AM");

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public CarSkin getCar(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_UNLOCKED, COLUMN_PURCHACED, COLUMN_PRICE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        boolean unlocked = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_UNLOCKED)) > 0);
        boolean purchaced = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PURCHACED)) > 0);
        // prepare note object
        CarSkin car = new CarSkin(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                unlocked,
                purchaced,
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));

        // close the db connection
        cursor.close();
        db.close();

        return car;
    }

    public List<CarSkin> getAllCars() {
        List<CarSkin> cars = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_PRICE + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                boolean unlocked = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_UNLOCKED)) > 0);
                boolean purchaced = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PURCHACED)) > 0);
                CarSkin car = new CarSkin(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    unlocked,
                    purchaced,
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                cars.add(car);
            } while (cursor.moveToNext());
        }

        // close db connection
        cursor.close();
        db.close();

        // return notes list
        return cars;
    }

    /*public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }*/

    public int updateCar(CarSkin car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UNLOCKED, car.unlocked?1:0);
        values.put(COLUMN_PURCHACED, car.purchased?1:0);
        values.put(COLUMN_PRICE, car.price);

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(car.getId())});
    }

    /*public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }*/
}
