package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new  ArrayList<>();

    public Product createProduct(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAllProducts() {
        return productData.iterator();
    }

    public Product findProductByItsId(UUID productId) {
        for (Product product : productData) {
            if (product.getProductId() != null &&  product.getProductId().equals(productId))
                return product;
        }
        return null;
    }

    public Product findProductByItsName(String productName) {
        for (Product product : productData) {
            if (product.getProductName().equals(productName))
                return product;
        }
        return null;
    }

    public Product editProduct(Product editedProduct) {
        if (editedProduct == null || editedProduct.getProductId() == null)
            return null;
        for (Product product : productData) {
            if (product.getProductId().equals(editedProduct.getProductId())) {
                // product.setProductId(editedProduct.getProductId());
                product.setProductName(editedProduct.getProductName());
                product.setProductQuantity(editedProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}
