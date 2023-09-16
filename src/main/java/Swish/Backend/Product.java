package Swish.Backend;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class Product {

    //private fields
    private int productID;
    private String productName;
    private int productStock;
    private double productCostPrice;
    private double productSellPrice;
    private String productCategory;
    private javafx.scene.image.Image productImage;
    private File productFile;
    private String description;

    //default constructor
    public Product() {
    }

    //parameterised constructor
    public Product(int pID, String pN, int pS, double pCP, double pSP, javafx.scene.image.Image pI, String pC, String pD){
        productID = pID;
        productName = pN;
        productStock = pS;
        productCostPrice = pCP;
        productSellPrice = pSP;
        productImage = pI;
        productCategory = pC;
        description = pD;
    }

    //getter
    public int getProductID() {
        return productID;
    }

    //setter
    public void setProductID(int productID) {
        this.productID = productID;
    }

    //getter
    public String getProductName() {
        return productName;
    }

    //setter
    public void setProductName(String productName) {
        this.productName = productName;
    }

    //getter
    public int getProductStock() {
        return productStock;
    }

    //setter
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    //getter
    public double getProductCostPrice() {
        return productCostPrice;
    }

    //setter
    public void setProductCostPrice(double productCostPrice) {this.productCostPrice = productCostPrice;}

    //getter
    public double getProductSellPrice(){return productSellPrice;}

    //setter
    public void setProductSellPrice(double productSellPrice){ this.productSellPrice = productSellPrice; }

    //getter
    public javafx.scene.image.Image getProductImage() {
        return productImage;
    }

    //setter
    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    //getter
    public String getProductCategory() {
        return productCategory;
    }

    //setter
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    //getter
    public File getProductFile() {
        return productFile;
    }

    //setter
    public void setProductFile(File productFile) {
        this.productFile = productFile;
    }

    //getter
    public String getDescription() {
        return description;
    }

    //setter
    public void setDescription(String description) {
        this.description = description;
    }

    //chekcs the current stock of a product
    public boolean checkStock(){
        if(productStock > 0)
            return true; //returns true if there is stock available
        else
            return false; //returns false if no stock is available
    }

    //checks that the company's balance does not go below R25000
    public boolean validateStockOrder(int stockToOrder, double costPrice, double companyBalance){
        double sum = companyBalance - (stockToOrder * costPrice); //calculation
        if(sum < 25000){
            return false; //returns false if the calculation will cause the balance to go below R25000
        }else{
            return true; //returns true if the calculation will not cause an issue with the company balance
        }
    }

    //gets the total cost of all products in an arraylist
    public static double totalCost(ArrayList<Product> products){
        double cost = 0; //initial cost is 0
        for(int i = 0; i < products.size(); i++){ //loops while there is a product object in the arraylist
            cost += products.get(i).getProductSellPrice(); //the product object's price is added to the initial cost
        }
        return cost; //total cost returned after loop has been completed
    }

    //toString method
    public String toString(){
        String temp;
        temp = "Product ID: " + getProductID() + "\nProduct Name: " + getProductName() + "\nCost Price: R" + getProductCostPrice() + "\nSell Price: R" + getProductSellPrice() + "\nProduct Stock: " + getProductStock() + "\nProduct Category: " + getProductCategory();
        return temp;
    }

    //Second toString with dummy parameter.
    public String toString(boolean x){
        String temp;
        temp = getProductName() + "     |     R" + getProductSellPrice() + "     |     " + getProductCategory();
        return temp;
    }
}
