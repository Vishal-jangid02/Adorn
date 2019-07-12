package com.example.vishal.Adorn.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.vishal.Adorn.MyPojo.orderpojo;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    public static final String database_name = "database_ad.db";
    public static final int DB_VAR = 2;


    public Database(Context context) {

        super(context, database_name, null, DB_VAR);
    }
    public List<orderpojo> getcarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productid", "productname", "qauntity", "price", "discount","wood","polish","phonenumber"};
        String sqlTable="orderDetail";

        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        final List<orderpojo> result=new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new orderpojo(c.getString(c.getColumnIndex("productid")),
                        c.getString(c.getColumnIndex("productname")),
                        c.getString(c.getColumnIndex("qauntity")),
                        c.getString(c.getColumnIndex("price")),
                        c.getString(c.getColumnIndex("discount")),
                        c.getString(c.getColumnIndex("wood")),
                        c.getString(c.getColumnIndex("polish")),
                        c.getString(c.getColumnIndex("phonenumber"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addTocart(orderpojo order)

    {
        SQLiteDatabase db=getWritableDatabase();
        //ContentValues contentValues=new ContentValues();
        String query=String.format("INSERT INTO orderDetail(productid,productname,qauntity,price,discount,wood,polish,phonenumber) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s');",
                order.getProductid(),
                order.getProductname(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getWoodType(),
                order.getPolish(),
                order.getEmail());
        db.execSQL(query);
    }

    public void cleanTocart()
    {
        SQLiteDatabase db=getWritableDatabase();
        String query= String.format("DELETE FROM orderDetail");
        db.execSQL(query);
    }



}

