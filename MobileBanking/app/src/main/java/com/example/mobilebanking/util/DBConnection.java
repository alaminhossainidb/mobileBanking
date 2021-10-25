package com.example.mobilebanking.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mobilebanking.model.Balance;
import com.example.mobilebanking.model.Transactions;

import java.util.ArrayList;
import java.util.List;

public class DBConnection extends SQLiteOpenHelper {

    public static final String DBNAME = "DBMOBILEBANKING";
    public static final int VERSION = 1;

    public DBConnection(@Nullable Context context){
        super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table balance(id INTEGER PRIMARY KEY , cash INTEGER)";
        String trans = "create table transactions(id INTEGER PRIMARY KEY , account TEXT, time TEXT, amount TEXT, status TEXT, balance INTEGER)";

        db.execSQL(sql);
        db.execSQL(trans);
    }

    public Long addbalance(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cash",1000);

      return db.insert("balance",null,values);
    }

    public Balance getBalance(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from balance";
        Cursor c = db.rawQuery(sql, null);

        Balance b = new Balance();

        if (c.moveToFirst()){
        b.setId(Integer.parseInt(c.getString(0)));
        b.setBalance(c.getInt(1));
        }
        return b;
    }

    public void updateBalance(Balance b){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "update balance set cash = '"+b.getBalance()+"'";
        db.execSQL(sql);
        //Cursor c= db.rawQuery(sql, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public Long addMoney(Transactions t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", t.getAccount());
        values.put("time", t.getTime());
        values.put("amount", String.valueOf(t.getAmount()));
        values.put("status", t.getStatus());
        values.put("balance", t.getCurrentBalance());

        Long l = db.insert("transactions",null,values);
        return l;
    }

    public List<Transactions> getAllTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from transactions";
        Cursor c = db.rawQuery(sql, null);

        List<Transactions> transactions = new ArrayList<>();

        if(c.moveToFirst())
            do {
                Transactions t = new Transactions();
                    t.setTime(c.getString(2));
                    t.setAmount(c.getInt(3));
                    t.setStatus(c.getString(4));

                transactions.add(t);
            }while (c.moveToNext());
        return transactions;
    }
}
