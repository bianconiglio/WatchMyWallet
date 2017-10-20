package se.mah.aliona.watchmywallet.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import se.mah.aliona.watchmywallet.beans.WalletBarcode;
import se.mah.aliona.watchmywallet.beans.Expenditure;

/**
 * Created by aliona on 2017-09-07.
 */

public class ExpenditureRepository {
    public static final int CURSOR_ID = 1;
    private DatabaseController ctrl;

    public ExpenditureRepository(DatabaseController ctrl) {
        this.ctrl = ctrl;
    }

    public long saveExpenditure(Expenditure exp) {
        SQLiteDatabase db = ctrl.openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.Exp.COLUMN_NAME_TITLE, exp.getExpTitle());
        values.put(Contract.Exp.COLUMN_NAME_COST, exp.getExpCost());
        values.put(Contract.Exp.COLUMN_NAME_DATE, exp.getExpDate());
        values.put(Contract.Exp.COLUMN_NAME_CATEGORY, exp.getExpCatId());
        return db.insert(Contract.Exp.TABLE_NAME, null, values);
    }

    public long saveBarcode(WalletBarcode barcode) {
        SQLiteDatabase db = ctrl.openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.Barcodes.COLUMN_NAME_BARCODE, barcode.getBarcodeNumber());
        values.put(Contract.Barcodes.COLUMN_NAME_PRODUCT_NAME, barcode.getProductName());
        values.put(Contract.Barcodes.COLUMN_NAME_INITIAL_PRICE, barcode.getInitialPrice());
        return db.insert(Contract.Barcodes.TABLE_NAME, null, values);
    }

    public void saveExpBarcode(int barcodeId, int expId) {
        SQLiteDatabase db = ctrl.openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.ExpBarcode.COLUMN_NAME_BARCODE_ID, barcodeId);
        values.put(Contract.ExpBarcode.COLUMN_NAME_EXPENDITURE_ID, expId);
        db.insert(Contract.ExpBarcode.TABLE_NAME, null, values);
    }

    public Cursor getExpenditureList() {
        SQLiteDatabase db = ctrl.openDatabase();
        String query = "SELECT exp." + Contract.Exp._ID +  ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_TITLE + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_COST + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_DATE + ", " +
                    "cats." + Contract.ExpCats.COLUMN_EXP_CAT_NAME + " " +
                " FROM " + Contract.Exp.TABLE_NAME +
                " exp INNER JOIN " +
                    Contract.ExpCats.TABLE_NAME + " cats ON exp." +
                    Contract.Exp.COLUMN_NAME_CATEGORY + " = cats." +
                    Contract.ExpCats._ID +
                " ORDER BY " + Contract.Exp.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, null);
        DatabaseUtils.dumpCursor(cursor);
        return cursor;
    }

    public Cursor getExpenditureList(int cat) {
        SQLiteDatabase db = ctrl.openDatabase();
        String[] params = new String[]{ String.valueOf(cat) };
        String query = "SELECT exp." + Contract.Exp._ID +  ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_TITLE + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_COST + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_DATE + ", " +
                    "cats." + Contract.ExpCats.COLUMN_EXP_CAT_NAME + " " +
                " FROM " + Contract.Exp.TABLE_NAME +
                " exp INNER JOIN " +
                    Contract.ExpCats.TABLE_NAME + " cats ON exp." +
                    Contract.Exp.COLUMN_NAME_CATEGORY + " = cats." +
                    Contract.ExpCats._ID +
                " WHERE cats." + Contract.ExpCats._ID + "=?" +
                " ORDER BY " + Contract.Exp.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, params);
//        DatabaseUtils.dumpCursor(cursor);
        return cursor;
    }

    public Cursor getExpenditureList(int cat, long start, long end) {
        SQLiteDatabase db = ctrl.openDatabase();
        String[] params = new String[]{ String.valueOf(cat), String.valueOf(start), String.valueOf(end) };
        String query = "SELECT exp." + Contract.Exp._ID +  ", " +
                        "exp." + Contract.Exp.COLUMN_NAME_TITLE + ", " +
                        "exp." + Contract.Exp.COLUMN_NAME_COST + ", " +
                        "exp." + Contract.Exp.COLUMN_NAME_DATE + ", " +
                        "cats." + Contract.ExpCats.COLUMN_EXP_CAT_NAME + " " +
                " FROM " + Contract.Exp.TABLE_NAME +
                    " exp INNER JOIN " +
                    Contract.ExpCats.TABLE_NAME + " cats ON exp." +
                    Contract.Exp.COLUMN_NAME_CATEGORY + " = cats." +
                    Contract.ExpCats._ID +
                " WHERE cats." + Contract.ExpCats._ID + "=? AND " +
                    Contract.Exp.COLUMN_NAME_DATE + ">=? AND " +
                    Contract.Exp.COLUMN_NAME_DATE + "<=?" +
                " ORDER BY " + Contract.Exp.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, params);
