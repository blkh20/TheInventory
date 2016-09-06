package com.example.android.theinventory;

import java.io.Serializable;

/**
 * Created by BlkH20 on 9/6/2016.
 */
public class ProdClass implements Serializable {

    private int productID;
    private String productName;
    private String productImgLink;
    private float productPrice;
    private int productQty;
    private String supplierEmail;
    private int productQtySold;

    public ProdClass(int productID, String productName, String productImgLink, float productPrice, int productQty, int productQtySold, String supplierEmail) {
        this.productID = productID;
        this.productName = productName;
        this.productImgLink = productImgLink;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productQtySold = productQtySold;
        this.supplierEmail = supplierEmail;
    }

    public ProdClass() {

    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public int getProductQtySold() {
        return productQtySold;
    }

    public void setProductQtySold(int productQtySold) {
        this.productQtySold = productQtySold;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImgLink() {
        return productImgLink;
    }

    public void setProductImgLink(String productImgLink) {
        this.productImgLink = productImgLink;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}


