package com.example.android.theinventory;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class addproduct {
    private int PICK_IMAGE_REQUEST = 1;
    ProductValidator PV = new ProductValidator();
    ProductDbHelper DB = new ProductDbHelper(this);//cannot be applied to context
    Uri uri;
    TextView errorMsg;

    @Override//does not override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//cannot resolve oncreate
        setContentView(R.layout.add_product);//cannot resolve method

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button upload = (Button) findViewById(R.id.upload);//cannot resolve method
        Button add_db = (Button) findViewById(R.id.add_db);//cannot resolve method
        final TextView errorMsg = (TextView) findViewById(R.id.error_message);//cannot resolve method

        final EditText add_product_name = (EditText) findViewById(R.id.add_product_name);//cannot resolve method
        final EditText add_product_price = (EditText) findViewById(R.id.add_product_price);//cannot resolve method
        final EditText add_quantity = (EditText) findViewById(R.id.add_quantity);//cannot resolve method
        final EditText contact = (EditText) findViewById(R.id.contact);//cannot resolve method

       upload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                if (validateField(add_product_name, add_product_price, add_quantity, contact)) {
                    if (uri == null) {
                        errorMsg.setText("Image required!!");
                    } else {

                        errorMsg.setText("");
                        String prodName = add_product_name.getText().toString();
                        String prodPrice = add_product_price.getText().toString();
                        String prodQty = add_quantity.getText().toString();
                        String email = contact.getText().toString();

                        ProdClass pc = new ProdClass();
                        pc.setProductName(prodName);
                        pc.setProductPrice(Float.parseFloat(prodPrice));
                        pc.setProductQty(Integer.parseInt(prodQty));
                        pc.setProductImgLink(uri.toString());
                        pc.setProductQtySold(0);
                        pc.setSupplierEmail(email);
                        DB.addProduct(pc);

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);//cannot resolve method
                        finish();//cannot resolve method
                    }
                }
            }

        });

        add_db.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent;

                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }

                checkWriteToExternalPerms();
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//cannot resolve method

            }

        });
    }

    private void checkWriteToExternalPerms() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)//wrong first arguement
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
            }
        } else {
        }
    }

    private boolean validateField(EditText name, EditText price, EditText qty, EditText email) {
        if (!PV.checkBlank(name) && !PV.checkBlank(price) && !PV.checkBlank(qty) && !PV.checkBlank(email)) {
            if (!PV.checkIsFloat(price)) {
                errorMsg.setText("Price is in wrong format");
                return false;
            } else {
                if (!PV.checkIsInteger(qty)) {
                    errorMsg.setText("Quantity is in wrong format");
                    return false;
                } else {
                    return true;
                }
            }
        }
        {
            errorMsg.setText("Missing field detected.");
            return false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//cannot resolve method

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {//cannot resolve method

            uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);//cannot resolve method
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String picturePath = cursor.getString(columnIndex); // returns null

            cursor.close();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);//cannot resolve method

                ImageView imageView = (ImageView) findViewById(R.id.upload);//unexpected cast to imageview-this error I can fix
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
