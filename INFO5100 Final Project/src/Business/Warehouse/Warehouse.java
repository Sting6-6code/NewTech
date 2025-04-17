/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Warehouse;

import Business.Product.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author yushe
 */
public class Warehouse {
//    private HashMap<Product, Integer> stock;
    private ArrayList<Stock> stock;
    
    public Warehouse() {
        stock = new ArrayList<>();
    }

//    public HashMap<Product, Integer> getStock() {
//        return stock;
//    }
    
    
//    
//    public String getProductName(Product p) {
//        if (stock.containsKey(p)) {
//            return p.getProductName();
//        } else {
//            return "Product not in stock";
//        }
//    }
//    
//    public void addStock(Product product, int amount) {
//        stock.put(product, stock.getOrDefault(product, 0) + amount); // amount start from 0 if nothing added, start from stock.get(product) if in stock
//    }
//    
//    public int getProductAmount(Product product) {
//        return stock.getOrDefault(product, 0);
//    }

    public ArrayList<Stock> getStock() {
        return stock;
    }
    
    
}
