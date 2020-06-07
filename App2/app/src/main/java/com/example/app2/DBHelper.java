package com.example.app2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public final static int WERSJA_BAZY = 1;
    public final static String ID = "_id";
    public final static String NAZWA_BAZY = "nazwa_bazy";
    public final static String NAZWA_TABELI = "nazwa_tabeli";
    public final static String POLE1 = "pole_1";
    public final static String POLE2 = "pole_2";
    public final static String TW_BAZY = "CREATE TABLE " +
            NAZWA_TABELI + "(" + ID + " INTEGER primary key autoincrement, "
            + POLE1 + " text," + POLE2 + " text);";
    private static final String KAS_BAZY = "DROP TABLE IF EXISTS " + NAZWA_TABELI;

    public DBHelper(Context context) {
        super(context, NAZWA_BAZY, null, WERSJA_BAZY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TW_BAZY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KAS_BAZY);
        onCreate(db);
    }
}
