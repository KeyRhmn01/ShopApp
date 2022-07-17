package com.example.store.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.store.model.Products;

import java.util.ArrayList;

public class SqlCards extends SQLiteOpenHelper {

    String TABLE_NAME = "CARDS";

    public SqlCards(@Nullable Context context) {
        super(context, "cards.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Creat TABLE
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME +
                "( id INTEGER PRIMARY KEY , " +
                " name TEXT , " +
                " description TEXT , " +
                " price TXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //DELETE
    public void delete(int id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME, "id=?", new String[]{id+""});

    }

    //INSERT
    public void Insert(Integer id, String name, String descripsion, String price) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("description", descripsion);
        contentValues.put("price", price);
        database.insert(TABLE_NAME, null, contentValues);

    }

    //SELECT
    public ArrayList<Products> getData(){

        ArrayList<Products> productsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (c.moveToFirst()){
            do {
                // Passing values
                int id = c.getInt(0);
                String name = c.getString(1);
                String desc = c.getString(2);
                String price = c.getString(3);

                Products product = new Products(id,name,price,desc);
                productsList.add(product);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        return productsList;


    }

    //SELECT
    public boolean getById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select id from CARDS where id = ?", new String[]{id+""});
        if (c.moveToFirst()){
            return true;
        }
        c.close();

        return false;
    }

}
