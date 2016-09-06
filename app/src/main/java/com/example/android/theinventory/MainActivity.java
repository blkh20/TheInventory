package com.example.android.theinventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ProdClass> productStorage = new ArrayList<ProdClass>();
    ListView LV;
    TextView TV;
    ProductAdapter adapter;
    ProductDbHelper db = new ProductDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.addProduct);


        LV = (ListView) findViewById(R.id.listProduct);
        adapter = new ProductAdapter(MainActivity.this, productStorage);
        LV.setAdapter(adapter);

        if (db.getProductCount() == 0) {
            TV = (TextView) findViewById(R.id.noProduct);
            TV.setVisibility(View.VISIBLE);
        } else {
            LV.setVisibility(View.VISIBLE);

            productStorage.addAll(db.getAllProducts());
            adapter.notifyDataSetChanged();
        }

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductDetails.class);
                intent.putExtra("class", productStorage.get(position));
                startActivityForResult(intent, 0);
            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, addproduct.class);

                startActivityForResult(i, 0);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(LV.getVisibility() == View.GONE)
        {
            LV.setVisibility(View.GONE);
            TV.setVisibility(View.VISIBLE);
        }
        adapter.clear();
        productStorage.clear();
        productStorage.addAll(db.getAllProducts());
        adapter.notifyDataSetChanged();

        if(productStorage != null)
        {
            if(productStorage.size() == 0)
            {
                LV.setVisibility(View.GONE);
            }
            else
            {
                TV.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            LV.setVisibility(View.VISIBLE);
        }
    }
}

