package com.example.android.theinventory;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class ProductAdapter extends ArrayAdapter<ProdClass> {


    public ProductAdapter(Activity context, ArrayList<ProdClass> items) {
        super(context, 0, items);
    }

    ProductDbHelper db = new ProductDbHelper(getContext());

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final ProdClass currentProduct = getItem(position);


        Button btnBuy = (Button) listItemView.findViewById(R.id.buyProd);

        TextView tvProductName = (TextView) listItemView.findViewById(R.id.dbProductName);
        tvProductName.setText(currentProduct.getProductName());

        final TextView tvProductQty = (TextView) listItemView.findViewById(R.id.dbProductQantity);
        tvProductQty.setText(Integer.toString(currentProduct.getProductQty()));

        TextView tvProductPrice = (TextView) listItemView.findViewById(R.id.dbProductPrice);
        tvProductPrice.setText(Float.toString(currentProduct.getProductPrice()));

        final TextView tvProductSold = (TextView) listItemView.findViewById(R.id.dbProductSold);
        tvProductSold.setText(Integer.toString(currentProduct.getProductQtySold()));

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(tvProductQty.getText().toString());
                int sold = Integer.parseInt(tvProductSold.getText().toString());

                if (qty > 0) {
                    qty--;
                    sold++;
                    currentProduct.setProductQtySold(qty);
                    currentProduct.setProductQty(sold);
                    tvProductQty.setText(Integer.toString(qty));
                    tvProductSold.setText(Integer.toString(sold));
                    db.updateProduct(currentProduct);
                }

            }
        });

        return listItemView;
    }


}
