package com.example.rodri.letsgetout.database;

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
    public static final int DATABASE_VERSION = 4;

    // Table names
    public static final String TABLE_CURRENT_BALANCE = "current_balance";
    public static final String TABLE_EXPENSES = "expenses";
    public static final String TABLE_SAVINGS = "savings";
    public static final String TABLE_MONTHLY_BALANCE = "monthly_balance";

    // Common column name
    public static final String KEY_ID = "id";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";

    // current_balance columns names
    public static final String COLUMN_ESTIMATED_VALUE = "estimated_value";
    public static final String COLUMN_ACHIEVED_VALUE = "achieved_value";

    // expenses columns names
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";

    // savings columns names
    public static final String COLUMN_DESCRIPTION = "description";

    // monthly_balance column names
    public static final String COLUMN_TOTAL_EXPENSES = "total_expenses";
    public static final String COLUMN_TOTAL_SAVINGS = "total_savings";
    public static final String COLUMN_BALANCE = "balance";

    // current_balance table create
    public static final String CREATE_TABLE_CURRENT_BALANCE =
            "CREATE TABLE " + TABLE_CURRENT_BALANCE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ESTIMATED_VALUE + " REAL NOT NULL, "
            + COLUMN_ACHIEVED_VALUE + " REAL NOT NULL, "
            + KEY_DAY + " INTEGER NOT NULL, "
            + KEY_MONTH + " INTEGER NOT NULL, "
            + KEY_YEAR + " INTEGER NOT NULL);";


    // expenses table create
    public static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE " + TABLE_EXPENSES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_VALUE + " REAL NOT NULL, "
            + KEY_DAY + " INTEGER NOT NULL, "
            + KEY_MONTH + " INTEGER NOT NULL, "
            + KEY_YEAR + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_SAVINGS =
            "CREATE TABLE " + TABLE_SAVINGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_VALUE + " REAL NOT NULL, "
            + KEY_DAY + " INTEGER NOT NULL, "
            + KEY_MONTH + " INTEGER NOT NULL, "
            + KEY_YEAR + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_MONTHLY_BALANCE =
            "CREATE TABLE " + TABLE_MONTHLY_BALANCE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MONTH + " INTEGER NOT NULL, "
            + KEY_YEAR + " INTEGER NOT NULL, "
            + COLUMN_TOTAL_EXPENSES + " REAL NOT NULL, "
            + COLUMN_TOTAL_SAVINGS + " REAL NOT NULL, "
            + COLUMN_BALANCE + " REAL NOT NULL);";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CURRENT_BALANCE);
        db.execSQL(CREATE_TABLE_EXPENSES);
        db.execSQL(CREATE_TABLE_SAVINGS);
        db.execSQL(CREATE_TABLE_MONTHLY_BALANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
            + ", which will destroy all old data.");


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_BALANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLY_BALANCE);

        onCreate(db);


        System.out.println("I've been here! 2");
    }
}
