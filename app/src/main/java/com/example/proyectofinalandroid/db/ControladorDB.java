package com.example.proyectofinalandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControladorDB extends SQLiteOpenHelper {
    public ControladorDB(Context context) {
        super(context, "com.example.proyectofinalandroid.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTarea(String tarea) {

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", tarea);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("TAREAS", null, registro);
//        db.execSQL("INSERT INTO TAREAS VALUES(null, ' + tarea + ')");
        db.close();
    }

    public String[] obtenerTareas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS", null);
        int regs = cursor.getCount();
        if (regs == 0) {
            db.close();
            return null;
        } else {
            String[] tareas = new String[regs];
            cursor.moveToFirst();
            for (int i = 0; i < regs; i++) {
                tareas[i]= cursor.getString(1);
                cursor.moveToNext();
            }
            db.close();
            return tareas;
        }
    }

    public int numeroRegistro() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS", null);
        return cursor.getCount();
    }

    public void borrarTarea(String tarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS","NOMBRE = ?", new String[]{tarea});
        db.close();
    }

    public void editarTarea(String nuevaTarea, String viejaTarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE",nuevaTarea);
        db.update("TAREAS",cv,"ID = ?", new String[]{obtenerIdTarea(viejaTarea,db)});
        db.close();
    }

    public String obtenerIdTarea(String tarea, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT ID FROM TAREAS WHERE NOMBRE = ?", new String[]{tarea});
        int regs = cursor.getCount();
        if (regs == 0) {
            return null;
        } else {
            String[] tareas = new String[regs];
            cursor.moveToFirst();
            for (int i = 0; i < regs; i++) {
                tareas[i]= cursor.getString(0);
                cursor.moveToNext();
            }
            return tareas[0];
        }
    }
}
