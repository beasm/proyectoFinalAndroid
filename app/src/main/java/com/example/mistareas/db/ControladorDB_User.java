package com.example.mistareas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControladorDB_User extends SQLiteOpenHelper {
    public ControladorDB_User(Context context) {
        super(context, "com.example.mistareas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIO (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, PASSWORD TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTarea(String usuario, String password) {
        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", usuario);
        registro.put("PASSWORD", password);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("USUARIO", null, registro);
        db.close();
    }

    public boolean validarLogin(String nombre, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID FROM USUARIO WHERE NOMBRE = ? AND PASSWORD = ?", new String[]{nombre, password});
        int regs = cursor.getCount();
        if (regs == 0) {
            return false;
        } else {
            return true;
        }
    }
}