//        DatabaseUtils.dumpCursor(cursor);
        return cursor;
    }

    public Cursor getExpenditureList(long start, long end) {
        String[] params = new String[]{ String.valueOf(start), String.valueOf(end) };
        SQLiteDatabase db = ctrl.openDatabase();
        String query = "SELECT exp." + Contract.Exp._ID +  ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_TITLE + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_COST + ", " +
                    "exp." + Contract.Exp.COLUMN_NAME_DATE + ", " +
                    "cats." + Contract.ExpCats.COLUMN_EXP_CAT_NAME + " " +
                " FROM " + Contract.Exp.TABLE_NAME +
                " exp INNER JOIN " +
                    Contract.ExpCats.TABLE_NAME + " cats ON exp." +
                    Contract.Exp.COLUMN_NAME_CATEGORY + " = cats." +
                    Contract.ExpCats._ID +
                " WHERE " +
                    Contract.Exp.COLUMN_NAME_DATE + ">=? AND " +
                    Contract.Exp.COLUMN_NAME_DATE + "<=?" +
                " ORDER BY " + Contract.Exp.COLUMN_NAME_DATE + " DESC";

        Cursor cursor = db.rawQuery(query, params);
        return cursor;
    }

    public Expenditure getExpenditure(int id) {
        SQLiteDatabase db = ctrl.openDatabase();
        Expenditure exp = new Expenditure();
        String[] params = new String[]{String.valueOf(id) };
        String query = "SELECT exp." + Contract.Exp._ID +  ", " +
                            "exp." + Contract.Exp.COLUMN_NAME_TITLE + ", " +
                            "exp." + Contract.Exp.COLUMN_NAME_COST + ", " +
                            "exp." + Contract.Exp.COLUMN_NAME_DATE + ", " +
                            "cats." + Contract.ExpCats.COLUMN_EXP_CAT_NAME + " " +
                        "FROM " + Contract.Exp.TABLE_NAME +
                            " exp INNER JOIN " +
                        Contract.ExpCats.TABLE_NAME + " cats ON exp." +
                            Contract.Exp.COLUMN_NAME_CATEGORY + " = cats." +
                            Contract.ExpCats._ID +
                        " WHERE exp." +
                        Contract.Exp._ID + " = ?";

        Cursor cursor = db.rawQuery(query, params);

        cursor.moveToFirst();
        exp.setExpID(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Exp._ID)));
        exp.setExpTitle(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Exp.COLUMN_NAME_TITLE)));
        exp.setExpCost(cursor.getDouble(cursor.getColumnIndexOrThrow(Contract.Exp.COLUMN_NAME_COST)));
        exp.setExpDate(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.Exp.COLUMN_NAME_DATE)));
        exp.setExpCat(cursor.getString(cursor.getColumnIndexOrThrow(Contract.ExpCats.COLUMN_EXP_CAT_NAME)));

        DatabaseUtils.dumpCursor(cursor);
        cursor.close();
        return exp;
    }

    public WalletBarcode getBarcode(long barcodeNumber) {
        SQLiteDatabase db = ctrl.openDatabase();
        WalletBarcode barcode = new WalletBarcode();
        String[] params = new String[]{String.valueOf(barcodeNumber) };
        String query =
                "SELECT * FROM " + Contract.Barcodes.TABLE_NAME +
                        " WHERE " + Contract.Barcodes.COLUMN_NAME_BARCODE + " =?";
        Cursor cursor = db.rawQuery(query, params);
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToFirst();
            barcode.setBarcodeID(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Barcodes._ID)));
            barcode.setBarcodeNumber(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.Barcodes.COLUMN_NAME_BARCODE)));
            barcode.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Barcodes.COLUMN_NAME_PRODUCT_NAME)));
            barcode.setInitialPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(Contract.Barcodes.COLUMN_NAME_INITIAL_PRICE)));
            cursor.close();
            return barcode;
        }
    }

    public void deleteExpenditure(int id) {
        SQLiteDatabase db = ctrl.openDatabase();
        String params = String.valueOf(id);
        db.delete(Contract.Exp.TABLE_NAME, Contract.Exp._ID + "=" + params, null);

    }
}


