package com.example.rodri.portugalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.portugalproject.model.CurrentBalance;

/**
 * Created by rodri on 7/5/2016.
 */
public class MyDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] currentBalanceColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_ESTIMATED_VALUE,
            MySQLiteHelper.COLUMN_ACHIEVED_VALUE
    };
    private String[] expensesColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_VALUE,
            MySQLiteHelper.COLUMN_DATE
    };

    public MyDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public CurrentBalance createCurrentBalance(float estimatedValue, float achievedValue) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESTIMATED_VALUE, estimatedValue);
        values.put(MySQLiteHelper.COLUMN_ACHIEVED_VALUE, achievedValue);

        long insertId = database.insert(MySQLiteHelper.TABLE_CURRENT_BALANCE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CURRENT_BALANCE, currentBalanceColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        CurrentBalance newCurrentBalance = cursorToCurrentBalance(cursor);
        cursor.close();

        return newCurrentBalance;

    }

    public CurrentBalance cursorToCurrentBalance(Cursor cursor) {
        CurrentBalance currentBalance = new CurrentBalance();
        currentBalance.setId(cursor.getLong(0));
        currentBalance.setEstimatedValue(cursor.getFloat(1));
        currentBalance.setAchievedValue(cursor.getFloat(2));
        return currentBalance;
    }

}
