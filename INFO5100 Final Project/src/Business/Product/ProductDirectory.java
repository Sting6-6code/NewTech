/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangsiting
 */
public class ProductDirectory {
    // ProductCatalog.java - 产品目录管理

    private List<Product> products;

    public ProductDirectory() {
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // 添加产品
    public void addProduct(Product product) {
        products.add(product);
    }

    // 根据ID获取产品
    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // 获取所有产品
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // 获取所有上架产品
    public List<Product> getOnShelfProducts() {
        List<Product> onShelfProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isOnShelf()) {
                onShelfProducts.add(product);
            }
        }
        return onShelfProducts;
    }

    // 获取库存低的产品
    public List<Product> getLowStockProducts() {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getStockStatus().equals("Low")) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
    }

    // 更新产品信息
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(updatedProduct.getProductId())) {
                products.set(i, updatedProduct);
                break;
            }
        }
    } 
}
