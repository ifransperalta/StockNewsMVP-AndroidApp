package com.example.finalprojectstocknews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOP extends SQLiteOpenHelper{

    public static final String CUSTOMER_TABLE = "STOCKTICKER";
    public static final String TICKER = "TICKER";
    public static final String ID = "ID";

    public DatabaseOP(@Nullable Context context) {
        super(context, "ticker.db", null, 1);
    }

    // This section the create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TICKER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + CUSTOMER_TABLE );
        onCreate(db);
    }

    public boolean addOne(TickerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase(); // Writing
        ContentValues cv = new ContentValues();
        cv.put(TICKER, customerModel.getTicker());

        db.insert( CUSTOMER_TABLE, null, cv);
        return true;
    }

    public boolean DeleteEntry(TickerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteData = "DELETE FROM " + CUSTOMER_TABLE  + " WHERE " + ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(deleteData, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    private TickerModel getCustomerModel(TickerModel tickerModel) {
        return tickerModel;
    }


    public List<TickerModel> getEveryone(){
        List<TickerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE + " ORDER BY " +  ID + " DESC ";
        SQLiteDatabase db = this.getReadableDatabase();
        // result set
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loo through the cursor result
            do {
                int tickerID = cursor.getInt(0);
                String ticker = cursor.getString(1);

                TickerModel newCustomer = new TickerModel(tickerID, ticker);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());

        }else{
            // failure . nothing
        }

        cursor.close();
        db.close();
        return returnList;
    }
}