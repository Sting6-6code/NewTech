/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Warehouse;

import Business.Product.Product;
import java.util.HashMap;

/**
 *
 * @author yushe
 */
public class Stock<K, V> {
//    private HashMap<Product, Integer> stockLevel;
//    
//    public Stock() {
//        stockLevel = new HashMap<>();
//    }
//    
//    public Stock(Product p, int amount) {
//        stockLevel = new HashMap<>();
//        stockLevel.put(p, amount);
//    }
//    
//    public int getAmount(Product p) {
//        if (stockLevel.containsKey(p)) {
//            return stockLevel.get(p);
//        }
//        return 0;
//    }
//    
//    public Product getProduct(Product p) {
//        if (stockLevel.containsKey(p)) {
//            return stockLevel.
//        }
//    }
   
    private Product product;
    private int amount;

    public Stock(Product p, int i) {
        this.product = p;
        this.amount = i;
    }

    public Product getProduct() { return product; }
    public int getAmount() { return amount; }
    
    public String getStockStatus() {
        if (amount < 20) return "low in stock";
        else if (amount <= 0) return "sold out";
        else if (amount <= 60 && amount >= 20) return "in stock";
        else return "excess";
    }
    
    @Override
    public String toString() {
        return product.toString();
    }
}
