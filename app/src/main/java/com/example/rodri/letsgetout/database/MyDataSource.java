package com.example.rodri.letsgetout.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.letsgetout.model.CurrentBalance;
import com.example.rodri.letsgetout.model.Expense;
import com.example.rodri.letsgetout.model.GenericBudget;
import com.example.rodri.letsgetout.model.Saving;

import java.util.ArrayList;
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
            MySQLiteHelper.COLUMN_ACHIEVED_VALUE,
            MySQLiteHelper.KEY_DAY,
            MySQLiteHelper.KEY_MONTH,
            MySQLiteHelper.KEY_YEAR
    };
    private String[] expensesColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_VALUE,
            MySQLiteHelper.KEY_DAY,
            MySQLiteHelper.KEY_MONTH,
            MySQLiteHelper.KEY_YEAR
    };
    private String[] savingsColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_VALUE,
            MySQLiteHelper.KEY_DAY,
            MySQLiteHelper.KEY_MONTH,
            MySQLiteHelper.KEY_YEAR
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

    /**
     *  ---------- CREATE ----------
     */

    public CurrentBalance createCurrentBalance(float estimatedValue, float achievedValue, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESTIMATED_VALUE, estimatedValue);
        values.put(MySQLiteHelper.COLUMN_ACHIEVED_VALUE, achievedValue);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);

        long insertId = database.insert(MySQLiteHelper.TABLE_CURRENT_BALANCE, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CURRENT_BALANCE, currentBalanceColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        CurrentBalance newCurrentBalance = cursorToCurrentBalance(cursor);
        cursor.close();

        return newCurrentBalance;

    }

    public Expense createExpense(String name, float value, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);

        long insertId = database.insert(MySQLiteHelper.TABLE_EXPENSES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();

        return newExpense;
    }

    public Saving createSaving(String description, float value, int day, int month, int year) {
        ContentValues values = new ContentValues();
        if (!description.isEmpty()) values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);

        long insertId = database.insert(MySQLiteHelper.TABLE_SAVINGS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_ID + " = " + insertId, null, null, null, null, null);

        // Update Achieved value of the current balance
        CurrentBalance currentBalance = getCurrentBalance(1);
        float newAchievedValued = currentBalance.getAchievedValue();
        newAchievedValued += value;
        updateCurrentBalance(currentBalance.getId(), currentBalance.getEstimatedValue(), newAchievedValued,
                currentBalance.getDay(), currentBalance.getMonth(), currentBalance.getYear());

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Saving saving = cursorToSaving(cursor);
        cursor.close();



        return saving;
    }


    /**
     * ---------- CURSOR TO ---------
     */

    public CurrentBalance cursorToCurrentBalance(Cursor cursor) {
        CurrentBalance currentBalance = new CurrentBalance();
        currentBalance.setId(cursor.getLong(0));
        currentBalance.setEstimatedValue(cursor.getFloat(1));
        currentBalance.setAchievedValue(cursor.getFloat(2));
        currentBalance.setDay(cursor.getInt(3));
        currentBalance.setMonth(cursor.getInt(4));
        currentBalance.setYear(cursor.getInt(5));
        return currentBalance;
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

    public Saving cursorToSaving(Cursor cursor) {
        Saving saving = new Saving();
        saving.setId(cursor.getLong(0));
        saving.setDescription(cursor.getString(1));
        saving.setValue(cursor.getFloat(2));
        saving.setDay(cursor.getInt(3));
        saving.setMonth(cursor.getInt(4));
        saving.setYear(cursor.getInt(5));
        return saving;
    }

    /**
     *  ---------- GET DATA --------
     */

    public List<Expense> getAllExpensesByMonth(int month, int year) {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_MONTH + " = " + month + " AND " + MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
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

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Expense expense = cursorToExpense(cursor);
        cursor.close();

        return expense;
    }

    public CurrentBalance getCurrentBalance(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CURRENT_BALANCE, currentBalanceColumns,
                MySQLiteHelper.KEY_ID + " = " + id,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        CurrentBalance currentBalance = cursorToCurrentBalance(cursor);
        cursor.close();

        return currentBalance;
    }

    public Saving getSaving(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_ID + " = " + id,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Saving saving = cursorToSaving(cursor);
        cursor.close();

        return saving;
    }

    public List<Saving> getAllSavings() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        List<Saving> savings = new ArrayList<>();
        while (cursor.isAfterLast()) {
            savings.add(cursorToSaving(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return savings;
    }

    public List<Saving> getAllSavingsByMonth(int month, int year) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_MONTH + " = " + month + " AND " + MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        List<Saving> savings = new ArrayList<>();
        while (cursor.isAfterLast()) {
            savings.add(cursorToSaving(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return savings;
    }

    public List<Saving> getAllSavingsByYear(int year) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        List<Saving> savings = new ArrayList<>();
        while (cursor.isAfterLast()) {
            savings.add(cursorToSaving(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return savings;
    }

    public List<Expense> getAllExpensesByYear(int year) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        if (isCursorEmpty(cursor)) {
            System.out.println("ERROR!! Cursor is empty!");
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        List<Expense> expenses = new ArrayList<>();
        while (cursor.isAfterLast()) {
            expenses.add(cursorToExpense(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return expenses;
    }

    public List<GenericBudget> getAllExpensesAndSavings() {
        Cursor cursorExpenses = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns, null, null, null, null, null);
        Cursor cursorSavings = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns, null, null, null, null, null);

        List<Expense> expenses = new ArrayList<>();
        List<Saving> savings = new ArrayList<>();
        List<GenericBudget> genericBudgets = new ArrayList<>();

        if (!isCursorEmpty(cursorExpenses)) {
            while (cursorExpenses.isAfterLast()) {
                expenses.add(cursorToExpense(cursorExpenses));
                cursorExpenses.moveToNext();
            }
            cursorExpenses.close();
            genericBudgets.addAll(expenses);
        }

        if (!isCursorEmpty(cursorSavings)) {
            cursorSavings.moveToFirst();
            while (cursorSavings.isAfterLast()) {
                savings.add(cursorToSaving(cursorSavings));
                cursorSavings.moveToNext();
            }
            cursorSavings.close();
            genericBudgets.addAll(savings);
        }

        if (genericBudgets.isEmpty())
            return null;

        return  genericBudgets;
    }

    public List<GenericBudget> getAllExpensesAndSavingsByMonth(int month, int year) {
        Cursor cursorExpenses = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_MONTH + " = " + month + " AND " + MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);
        Cursor cursorSavings = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_MONTH + " = " + month + " AND " + MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        List<Expense> expenses = new ArrayList<>();
        List<Saving> savings = new ArrayList<>();
        List<GenericBudget> genericBudgets = new ArrayList<>();

        if (!isCursorEmpty(cursorExpenses)) {
            cursorExpenses.moveToFirst();
            while (cursorExpenses.isAfterLast()) {
                expenses.add(cursorToExpense(cursorExpenses));
                cursorExpenses.moveToNext();
            }
            cursorExpenses.close();
            genericBudgets.addAll(expenses);
        }

        if (!isCursorEmpty(cursorSavings)) {
            cursorSavings.moveToFirst();
            while (cursorSavings.isAfterLast()) {
                savings.add(cursorToSaving(cursorSavings));
                cursorSavings.moveToNext();
            }
            cursorSavings.close();
            genericBudgets.addAll(savings);
        }

        if (genericBudgets.isEmpty())
            return null;


        return  genericBudgets;
    }

    public List<GenericBudget> getAllExpensesAndSavingsByYear(int year) {
        Cursor cursorExpenses = database.query(MySQLiteHelper.TABLE_EXPENSES, expensesColumns,
                MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);
        Cursor cursorSavings = database.query(MySQLiteHelper.TABLE_SAVINGS, savingsColumns,
                MySQLiteHelper.KEY_YEAR + " = " + year,
                null, null, null, null);

        List<Expense> expenses = new ArrayList<>();
        List<Saving> savings = new ArrayList<>();
        List<GenericBudget> genericBudgets = new ArrayList<>();

        if (!isCursorEmpty(cursorExpenses)) {
            cursorExpenses.moveToFirst();
            while (cursorExpenses.isAfterLast()) {
                expenses.add(cursorToExpense(cursorExpenses));
                cursorExpenses.moveToNext();
            }
            cursorExpenses.close();
            genericBudgets.addAll(expenses);
        }

        if (!isCursorEmpty(cursorSavings)) {
            cursorSavings.moveToFirst();
            while (cursorSavings.isAfterLast()) {
                savings.add(cursorToSaving(cursorSavings));
                cursorSavings.moveToNext();
            }
            cursorSavings.close();
            genericBudgets.addAll(savings);
        }

        if (genericBudgets.isEmpty())
            return null;

        return  genericBudgets;
    }


    /**
     * ---------- DELETE DATA ---------
     */

    public void deleteExpense(long id ) {
        System.out.println("The expense with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_EXPENSES, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteCurrentBalance(long id) {
        System.out.println("The current balance with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_CURRENT_BALANCE, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void deleteSaving(long id) {
        System.out.println("The saving with the id " + id + " will be deleted!");
        database.delete(MySQLiteHelper.TABLE_SAVINGS, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    /**
     * ---------- UPDATE DATA ---------
     */

    public void updateExpense(long id, String name, float value, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);
        database.update(MySQLiteHelper.TABLE_EXPENSES, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateCurrentBalance(long id, float estimatedValue, float achievedValue, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ESTIMATED_VALUE, estimatedValue);
        values.put(MySQLiteHelper.COLUMN_ACHIEVED_VALUE, achievedValue);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);
        database.update(MySQLiteHelper.TABLE_CURRENT_BALANCE, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }

    public void updateSaving(long id, String description, float value, int day, int month, int year) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.KEY_DAY, day);
        values.put(MySQLiteHelper.KEY_MONTH, month);
        values.put(MySQLiteHelper.KEY_YEAR, year);
        database.update(MySQLiteHelper.TABLE_SAVINGS, values, MySQLiteHelper.KEY_ID + " = " + id, null);
    }


    /**
     * --------- OTHER --------
     */

    public boolean isCursorEmpty(Cursor cursor) {
        if (cursor.moveToFirst())
            return false;
        else
            return true;
    }

}
