package com.example.rodri.portugalproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.portugalproject.model.CurrentBalance;
import com.example.rodri.portugalproject.model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            MySQLiteHelper.COLUMN_DAY,
            MySQLiteHelper.COLUMN_MONTH,
            MySQLiteHelper.COLUMN_YEAR
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

    public Expense createExpense(String name, float value, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.COLUMN_DAY, day);
        values.put(MySQLiteHelper.COLUMN_MONTH, month);
        values.put(MySQLiteHelper.COLUMN_YEAR, year);

        long insertId = database.insert(MySQLiteHelper.TABLE_EXPENSES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();

        return newExpense;
    }

    public Expense cursorToExpense(Cursor cursor) {
        Expense expense = new Expense();
        expense.setId(cursor.getLong(0));
        expense.setName(cursor.getString(1));
        expense.setValue(cursor.getFloat(2));
        expense.setDay(cursor.getInt(3));
        expense.setMonth(cursor.getInt(4));
        expense.setYear(cursor.getInt(5));
        return expense;
    }

    public List<Expense> getAllExpensesByMonth(int month, int year) {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.COLUMN_MONTH + " = " + month + " AND " + MySQLiteHelper.COLUMN_YEAR + " = " + year,
                null, null, null, null);
        cursor.moveToFirst();

        Expense expense;
        while (!cursor.isAfterLast()) {
            expense = cursorToExpense(cursor);
            expenses.add(expense);
            cursor.moveToNext();
        }

        return expenses;
    }

    public Expense getExpense(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();

        Expense expense = cursorToExpense(cursor);
        cursor.close();

        return expense;
    }

    public CurrentBalance getCurrentBalance(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CURRENT_BALANCE, currentBalanceColumns,
                MySQLiteHelper.KEY_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();

        CurrentBalance currentBalance = cursorToCurrentBalance(cursor);
        cursor.close();

        return currentBalance;
    }

}
