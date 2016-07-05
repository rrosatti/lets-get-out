package com.example.rodri.portugalproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 7/5/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "portugalProject.db";

    // Database version
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CURRENT_BALANCE = "current_balance";
    public static final String TABLE_EXPENSES = "expenses";

    // Common column name
    public static final String KEY_ID = "id";

    // current_balance columns names
    public static final String COLUMN_ESTIMATED_VALUE = "estimated_value";
    public static final String COLUMN_ACHIEVED_VALUE = "achieved_value";

    // expenses columns names
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DATE = "date";

    // current_balance table create
    public static final String CREATE_TABLE_CURRENT_BALANCE =
            "CREATE TABLE " + TABLE_CURRENT_BALANCE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ESTIMATED_VALUE + " REAL NOT NULL, "
            + COLUMN_ACHIEVED_VALUE + " REAL NOT NULL);";

    // expenses table create
    public static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE " + TABLE_EXPENSES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_VALUE + " REAL NOT NULL, "
            + COLUMN_DATE + " INTEGER NOT NULL);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CURRENT_BALANCE);
        db.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
            + ", which will destroy all old data.");
        if (oldVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_BALANCE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        }
    }
}
