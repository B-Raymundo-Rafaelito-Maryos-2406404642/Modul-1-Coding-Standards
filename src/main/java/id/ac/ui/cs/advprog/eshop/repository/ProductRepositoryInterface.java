package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.Iterator;
import java.util.UUID;

public interface ProductRepositoryInterface {
    Product createProduct(Product product);
    Iterator<Product> findAllProducts();
    Product findProductByItsId(UUID productId);
    Product findProductByItsName(String productName);
    Product editProduct(Product editedProduct);
    boolean deleteProductByItsId(UUID productId);
}
