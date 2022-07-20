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
                " count INTERGER DEFAULT 1 , " +
                " price TXT , " +
                " tprice TXT)");

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



    // below is the method for updating our courses
    public void updateCourse(Integer id, String name, String descripsion, Integer count, String price, String tprice) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("description", descripsion);
        contentValues.put("count", count );
        contentValues.put("price", price);
        contentValues.put("tprice" , tprice);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, contentValues, "id=?", new String[]{id+""});
        db.close();
    }

    //INSERT
    public void Insert(Integer id, String name, String descripsion, Integer count, String price, String tprice) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("description", descripsion);
        contentValues.put("count", count );
        contentValues.put("price", price);
        contentValues.put("tprice" , tprice);
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
                int count = c.getInt(3);
                String price = c.getString(4);
                String tprice = c.getString(5);



                Products product = new Products(id,name,price,desc,count,tprice);
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
