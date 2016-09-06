package com.example.android.theinventory;

import android.provider.BaseColumns;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class ProductContract {

    public static final String DB_NAME = "ProductContract.db";
    public static final int DB_VERSION = 1;

    public ProductContract() {
    }

    /* Inner class that defines the table contents */
    public class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "productTable";
        public static final String COLUMN_NAME_PRODUCT_ID = "prdID";
        public static final String COLUMN_NAME_PRODUCT_NAME = "prdName";
        public static final String COLUMN_NAME_PRODUCT_PRICE = "prdPrice";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY = "prdQty";
        public static final String COLUMN_NAME_PRODUCT_QUANTITY_SOLD = "prdQtySold";
        public static final String COLUMN_NAME_PRODUCT_IMAGE_LINK = "prdImgLink";
        public static final String COLUMN_NAME_SUPPLIER_EMAIL = "supEmail";
    }

}