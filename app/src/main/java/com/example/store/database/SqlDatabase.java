package com.example.store.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.store.model.Products;

import java.util.ArrayList;

public class SqlDatabase extends SQLiteOpenHelper {

    String TABLE_NAME = "Products";

    public SqlDatabase(@Nullable Context context) {
        super(context, "mySql.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //CREAT TABLE
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME +
                "(ida INTEGER PRIMARY KEY , " +
                " name TEXT  , " +
                " descripsion TEXT  , " +
                " price TEXT , " +
                " image TXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
                String image = c.getString(4);
                Products product = new Products(id,name,price,desc, image);
                productsList.add(product);

                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();

        return productsList;


    }

    //SELECT Find ID
    public boolean getById(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select ida from Products where ida = ?", new String[]{id+""});
        if (c.moveToFirst()){
            return true;


        }
        c.close();

        return false;
    }

    //DELETE
    public void delete(int id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME, "ida=?", new String[]{id+""});

    }

    //INSERT
    public void Insert(Integer ida, String name, String descripsion, String price, String image) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ida", ida);
        contentValues.put("name", name);
        contentValues.put("descripsion", descripsion);
        contentValues.put("price", price);
        contentValues.put("image" , image);
        database.insert(TABLE_NAME, null, contentValues);

    }




}