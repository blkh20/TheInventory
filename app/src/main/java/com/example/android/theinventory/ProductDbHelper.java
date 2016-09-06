package com.example.android.theinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class ProductDbHelper extends SQLiteOpenHelper {

    public ProductDbHelper(Context context) {
        super(context, ProductContract.DB_NAME, null, ProductContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TEXT_TYPE = " TEXT";
        final String COMMA_SEP = ",";
        final String INTEGER_TYPE = " INTEGER";
        final String FLOAT_TYPE = " REAL";
        String createTable = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " ( " +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_LINK + TEXT_TYPE + COMMA_SEP +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE + FLOAT_TYPE + COMMA_SEP +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY_SOLD + INTEGER_TYPE + COMMA_SEP +
                ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_EMAIL + TEXT_TYPE + ");";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME);
        onCreate(db);
    }

    // code to add the new Product
    public void addProduct(ProdClass product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, product.getProductName());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE, product.getProductPrice());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, product.getProductQty());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_LINK, product.getProductImgLink());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_EMAIL, product.getSupplierEmail());
        // Inserting Row
        db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single Product
    public ProdClass getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, new String[]{ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID,
                        ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_IMAGE_LINK, ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY_SOLD,
                        ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE,
                        ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME, ProductContract.ProductEntry.COLUMN_NAME_SUPPLIER_EMAIL},
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProdClass product = new ProdClass(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4), cursor.getInt(5), cursor.getString(6));

        return product;
    }

    public ArrayList<ProdClass> getAllProducts() {
        ArrayList<ProdClass> productList = new ArrayList<ProdClass>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ProdClass product = new ProdClass();
                product.setProductID(cursor.getInt(0));
                product.setProductName(cursor.getString(1));
                product.setProductImgLink(cursor.getString(2));
                product.setProductPrice(cursor.getFloat(3));
                product.setProductQty(cursor.getInt(4));
                product.setProductQtySold((cursor.getInt(5)));
                product.setSupplierEmail((cursor.getString(6)));

                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return contact list
        return productList;
    }

    public Cursor getCursor() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * FROM " + ProductContract.ProductEntry.TABLE_NAME + ";";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public int updateProduct(ProdClass product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY, product.getProductQty());
        values.put(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_QUANTITY_SOLD, product.getProductQtySold());


        // updating row
        return db.update(ProductContract.ProductEntry.TABLE_NAME, values, ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getProductID())});
    }

    // Deleting single contact
    public void deleteProduct(ProdClass product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ProductContract.ProductEntry.TABLE_NAME, ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getProductID())});
        db.close();
    }

    public void deleteDB(SQLiteDatabase db) {

        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ProductContract.ProductEntry.TABLE_NAME); //delete all rows in a table
        db.close();

    }

    // Getting Count
    public int getProductCount() {
        String countQuery = "SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
}
