/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Supplier;

import Business.Product.Product;
import java.util.ArrayList;

/**
 *
 * @author wangsiting
 */
public class Supplier {
    private String supplyName;
    private ArrayList<Product> productCatalog;
    
    public Supplier() {
        productCatalog = new ArrayList<>();
    }
    
    public String getSupplyName() {
        return supplyName;
    }
    
    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }
    
    public ArrayList<Product> getProductCatalog() {
        return productCatalog;
    }
    
    public void setProductCatalog(ArrayList<Product> productCatalog) {
        this.productCatalog = productCatalog;
    }
    
    public void addProduct(Product product) {
        productCatalog.add(product);
    }
    
    public void removeProduct(Product product) {
        productCatalog.remove(product);
    }
    
    @Override
    public String toString() {
        return supplyName;
    }
}
